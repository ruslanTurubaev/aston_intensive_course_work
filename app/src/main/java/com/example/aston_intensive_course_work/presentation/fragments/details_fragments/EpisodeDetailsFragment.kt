package com.example.aston_intensive_course_work.presentation.fragments.details_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.aston_intensive_course_work.databinding.CharacterListItemBinding
import com.example.aston_intensive_course_work.databinding.FragmentEpisodeDetailsBinding
import com.example.aston_intensive_course_work.presentation.MainActivity
import com.example.aston_intensive_course_work.presentation.fragments.base_fragments.ItemDetailsBaseFragment
import com.example.aston_intensive_course_work.presentation.models.items_models.CharacterItem
import com.example.aston_intensive_course_work.presentation.models.items_models.EpisodeItem
import com.example.aston_intensive_course_work.presentation.recycler_view.ItemsPagingAdapter
import com.example.aston_intensive_course_work.presentation.recycler_view.ItemsPagingAdapter.ItemsDiffUtils
import com.example.aston_intensive_course_work.presentation.recycler_view.ItemsViewHolder.CharacterViewHolder
import kotlinx.coroutines.launch

class EpisodeDetailsFragment : ItemDetailsBaseFragment<EpisodeItem, CharacterItem>() {

    private var _binding: FragmentEpisodeDetailsBinding? = null
    private val binding get() = _binding!!

    private var episodeItem: EpisodeItem? = null

    private val adapter =
        ItemsPagingAdapter(
            onClick = onCharacterItemClick,
            diffCallback = object : ItemsDiffUtils<CharacterItem>() {},
            viewHolderClass = CharacterViewHolder::class
        ) { inflater, parent, attachToParent ->
            CharacterListItemBinding.inflate(inflater, parent, attachToParent)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        itemDetailsViewModel = ViewModelProvider(
            context as MainActivity,
            viewModelFactory
        )[EpisodeDetailsViewModel::class.java]

        _binding = FragmentEpisodeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (episodeItem == null) {
            episodeItem = mainViewModel.selectedEpisode!!
        }

        if (episodeItem != null) {
            itemDetailsViewModel.onUserSelectItem(item = episodeItem!!)
        }

        setUpFragment(
            adapter = adapter,
            recyclerView = binding.recyclerViewCharactersInEpisode,
            progressBar = binding.progressBar,
            toolbar = binding.toolBarEpisodeDetails,
            swipeRefresher = binding.swipeRefresherCharactersInEpisode
        )

        itemDetailsViewModel.attachedItemsList.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(pagingData = it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun setFields() {
        binding.includedPart.textViewEpisodeName.text = episodeItem?.name
        binding.includedPart.textViewEpisodeAirDateItem.text = episodeItem?.airDate
        binding.includedPart.textViewEpisodeNumberItem.text = episodeItem?.episode
    }
}