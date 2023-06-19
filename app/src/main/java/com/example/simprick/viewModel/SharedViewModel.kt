package com.example.rickmorty.viewModel

import androidx.lifecycle.ViewModel

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