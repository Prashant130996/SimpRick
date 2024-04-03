package com.example.simprick.ui.episodes

import com.example.simprick.model.episodes.allEpisodes.AllEpisodeResponse
import com.example.simprick.network.ApiClient
import com.example.simprick.repo.SimpleResponse
import javax.inject.Inject

class EpisodeRepository @Inject constructor(private val apiClient: ApiClient) {

    suspend fun fetchEpisodePage(pageIndex: Int): SimpleResponse<AllEpisodeResponse>? {
        val request = apiClient.getEpisodesPage(pageIndex)
        if (request.failed or !request.isSuccessful) return null
        return request
    }

}