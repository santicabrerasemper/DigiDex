package com.example.digidex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.digidex.databinding.ItemDigimonBinding
import com.example.digidex.models.Digimon

class DigimonAdapter(private val onClick: (Digimon) -> Unit) :
    ListAdapter<Digimon, DigimonAdapter.DigimonViewHolder>(DigimonDiffCallback()) {

    private var originalList: List<Digimon> = emptyList()

    fun submitOriginalList(list: List<Digimon>) {
        originalList = list
        submitList(list)
    }

    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            originalList
        } else {
            originalList.filter { digimon ->
                digimon.name.contains(query, ignoreCase = true)
            }
        }
        submitList(filteredList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DigimonViewHolder {
        val binding = ItemDigimonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DigimonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DigimonViewHolder, position: Int) {
        val digimon = getItem(position)
        holder.bind(digimon)
        holder.itemView.setOnClickListener {
            onClick(digimon)
        }
        holder.binding.favoriteButton.setOnClickListener {
            digimon.isFavorite = !digimon.isFavorite //
            notifyItemChanged(position)
    }
    }

    class DigimonViewHolder(val binding: ItemDigimonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(digimon: Digimon) {
            binding.digimonName.text = digimon.name
            binding.digimonLevel.text = digimon.level
            Glide.with(binding.root.context)
                .load(digimon.img)
                .circleCrop()
                .into(binding.digimonImage)

            if (digimon.isFavorite) {
                binding.favoriteButton.setImageResource(R.drawable.ic_favorite_filled)
            } else {
                binding.favoriteButton.setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }

    class DigimonDiffCallback : DiffUtil.ItemCallback<Digimon>() {
        override fun areItemsTheSame(oldItem: Digimon, newItem: Digimon): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Digimon, newItem: Digimon): Boolean {
            return oldItem == newItem
        }
    }
}
