package com.example.aston_intensive_course_work.presentation.recycler_view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.aston_intensive_course_work.R
import com.example.aston_intensive_course_work.databinding.CharacterListItemBinding
import com.example.aston_intensive_course_work.databinding.EpisodeListItemBinding
import com.example.aston_intensive_course_work.databinding.LocationListItemBinding
import com.example.aston_intensive_course_work.presentation.interfaces.Item
import com.example.aston_intensive_course_work.presentation.models.items_models.CharacterItem
import com.example.aston_intensive_course_work.presentation.models.items_models.EpisodeItem
import com.example.aston_intensive_course_work.presentation.models.items_models.LocationItem
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

sealed class ItemsViewHolder<I : Item>(open val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: I)

    class CharacterViewHolder(override val binding: CharacterListItemBinding) :
        ItemsViewHolder<CharacterItem>(binding = binding) {
        override fun bind(item: CharacterItem) {
            item.let {
                binding.progressBar.visibility = View.VISIBLE

                Picasso.get().load(item.image).error(R.drawable.no_image)
                    .into(binding.imageViewCharacterImage, object : Callback {
                        override fun onSuccess() {
                            binding.progressBar.visibility = View.GONE
                        }

                        override fun onError(e: Exception?) {
                            binding.progressBar.visibility = View.GONE
                        }
                    })

                binding.textViewCharacterName.text = item.name
                binding.textViewCharacterSpecies.text = item.species
                binding.textViewCharacterGender.text = item.gender
                binding.textViewCharacterStatus.text = item.status
            }
        }
    }

    class LocationViewHolder(override val binding: LocationListItemBinding) :
        ItemsViewHolder<LocationItem>(binding = binding) {
        override fun bind(item: LocationItem) {
            item.let {
                binding.textViewLocationName.text = item.name
                binding.textViewLocationItemType.text = item.type
                binding.textViewLocationItemDimension.text = item.dimension
            }
        }
    }

    class EpisodeViewHolder(override val binding: EpisodeListItemBinding) :
        ItemsViewHolder<EpisodeItem>(binding = binding) {
        override fun bind(item: EpisodeItem) {
            item.let {
                binding.textViewEpisodeName.text = item.name
                binding.textViewEpisodeNumberItem.text = item.episode
                binding.textViewEpisodeAirDateItem.text = item.airDate
            }
        }
    }
}