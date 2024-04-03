package com.example.simprick.network

import com.example.simprick.domain.models.Character

object SimpleMortyCache {

    val characterMap = mutableMapOf<Int, Character>()
}