package com.example.simprick.ui.characters

import com.example.rickmorty.repo.SimpleResponse
import com.example.simprick.ui.characters.all.CharPagingSource
import com.example.simprick.model.characters.single.Character
import com.example.simprick.model.characters.chars.AllCharsResponse
import com.example.simprick.network.ApiClient
import javax.inject.Inject

class CharRepository @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getCharById(charId: Int): Character? {
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