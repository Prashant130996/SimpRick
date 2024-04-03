package com.example.simprick.ui.episodes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.example.simprick.R
import com.example.simprick.databinding.FragmentAllEpisodesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllEpisodesFragment : Fragment(R.layout.fragment_all_episodes) {

    private var _binding: FragmentAllEpisodesBinding? = null
    private val binding: FragmentAllEpisodesBinding by lazy { _binding!! }

    private val allEpisodesViewModel: AllEpisodesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAllEpisodesBinding.bind(view)

        val episodeListEpoxyController = EpisodeListEpoxyController()

        viewLifecycleOwner.lifecycleScope.launch {
            allEpisodesViewModel.flow.collectLatest { value: PagingData<EpisodesUiModel> ->
                episodeListEpoxyController.submitData(value)
            }
        }
        binding.episodeRv.setController(episodeListEpoxyController)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}