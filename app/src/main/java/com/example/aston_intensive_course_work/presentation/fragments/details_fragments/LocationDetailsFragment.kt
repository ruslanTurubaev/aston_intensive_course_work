package com.example.aston_intensive_course_work.presentation.fragments.details_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.aston_intensive_course_work.databinding.CharacterListItemBinding
import com.example.aston_intensive_course_work.databinding.FragmentLocationDetailsBinding
import com.example.aston_intensive_course_work.presentation.MainActivity
import com.example.aston_intensive_course_work.presentation.fragments.base_fragments.ItemDetailsBaseFragment
import com.example.aston_intensive_course_work.presentation.models.items_models.CharacterItem
import com.example.aston_intensive_course_work.presentation.models.items_models.LocationItem
import com.example.aston_intensive_course_work.presentation.recycler_view.ItemsPagingAdapter
import com.example.aston_intensive_course_work.presentation.recycler_view.ItemsPagingAdapter.ItemsDiffUtils
import com.example.aston_intensive_course_work.presentation.recycler_view.ItemsViewHolder.CharacterViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationDetailsFragment : ItemDetailsBaseFragment<LocationItem, CharacterItem>() {

    private var _binding: FragmentLocationDetailsBinding? = null
    private val binding get() = _binding!!
    private var locationItem: LocationItem? = null

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
        )[LocationDetailsViewModel::class.java]
        _binding = FragmentLocationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (locationItem == null) {
            binding.progressBar.visibility = View.VISIBLE
            mainViewModel.selectedLocation.observe(viewLifecycleOwner) { location ->
                if (location != null) {
                    binding.progressBar.visibility = View.INVISIBLE
                    locationItem = mainViewModel.selectedLocation.value!!
                    startFragment()
                }
            }
        } else {
            startFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun startFragment() {
        itemDetailsViewModel.onUserSelectItem(locationItem!!)

        setUpFragment(
            adapter = adapter,
            recyclerView = binding.recyclerViewLocationResidents,
            progressBar = binding.progressBar,
            toolbar = binding.toolBarLocationDetails,
            swipeRefresher = binding.swipeRefresherLocationResidents
        )

        itemDetailsViewModel.attachedItemsList.observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.Main).launch {
                adapter.submitData(it)
            }
        }
    }

    override fun setFields() {
        binding.includedPart.textViewLocationName.text = locationItem?.name
        binding.includedPart.textViewLocationItemType.text = locationItem?.type
        binding.includedPart.textViewLocationItemDimension.text = locationItem?.dimension
    }
}