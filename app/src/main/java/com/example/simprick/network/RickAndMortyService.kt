package com.example.simprick.network

import com.example.simprick.model.characters.single.CharacterByIdResponse
import com.example.simprick.model.characters.chars.AllCharsResponse
import com.example.simprick.model.episodes.single.EpisodeByIdResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {
    @GET("character/{char-id}")
    suspend fun getCharById(@Path("char-id") characterId: Int): Response<CharacterByIdResponse>

    @GET("character")
    suspend fun getAllChars(@Query("page") pageIndex: Int): Response<AllCharsResponse>

    @GET("episode/{episode-id}")
    suspend fun getEpisodeById(@Path("episode-id") episodeId: Int): Response<EpisodeByIdResponse>

    @GET("episode/{episode-range}")
    suspend fun getEpisodeRange(@Path("episode-range") episodeRange: String): Response<List<EpisodeByIdResponse>>

}