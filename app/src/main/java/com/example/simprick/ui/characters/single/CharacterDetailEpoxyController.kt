package com.example.simprick.ui.characters.single

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.bumptech.glide.Glide
import com.example.simprick.R
import com.example.simprick.databinding.ModelCharacterDetailsDataPointBinding
import com.example.simprick.databinding.ModelCharacterDetailsHeaderBinding
import com.example.simprick.databinding.ModelCharacterDetailsImageBinding
import com.example.simprick.databinding.ModelEpisodeCarouselBinding
import com.example.simprick.databinding.ModelTitleBinding
import com.example.simprick.domain.models.Character
import com.example.simprick.domain.models.Episode
import com.example.simprick.epoxy.LoadingEpoxyModel
import com.example.simprick.epoxy.ViewBindingKotlinModel

class CharacterDetailEpoxyController : EpoxyController() {

    private var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var character: Character? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    override fun buildModels() {
        if (isLoading) {
            LoadingEpoxyModel().id("Loader").addTo(this)
            return
        }
        if (character == null) {
            return
        }

        HeaderEpoxyModel(
            character!!.name, character!!.gender, character!!.status
        ).id("header").addTo(this)

        ImageEpoxyModel(character!!.image).id("image").addTo(this)

        if (character!!.episodeList.isNotEmpty()) {
            val items = character!!.episodeList.map {
                EpisodeCarouselItemEpoxyModel(it).id(it.id)
            }
            TitleEpoxyModel(title = "Episodes").id("title_episode").addTo(this)

            CarouselModel_()
                .id("episode_carousel")
                .models(items)
                .numViewsToShowOnScreen(1.15f)
                .addTo(this)
        }

        DataPointEpoxyModel("Origin", character!!.origin.name).id("data_1").addTo(this)
        DataPointEpoxyModel("Species", character!!.species).id("data_2").addTo(this)
    }

    data class HeaderEpoxyModel(
        val name: String,
        val gender: String,
        val lifeStatus: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header) {
        override fun ModelCharacterDetailsHeaderBinding.bind() {
            nameTextView.text = name
            if (gender.equals("male", true)) {
                genderImageView.setImageResource(R.drawable.baseline_male_24)
            } else genderImageView.setImageResource(R.drawable.baseline_female_24)
            aliveTextView.text = lifeStatus
        }
    }

    data class ImageEpoxyModel(
        val imageUrl: String,
    ) : ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(R.layout.model_character_details_image) {
        override fun ModelCharacterDetailsImageBinding.bind() {
            Glide.with(headerImageView).load(imageUrl).into(headerImageView)
        }
    }

    data class DataPointEpoxyModel(
        val title: String,
        val description: String,
    ) : ViewBindingKotlinModel<ModelCharacterDetailsDataPointBinding>(R.layout.model_character_details_data_point) {
        override fun ModelCharacterDetailsDataPointBinding.bind() {
            labelTextView.text = title
            textView.text = description
        }
    }

    data class EpisodeCarouselItemEpoxyModel(val episode: Episode) :
        ViewBindingKotlinModel<ModelEpisodeCarouselBinding>(R.layout.model_episode_carousel) {
        override fun ModelEpisodeCarouselBinding.bind() {
            episodeNumTv.text = episode.getFormattedSeasonTruncated()
            val infoText = "${episode.name}\n${episode.airDate}"
            episodeInfoTv.text = infoText
        }

    }

    data class TitleEpoxyModel(val title: String) :
        ViewBindingKotlinModel<ModelTitleBinding>(R.layout.model_title) {
        override fun ModelTitleBinding.bind() {
            episodeTitleTv.text = title
        }

    }
}