package com.example.simprick.characters.all

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simprick.databinding.CharItemViewHolderBinding
import com.example.simprick.model.characters.single.Character

class AllCharacterAdapter :
    PagingDataAdapter<Character, AllCharacterAdapter.CharacterViewHolder>(
        CHARACTER_DIFF_CALLBACK
    ) {
    inner class CharacterViewHolder(private val binding: CharItemViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Character?) {
            binding.apply {
                Glide.with(headerImageView).load(item?.image).into(headerImageView)
                charName.text = item?.name
            }
        }
    }

    companion object {
        private val CHARACTER_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Character, newItem: Character) =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            CharItemViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}