package com.example.simprick

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.simprick.characters.AllCharViewModel
import com.example.simprick.characters.AllCharacterAdapter
import com.example.simprick.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //private val charDetailsViewModel by viewModels<CharDetailsViewModel>()

    private val allCharViewModel by viewModels<AllCharViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /*val epoxyController = CharacterDetailEpoxyController()
        charDetailsViewModel.fetchChar(13)
        charDetailsViewModel.characterByIdLiveData.observe(this) { response ->
            epoxyController.charByIdResponse = response
            if (response == null) {
                showToast("Unsuccessful network call")
                return@observe
            }
        }
        binding.epoxyRv.setControllerAndBuildModels(epoxyController)*/

        val allCharacterAdapter = AllCharacterAdapter()
        binding.bindAdapter(allCharacterAdapter = allCharacterAdapter)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                allCharViewModel.flow.collectLatest {
                    allCharacterAdapter.submitData(it)
                }
            }
        }
    }
}

private fun ActivityMainBinding.bindAdapter(allCharacterAdapter: AllCharacterAdapter) {
    allCharListRv.adapter = allCharacterAdapter
    val decoration = DividerItemDecoration(allCharListRv.context, DividerItemDecoration.VERTICAL)
    allCharListRv.addItemDecoration(decoration)
}