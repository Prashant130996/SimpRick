package com.example.simprick.network

import com.example.simprick.model.charById.CharByIdResponse
import com.example.simprick.model.chars.AllCharsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {
    @GET("character/{char-id}")
    suspend fun getCharById(@Path("char-id") characterId: Int): Response<CharByIdResponse>

    @GET("character")
    suspend fun getAllChars(@Query("page") pageIndex: Int):Response<AllCharsResponse>

}