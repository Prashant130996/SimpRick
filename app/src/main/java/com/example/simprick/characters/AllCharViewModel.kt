package com.example.simprick.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.simprick.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllCharViewModel @Inject constructor(private val charRepository: CharRepository) :
    ViewModel() {

    val flow = Pager(
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PREFETCH_DISTANCE,
            enablePlaceholders = false
        ), pagingSourceFactory = { charRepository.charPagingSource() }
    ).flow.cachedIn(viewModelScope)
}