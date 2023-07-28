package com.example.simprick.domain.mappers

import com.example.simprick.model.characters.single.CharacterByIdResponse
import com.example.simprick.domain.models.Character
import com.example.simprick.model.episodes.single.EpisodeByIdResponse

object CharacterMapper {

    fun buildFrom(response: CharacterByIdResponse, episodes: List<EpisodeByIdResponse>): Character {
        return Character(
            episodeList = episodes.map { EpisodeMapper.buildFrom(it) },
            gender = response.gender,
            id = response.id,
            image = response.image,
            location = Character.Location(
                name = response.location.name,
                url = response.location.url
            ),
            name = response.name,
            origin = Character.Origin(
                name = response.origin.name,
                url = response.origin.url
            ),
            species = response.species,
            status = response.status
        )
    }

}