package com.example.simprick.characters.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simprick.characters.CharRepository
import com.example.simprick.model.characters.single.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharDetailsViewModel @Inject constructor(private val charRepository: CharRepository) :
    ViewModel() {
    private val _characterByIdLiveData = MutableLiveData<Character?>()
    val characterByIdLiveData: LiveData<Character?> = _characterByIdLiveData

    fun fetchChar(charId: Int) = viewModelScope.launch {
        val char = charRepository.getCharById(charId)
        _characterByIdLiveData.postValue(char)
    }
}