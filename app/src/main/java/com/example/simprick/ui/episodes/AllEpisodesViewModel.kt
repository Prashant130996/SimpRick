package com.example.simprick.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.example.simprick.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AllEpisodesViewModel @Inject constructor(private val episodeRepository: EpisodeRepository) :
    ViewModel() {

    val flow = Pager(
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PREFETCH_DISTANCE,
            enablePlaceholders = false
        ), pagingSourceFactory = { EpisodePagingSource(episodeRepository) }
    ).flow.cachedIn(viewModelScope).map {
        it.insertSeparators { model: EpisodesUiModel?, model2: EpisodesUiModel? ->

            // Initial separator for the first season header (before the whole list)
            if (model == null) {
                return@insertSeparators EpisodesUiModel.Header("Season 1")
            }

            // No footer
            if (model2 == null) {
                return@insertSeparators null
            }

            // Make sure we only care about the items (episodes)
            if (model is EpisodesUiModel.Header || model2 is EpisodesUiModel.Header) {
                return@insertSeparators null
            }

            // Little logic to determine if a separator is necessary
            val episode1 = (model as EpisodesUiModel.Item).episode
            val episode2 = (model2 as EpisodesUiModel.Item).episode
            return@insertSeparators if (episode2.seasonNumber != episode1.seasonNumber) {
                EpisodesUiModel.Header("Season ${episode2.seasonNumber}")
            } else {
                null
            }
        }
    }
}