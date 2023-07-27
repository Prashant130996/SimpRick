package com.example.simprick.ui.characters.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.simprick.databinding.FragmentAllCharactersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private var _binding: FragmentAllCharactersBinding? = null
    private val binding get() = _binding!!

    private val allCharViewModel by viewModels<AllCharViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        _binding = FragmentAllCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val allCharEpoxyController = CharListPagingEpoxyController(::onCharacterSelected)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                allCharViewModel.flow.collectLatest {
                    allCharEpoxyController.submitData(it)
                }
            }
        }
        binding.allCharRv.setController(allCharEpoxyController)
    }

    private fun onCharacterSelected(characterId: Int) {
        val action =
            CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailsFragment(
                characterId = characterId
            )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}