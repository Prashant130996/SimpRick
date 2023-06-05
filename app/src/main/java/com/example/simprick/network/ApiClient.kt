package com.example.simprick.network

import com.example.simprick.model.charById.CharByIdResponse
import com.example.rickmorty.repo.SimpleResponse
import com.example.simprick.model.chars.AllCharsResponse
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class ApiClient @Inject constructor(private val rickAndMortyService: RickAndMortyService) {

    suspend fun getCharacterById(charId: Int): SimpleResponse<CharByIdResponse> {
        return safeApiCall { rickAndMortyService.getCharById(charId) }
    }

    suspend fun getAllChars(pageIndex: Int): SimpleResponse<AllCharsResponse> {
        return safeApiCall { rickAndMortyService.getAllChars(pageIndex) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}