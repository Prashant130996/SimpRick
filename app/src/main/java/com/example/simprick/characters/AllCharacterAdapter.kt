package com.example.simprick.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simprick.databinding.CharItemViewHolderBinding
import com.example.simprick.model.charById.CharByIdResponse

class AllCharacterAdapter :
    PagingDataAdapter<CharByIdResponse, AllCharacterAdapter.CharacterViewHolder>(
        CHARACTER_DIFF_CALLBACK
    ) {
    inner class CharacterViewHolder(private val binding: CharItemViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CharByIdResponse?) {
            binding.apply {
                Glide.with(headerImageView).load(item?.image).into(headerImageView)
                charName.text = item?.name
            }
        }
    }

    companion object {
        private val CHARACTER_DIFF_CALLBACK = object : DiffUtil.ItemCallback<CharByIdResponse>() {
            override fun areItemsTheSame(oldItem: CharByIdResponse, newItem: CharByIdResponse) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CharByIdResponse, newItem: CharByIdResponse) =
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