package com.example.simprick.domain.models

data class Episode(
    val id: Int = 0,
    val name: String = "",
    val airDate: String = "",
    val episodeNum: Int = 0,
    val seasonNumber: Int = 0
) {
    fun getFormattedSeason(): String {
        return "Season $seasonNumber Episode $episodeNum"
    }

    fun getFormattedSeasonTruncated(): String {
        return "S. $seasonNumber E. $episodeNum"
    }
}
