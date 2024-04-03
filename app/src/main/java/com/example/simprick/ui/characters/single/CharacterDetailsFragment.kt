package com.example.simprick.ui.characters.single

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.simprick.databinding.FragmentCharacterDetailsBinding
import com.example.simprick.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    private val charDetailsViewModel by viewModels<CharDetailsViewModel>()
    private val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        //setClickListener()
    }

    /*private fun setClickListener() = binding.run {
        toolbar.goBackIv.setOnClickListener { findNavController().popBackStack() }
    }*/

    private fun initViews() {
        val epoxyController = CharacterDetailEpoxyController()

        charDetailsViewModel.fetchChar(args.characterId)
        charDetailsViewModel.getCharacterLiveData.observe(viewLifecycleOwner) { character ->
            epoxyController.character = character
            if (character == null) {
                toast("Unsuccessful network call")
                findNavController().navigateUp()
                return@observe
            }
        }
        binding.epoxyRv.setControllerAndBuildModels(epoxyController)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}