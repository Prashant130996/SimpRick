package com.example.simprick.network

import com.example.simprick.repo.SimpleResponse
import com.example.simprick.model.characters.single.CharacterByIdResponse
import com.example.simprick.model.characters.chars.AllCharsResponse
import com.example.simprick.model.episodes.allEpisodes.AllEpisodeResponse
import com.example.simprick.model.episodes.single.EpisodeByIdResponse
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class ApiClient @Inject constructor(private val rickAndMortyService: RickAndMortyService) {

    suspend fun getCharacterById(charId: Int): SimpleResponse<CharacterByIdResponse> {
        return safeApiCall { rickAndMortyService.getCharById(charId) }
    }

    suspend fun getAllChars(pageIndex: Int): SimpleResponse<AllCharsResponse> {
        return safeApiCall { rickAndMortyService.getAllChars(pageIndex) }
    }

    suspend fun getEpisodeById(episodeId: Int): SimpleResponse<EpisodeByIdResponse> {
        return safeApiCall { rickAndMortyService.getEpisodeById(episodeId) }
    }

    suspend fun getEpisodesPage(pageIndex: Int): SimpleResponse<AllEpisodeResponse> {
        return safeApiCall { rickAndMortyService.getEpisodePage(pageIndex) }
    }

    suspend fun getEpisodeRange(episodeRange: String): SimpleResponse<List<EpisodeByIdResponse>> {
        return safeApiCall { rickAndMortyService.getEpisodeRange(episodeRange) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}