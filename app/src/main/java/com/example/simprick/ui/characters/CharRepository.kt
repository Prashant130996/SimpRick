package com.example.simprick.ui.characters

import com.example.simprick.repo.SimpleResponse
import com.example.simprick.domain.mappers.CharacterMapper
import com.example.simprick.domain.models.Character
import com.example.simprick.ui.characters.all.CharPagingSource
import com.example.simprick.model.characters.chars.AllCharsResponse
import com.example.simprick.model.characters.single.CharacterByIdResponse
import com.example.simprick.model.episodes.single.EpisodeByIdResponse
import com.example.simprick.network.ApiClient
import javax.inject.Inject

class CharRepository @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getCharById(charId: Int): Character? {
        val request = apiClient.getCharacterById(charId)
        if (request.failed || !request.isSuccessful) return null

        val networkEpisode = getEpisodeFromCharacterResponse(request.body)

        return CharacterMapper.buildFrom(request.body, episodes = networkEpisode)
    }

    private suspend fun getEpisodeFromCharacterResponse(body: CharacterByIdResponse): List<EpisodeByIdResponse> {
        val episodeRange = body.episode.map {
            it.substring(it.lastIndexOf("/") + 1)
        }.toString()
        val request = apiClient.getEpisodeRange(episodeRange)
        if (request.failed or !request.isSuccessful) return emptyList()

        return request.body
    }


    suspend fun getAllChars(pageIndex: Int): SimpleResponse<AllCharsResponse>? {
        val request = apiClient.getAllChars(pageIndex)
        if (request.failed or !request.isSuccessful) return null
        return request
    }
}