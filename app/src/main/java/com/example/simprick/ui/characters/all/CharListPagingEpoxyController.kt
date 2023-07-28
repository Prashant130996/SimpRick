package com.example.simprick.ui.characters.all

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.bumptech.glide.Glide
import com.example.simprick.R
import com.example.simprick.databinding.ModelCharacterListItemBinding
import com.example.simprick.databinding.ModelTitleListTitleBinding
import com.example.simprick.epoxy.LoadingEpoxyModel
import com.example.simprick.epoxy.ViewBindingKotlinModel
import com.example.simprick.model.characters.single.CharacterByIdResponse
import java.util.Locale

class CharListPagingEpoxyController(private val onItemClick: (Int) -> Unit) :
    PagingDataEpoxyController<CharacterByIdResponse>() {

    override fun buildItemModel(currentPosition: Int, item: CharacterByIdResponse?): EpoxyModel<*> {
        return CharListItemEpoxyModel(
            character = item!!,
            onItemClick = onItemClick
        ).id("Character_${item.id}")
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if (models.isEmpty()) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        CharacterTitleEpoxyModel("main_family").id("main_fam_header").addTo(this)
        super.addModels(models.subList(0, 5))
        (models.subList(5, models.size) as List<CharListItemEpoxyModel>)
            .groupBy { it.character.name[0].uppercaseChar() }
            .forEach {
                val character = it.key.toString().uppercase(Locale.getDefault())
                CharacterTitleEpoxyModel(title = character).id(character).addTo(this)
                super.addModels(it.value)
            }
    }

    data class CharListItemEpoxyModel(
        val character: CharacterByIdResponse,
        val onItemClick: (Int) -> Unit
    ) : ViewBindingKotlinModel<ModelCharacterListItemBinding>(R.layout.model_character_list_item) {
        override fun ModelCharacterListItemBinding.bind() {
            charName.text = character.name
            Glide.with(charImage).load(character.image).into(charImage)
            root.setOnClickListener { onItemClick(character.id) }
        }

    }

    data class CharacterTitleEpoxyModel(val title: String) :
        ViewBindingKotlinModel<ModelTitleListTitleBinding>(R.layout.model_title_list_title) {

        override fun ModelTitleListTitleBinding.bind() {
            titleTv.text = title
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }

    }

}