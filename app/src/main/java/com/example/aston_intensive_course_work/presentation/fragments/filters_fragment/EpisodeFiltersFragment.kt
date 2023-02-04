package com.example.aston_intensive_course_work.presentation.fragments.filters_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.aston_intensive_course_work.databinding.BottomSheetEpisodeFiltersBinding
import com.example.aston_intensive_course_work.presentation.MainActivity
import com.example.aston_intensive_course_work.presentation.fragments.base_fragments.FiltersBaseFragment
import com.example.aston_intensive_course_work.presentation.fragments.list_fragments.EpisodesListViewModel
import com.example.aston_intensive_course_work.presentation.models.filter_models.EpisodeFilter

class EpisodeFiltersFragment : FiltersBaseFragment() {

    private lateinit var episodeListViewModel: EpisodesListViewModel

    private var _binding: BottomSheetEpisodeFiltersBinding? = null
    private val binding get() = _binding!!

    override val expandedRatio = 0.6F

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        episodeListViewModel = ViewModelProvider(
            context as MainActivity,
            viewModelFactory
        )[EpisodesListViewModel::class.java]

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        _binding = BottomSheetEpisodeFiltersBinding.inflate(inflater, container, false)
        return _binding as BottomSheetEpisodeFiltersBinding
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun setFilters() {
        binding.episodeNameFilter.setText(episodeListViewModel.filtersSet.name)
        binding.episodeNumberFilter.setText(episodeListViewModel.filtersSet.episode)
    }

    override fun clearFilters() {
        binding.episodeNameFilter.text.clear()
        binding.episodeNumberFilter.text.clear()
    }

    override fun setUsersFilters() {
        val filters = EpisodeFilter(
            name = binding.episodeNameFilter.text.toString(),
            episode = binding.episodeNumberFilter.text.toString()
        )
        episodeListViewModel.onUserFiltersApply(filters = filters)

        closeFilterFragment()
    }
}