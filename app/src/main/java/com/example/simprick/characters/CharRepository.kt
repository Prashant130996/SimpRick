package com.example.simprick.characters

import com.example.rickmorty.repo.SimpleResponse
import com.example.simprick.model.charById.CharByIdResponse
import com.example.simprick.model.chars.AllCharsResponse
import com.example.simprick.network.ApiClient
import javax.inject.Inject

class CharRepository @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getCharById(charId: Int): CharByIdResponse? {
        val request = apiClient.getCharacterById(charId)
        if (request.failed || !request.isSuccessful) return null
        return request.body
    }


    suspend fun getAllChars(pageIndex: Int): SimpleResponse<AllCharsResponse>? {
        val request = apiClient.getAllChars(pageIndex)
        if (request.failed or !request.isSuccessful) return null
        return request
    }

    fun charPagingSource() = CharPagingSource(CharRepository(apiClient))

}