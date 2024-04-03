package com.example.simprick.ui.episodes

import com.example.simprick.domain.models.Episode

sealed class EpisodesUiModel {
    class Item(val episode: Episode) : EpisodesUiModel()
    class Header(val text: String) : EpisodesUiModel()
}