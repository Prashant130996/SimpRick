package com.example.simprick.ui.characters.all

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.simprick.domain.mappers.CharacterMapper
import com.example.simprick.domain.models.Character
import com.example.simprick.ui.characters.CharRepository
import javax.inject.Inject
import kotlin.math.max

private const val STARTING_KEY = 1

class CharPagingSource @Inject constructor(private val charRepository: CharRepository) :
    PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val char = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = char.id - (state.config.pageSize / 2))
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        // If params.key is null, it is the first load, so we start loading with STARTING_KEY
        val startKey = params.key ?: STARTING_KEY
        val range = startKey.until(startKey + params.loadSize)

        val response = charRepository.getAllChars(startKey)
        val charResponse = response?.body
        val chars = charResponse?.results

        response?.exception?.let { return LoadResult.Error(it) }

        return LoadResult.Page(
            data = chars?.map { CharacterMapper.buildFrom(it, emptyList()) }.orEmpty(),
            prevKey = when (startKey) {
                STARTING_KEY -> null
                else -> when (val prevKey = ensureValidKey(key = range.first - params.loadSize)) {
                    // We're at the start, there's nothing more to load
                    STARTING_KEY -> null
                    else -> prevKey
                }
            },
            nextKey = getPageIndexFromNext(charResponse?.info?.next)
        )
    }

    /**
     * Makes sure the paging key is never less than [STARTING_KEY]
     */
    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)

    private fun getPageIndexFromNext(next: String?): Int? {
        return next?.split("?page=")?.get(1)?.toInt()
    }
}