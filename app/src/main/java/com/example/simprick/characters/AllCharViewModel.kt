package com.example.simprick.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.simprick.utils.Constants
import javax.inject.Inject

class AllCharViewModel @Inject constructor(private val charRepository: CharRepository) :
    ViewModel() {

    val flow = Pager(
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        CharPagingSource(charRepository)
    }.flow.cachedIn(viewModelScope)
}