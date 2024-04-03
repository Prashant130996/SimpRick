package com.example.simprick.ui.characters.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simprick.domain.models.Character
import com.example.simprick.network.SimpleMortyCache
import com.example.simprick.ui.characters.CharRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharDetailsViewModel @Inject constructor(private val charRepository: CharRepository) :
    ViewModel() {
    private val _getCharacterLiveData = MutableLiveData<Character?>()
    val getCharacterLiveData: LiveData<Character?> = _getCharacterLiveData

    fun fetchChar(charId: Int) {
        //check the cache for our character
        val cachedCharacter = SimpleMortyCache.characterMap[charId]
        if (cachedCharacter != null) {
            _getCharacterLiveData.postValue(cachedCharacter)
            return
        }
        //otherwise make network call
        viewModelScope.launch {
            val character = charRepository.getCharById(charId)
            _getCharacterLiveData.postValue(character)

            //update cache if non-null character received.
            character?.let { SimpleMortyCache.characterMap[charId] = it }
        }
    }
}