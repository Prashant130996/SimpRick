package com.example.simprick.network

import com.example.simprick.model.characters.single.CharacterByIdResponse
import com.example.simprick.model.characters.chars.AllCharsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {
    @GET("character/{char-id}")
    suspend fun getCharById(@Path("char-id") characterId: Int): Response<CharacterByIdResponse>

    @GET("character")
    suspend fun getAllChars(@Query("page") pageIndex: Int):Response<AllCharsResponse>

}