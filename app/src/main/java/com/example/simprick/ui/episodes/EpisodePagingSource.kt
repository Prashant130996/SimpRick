package com.example.simprick.ui.episodes

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.simprick.domain.mappers.EpisodeMapper
import javax.inject.Inject

class EpisodePagingSource @Inject constructor(private val episodeRepository: EpisodeRepository) :
    PagingSource<Int, EpisodesUiModel>() {
    override fun getRefreshKey(state: PagingState<Int, EpisodesUiModel>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodesUiModel> {
        val pageNumber = params.key ?: 1
        val prevKey = if (pageNumber == 1) null else pageNumber - 1

        val pageResponse = episodeRepository.fetchEpisodePage(pageNumber)
        val episodeResponse = pageResponse?.body
        val episodes = episodeResponse?.results
        pageResponse?.exception?.let { return LoadResult.Error(it) }

        return LoadResult.Page(
            data = episodes?.map { response ->
                EpisodesUiModel.Item(episode = EpisodeMapper.buildFrom(response))
            }.orEmpty(),
            prevKey = prevKey,
            nextKey = getPageIndexFromNext(episodeResponse?.info?.next)
        )
    }

    private fun getPageIndexFromNext(next: String?): Int? {
        return next?.split("?page=")?.get(1)?.toInt()
    }

}