package com.example.simprick.model.chars

import com.example.simprick.model.charById.CharByIdResponse

data class AllCharsResponse(
    val info: Info,
    val results: List<CharByIdResponse> = emptyList()
) {
    data class Info(
        val count: Int = 0,
        val pages: Int = 0,
        val next: String? = null,
        val prev: String? = null
    )
}
