package com.example.simprick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.simprick.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.example.rickmorty.utils.showToast
import com.example.simprick.characters.AllCharViewModel
import com.example.simprick.characters.CharDetailsViewModel
import com.example.simprick.characters.CharacterDetailEpoxyController
import com.example.simprick.model.charById.CharByIdResponse
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val charDetailsViewModel by viewModels<CharDetailsViewModel>()
    private val allCharViewModel by viewModels<AllCharViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val epoxyController = CharacterDetailEpoxyController()
        charDetailsViewModel.fetchChar(13)
        charDetailsViewModel.characterByIdLiveData.observe(this) { response ->
            epoxyController.charByIdResponse = response
            if (response == null) {
                showToast("Unsuccessful network call")
                return@observe
            }
        }
        binding.epoxyRv.setControllerAndBuildModels(epoxyController)
        lifecycleScope.launch {
            allCharViewModel.flow.collectLatest { pagingData: PagingData<CharByIdResponse> ->
                Log.d(MainActivity::class.simpleName, "onCreate: $pagingData")
            }
        }
    }
}