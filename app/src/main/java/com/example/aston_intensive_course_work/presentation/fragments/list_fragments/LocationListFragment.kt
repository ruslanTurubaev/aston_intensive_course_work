package com.example.aston_intensive_course_work.presentation.fragments.list_fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.aston_intensive_course_work.databinding.LocationListItemBinding
import com.example.aston_intensive_course_work.presentation.MainActivity
import com.example.aston_intensive_course_work.presentation.fragments.base_fragments.ItemsListBaseFragment
import com.example.aston_intensive_course_work.presentation.fragments.filters_fragment.LocationFiltersFragment
import com.example.aston_intensive_course_work.presentation.models.filter_models.LocationFilters
import com.example.aston_intensive_course_work.presentation.models.items_models.LocationItem
import com.example.aston_intensive_course_work.presentation.recycler_view.ItemsPagingAdapter
import com.example.aston_intensive_course_work.presentation.recycler_view.ItemsPagingAdapter.ItemsDiffUtils
import com.example.aston_intensive_course_work.presentation.recycler_view.ItemsViewHolder.LocationViewHolder
import kotlinx.coroutines.launch

class LocationListFragment : ItemsListBaseFragment<LocationItem, LocationFilters>() {

    private val adapter =
        ItemsPagingAdapter(
            onClick = onLocationItemClick,
            diffCallback = object : ItemsDiffUtils<LocationItem>() {},
            viewHolderClass = LocationViewHolder::class
        ) { inflater, parent, attachToParent ->
            LocationListItemBinding.inflate(inflater, parent, attachToParent)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemsListViewModel = ViewModelProvider(
            context as MainActivity,
            viewModelFactory
        )[LocationListViewModel::class.java]
        setUpFragment(adapter = adapter)

        itemsListViewModel.itemsList.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
    }

    override fun showFiltersFragment() {
        LocationFiltersFragment().showFilterFragment(requireActivity())
    }
}