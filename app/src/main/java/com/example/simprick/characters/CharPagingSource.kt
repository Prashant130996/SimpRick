package com.example.simprick.characters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.simprick.model.charById.CharByIdResponse
import javax.inject.Inject
import kotlin.math.max

private const val STARTING_KEY = 0

class CharPagingSource @Inject constructor(private val charRepository: CharRepository) :
    PagingSource<Int, CharByIdResponse>() {
    override fun getRefreshKey(state: PagingState<Int, CharByIdResponse>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val char = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = char.id - (state.config.pageSize / 2))
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharByIdResponse> {
        // If params.key is null, it is the first load, so we start loading with STARTING_KEY
        val startKey = params.key ?: STARTING_KEY
        val range = startKey.until(startKey + params.loadSize)

        val chars = charRepository.getAllChars(startKey)

        chars?.exception?.let { return LoadResult.Error(it) }

        return LoadResult.Page(
            data = chars!!.body.results.map { charsById ->
                CharByIdResponse(
                    created = charsById.created,
                    episode = charsById.episode,
                    gender = charsById.gender,
                    id = charsById.id,
                    image = charsById.image,
                    location = charsById.location,
                    name = charsById.name,
                    origin = charsById.origin,
                    species = charsById.species,
                    status = charsById.status,
                    type = charsById.type,
                    charsById.url
                )
            },
            prevKey = when (startKey) {
                STARTING_KEY -> null
                else -> when (val prevKey = ensureValidKey(key = range.first - params.loadSize)) {
                    // We're at the start, there's nothing more to load
                    STARTING_KEY -> null
                    else -> prevKey
                }
            },
            nextKey = getPageIndexFromNext(chars.body.info.next)
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