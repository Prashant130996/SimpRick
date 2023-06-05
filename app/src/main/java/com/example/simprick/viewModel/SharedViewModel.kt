package com.example.rickmorty.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simprick.model.charById.CharByIdResponse
import com.example.simprick.characters.CharRepository
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

    /*private val sharedRepo = CharRepository()

    private val _charByIdLiveData = MutableLiveData<CharByIdResponse>()
    val charByIdLiveData: LiveData<CharByIdResponse?> = _charByIdLiveData

    fun refreshCharacter(charId:Int){
        viewModelScope.launch {
            val response = sharedRepo.getCharById(charId)
            _charByIdLiveData.postValue(response)
        }
    }*/
}