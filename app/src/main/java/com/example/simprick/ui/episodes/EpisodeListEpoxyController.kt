package com.example.simprick.ui.episodes

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.simprick.R
import com.example.simprick.databinding.ModelEpisodeListItemBinding
import com.example.simprick.databinding.ModelEpisodeListTitleBinding
import com.example.simprick.domain.models.Episode
import com.example.simprick.epoxy.ViewBindingKotlinModel

class EpisodeListEpoxyController : PagingDataEpoxyController<EpisodesUiModel>() {

    override fun buildItemModel(currentPosition: Int, item: EpisodesUiModel?): EpoxyModel<*> {
        return when (item!!) {
            is EpisodesUiModel.Item -> {
                val episode = (item as EpisodesUiModel.Item).episode
                EpisodeListItemEpoxyModel(
                    episode = episode,
                    onClick = { episodeId -> }
                ).id("episode_${episode.id}")
            }

            is EpisodesUiModel.Header -> {
                val headerText = (item as EpisodesUiModel.Header).text
                EpisodeListTitleEpoxyModel(headerText).id("header_$headerText")
            }
        }
    }

    data class EpisodeListItemEpoxyModel(
        val episode: Episode,
        val onClick: (Int) -> Unit
    ) : ViewBindingKotlinModel<ModelEpisodeListItemBinding>(R.layout.model_episode_list_item) {

        override fun ModelEpisodeListItemBinding.bind() {
            episodeNameTextView.text = episode.name
            episodeAirDateTextView.text = episode.airDate
            episodeNumberTextView.text = episode.getFormattedSeasonTruncated()

            root.setOnClickListener { onClick(episode.id) }
        }
    }

    data class EpisodeListTitleEpoxyModel(
        val title: String
    ) : ViewBindingKotlinModel<ModelEpisodeListTitleBinding>(R.layout.model_episode_list_title) {

        override fun ModelEpisodeListTitleBinding.bind() {
            textView.text = title
        }
    }
}