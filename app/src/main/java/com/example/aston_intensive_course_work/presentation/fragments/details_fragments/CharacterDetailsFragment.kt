package com.example.aston_intensive_course_work.presentation.fragments.details_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aston_intensive_course_work.R
import com.example.aston_intensive_course_work.databinding.EpisodeListItemBinding
import com.example.aston_intensive_course_work.databinding.FragmentCharacterDetailsBinding
import com.example.aston_intensive_course_work.presentation.MainActivity
import com.example.aston_intensive_course_work.presentation.fragments.base_fragments.ItemDetailsBaseFragment
import com.example.aston_intensive_course_work.presentation.models.items_models.CharacterItem
import com.example.aston_intensive_course_work.presentation.models.items_models.EpisodeItem
import com.example.aston_intensive_course_work.presentation.models.items_models.LocationSimpleItem
import com.example.aston_intensive_course_work.presentation.recycler_view.ItemsPagingAdapter
import com.example.aston_intensive_course_work.presentation.recycler_view.ItemsPagingAdapter.ItemsDiffUtils
import com.example.aston_intensive_course_work.presentation.recycler_view.ItemsViewHolder.EpisodeViewHolder
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class CharacterDetailsFragment : ItemDetailsBaseFragment<CharacterItem, EpisodeItem>() {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    private var characterItem: CharacterItem? = null

    private val onLocationClick: (LocationSimpleItem) -> Unit = { location ->
        mainViewModel.onUserSelectLocationOnCharacterCard(locationUrl = location.url)
        beginTransaction(fragment = LocationDetailsFragment())
    }

    private val adapter =
        ItemsPagingAdapter(
            onClick = onEpisodeItemClick,
            diffCallback = object : ItemsDiffUtils<EpisodeItem>() {},
            viewHolderClass = EpisodeViewHolder::class
        ) { inflater, parent, attachToParent ->
            EpisodeListItemBinding.inflate(inflater, parent, attachToParent)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        itemDetailsViewModel = ViewModelProvider(
            context as MainActivity,
            viewModelFactory
        )[CharacterDetailsViewModel::class.java]

        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (characterItem == null) {
            characterItem = mainViewModel.selectedCharacter!!
        }

        if (characterItem != null) {
            itemDetailsViewModel.onUserSelectItem(item = characterItem!!)
        }

        setUpFragment(
            adapter = adapter,
            recyclerView = binding.recyclerViewCharacterEpisodes,
            progressBar = binding.progressBar,
            toolbar = binding.toolBarCharacterDetails,
            swipeRefresher = binding.swipeRefresherCharacterEpisodes
        )

        binding.recyclerViewCharacterEpisodes.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        setOnLocationClick()

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
        Picasso.get().load(characterItem?.image).error(R.drawable.no_image)
            .into(binding.imageViewCharacterImage)

        binding.textViewCharacterName.text = characterItem?.name
        binding.textViewCharacterSpecies.text = characterItem?.species
        binding.textViewCharacterGender.text = characterItem?.gender
        binding.textViewCharacterStatus.text = characterItem?.status
        binding.textViewCharacterOrigin.text = characterItem?.origin?.name
        binding.textViewCharacterLocation.text = characterItem?.location?.name

        if (characterItem?.type?.isNotEmpty() == true) {
            binding.textViewCharacterType.text = characterItem?.type
        } else {
            binding.textViewCharacterType.text = resources.getText(R.string.general)
        }
    }

    private fun setOnLocationClick() {
        binding.textViewCharacterLocation.setOnClickListener {
            characterItem?.location?.let { location -> onLocationClick(location) }
        }
        binding.textViewCharacterOrigin.setOnClickListener {
            characterItem?.origin?.let { origin -> onLocationClick(origin) }
        }
    }
}