package com.example.simprick.model.characters.chars

import com.example.simprick.model.PageInfo
import com.example.simprick.model.characters.single.CharacterByIdResponse

data class AllCharsResponse(
    val info: PageInfo,
    val results: List<CharacterByIdResponse> = emptyList()
)
