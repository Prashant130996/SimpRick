package com.example.simprick.characters

import com.airbnb.epoxy.EpoxyController
import com.bumptech.glide.Glide
import com.example.simprick.R
import com.example.simprick.databinding.ModelCharacterDetailsDataPointBinding
import com.example.simprick.databinding.ModelCharacterDetailsHeaderBinding
import com.example.simprick.databinding.ModelCharacterDetailsImageBinding
import com.example.simprick.epoxy.LoadingEpoxyModel
import com.example.simprick.epoxy.ViewBindingKotlinModel
import com.example.simprick.model.charById.CharByIdResponse

class CharacterDetailEpoxyController : EpoxyController() {

    private var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var charByIdResponse: CharByIdResponse? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    override fun buildModels() {
        if (isLoading) {
            LoadingEpoxyModel().id("Loader").addTo(this)
            return
        }
        if (charByIdResponse == null) {
            return
        }
        HeaderEpoxyModel(
            charByIdResponse!!.name, charByIdResponse!!.gender, charByIdResponse!!.status
        ).id("header").addTo(this)
        ImageEpoxyModel(charByIdResponse!!.image).id("image").addTo(this)
        DataPointEpoxyModel("Origin", charByIdResponse!!.origin.name).id("data_1").addTo(this)
        DataPointEpoxyModel("Species", charByIdResponse!!.species).id("data_2").addTo(this)
    }

    data class HeaderEpoxyModel(
        val name: String,
        val gender: String,
        val lifeStatus: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header) {
        override fun ModelCharacterDetailsHeaderBinding.bind() {
            nameTextView.text = name
            if (gender.equals("male", true)) {
                genderImageView.setImageResource(R.drawable.baseline_male_24)
            } else genderImageView.setImageResource(R.drawable.baseline_female_24)
            aliveTextView.text = lifeStatus
        }
    }

    data class ImageEpoxyModel(
        val imageUrl: String,
    ) : ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(R.layout.model_character_details_image) {
        override fun ModelCharacterDetailsImageBinding.bind() {
            Glide.with(headerImageView).load(imageUrl).into(headerImageView)
        }
    }

    data class DataPointEpoxyModel(
        val title: String,
        val description: String,
    ) : ViewBindingKotlinModel<ModelCharacterDetailsDataPointBinding>(R.layout.model_character_details_data_point) {
        override fun ModelCharacterDetailsDataPointBinding.bind() {
            labelTextView.text = title
            textView.text = description
        }
    }
}