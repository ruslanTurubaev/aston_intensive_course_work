package com.example.aston_intensive_course_work.presentation.fragments.filters_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.aston_intensive_course_work.databinding.BottomSheetLocationFiltersBinding
import com.example.aston_intensive_course_work.presentation.MainActivity
import com.example.aston_intensive_course_work.presentation.fragments.base_fragments.FiltersBaseFragment
import com.example.aston_intensive_course_work.presentation.fragments.list_fragments.LocationListViewModel
import com.example.aston_intensive_course_work.presentation.models.filter_models.LocationFilters

class LocationFiltersFragment : FiltersBaseFragment() {

    private lateinit var locationListViewModel: LocationListViewModel

    private var _binding: BottomSheetLocationFiltersBinding? = null
    private val binding get() = _binding!!

    override val expandedRatio = 0.6F

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        locationListViewModel = ViewModelProvider(
            context as MainActivity,
            viewModelFactory
        )[LocationListViewModel::class.java]

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        _binding = BottomSheetLocationFiltersBinding.inflate(inflater, container, false)
        return _binding as BottomSheetLocationFiltersBinding
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun setFilters() {
        binding.locationNameFilter.setText(locationListViewModel.filtersSet.name)
        binding.locationTypeFilter.setText(locationListViewModel.filtersSet.type)
        binding.locationDimensionFilter.setText(locationListViewModel.filtersSet.dimension)
    }

    override fun clearFilters() {
        binding.locationNameFilter.text.clear()
        binding.locationTypeFilter.text.clear()
        binding.locationDimensionFilter.text.clear()
    }

    override fun setUsersFilters() {
        val filters = LocationFilters(
            name = binding.locationNameFilter.text.toString(),
            type = binding.locationTypeFilter.text.toString(),
            dimension = binding.locationDimensionFilter.text.toString()
        )

        locationListViewModel.onUserFiltersApply(filters = filters)

        closeFilterFragment()
    }
}