package com.example.simprick.domain.mappers

import com.example.simprick.domain.models.Episode
import com.example.simprick.model.episodes.single.EpisodeByIdResponse

object EpisodeMapper {

    fun buildFrom(response: EpisodeByIdResponse): Episode {
        return Episode(
            id = response.id,
            airDate = response.air_date,
            name = response.name,
            episode = response.episode
        )
    }
}