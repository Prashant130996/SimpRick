package com.example.simprick.model.episodes.allEpisodes

import com.example.simprick.model.PageInfo
import com.example.simprick.model.episodes.single.EpisodeByIdResponse

data class AllEpisodeResponse(
    val info: PageInfo,
    val results: List<EpisodeByIdResponse> = emptyList()
)
