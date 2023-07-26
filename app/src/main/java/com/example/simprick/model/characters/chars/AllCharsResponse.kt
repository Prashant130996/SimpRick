package com.example.simprick.model.characters.chars

import com.example.simprick.model.characters.single.Character

data class AllCharsResponse(
    val info: Info,
    val results: List<Character> = emptyList()
) {
    data class Info(
        val count: Int = 0,
        val pages: Int = 0,
        val next: String? = null,
        val prev: String? = null
    )
}
