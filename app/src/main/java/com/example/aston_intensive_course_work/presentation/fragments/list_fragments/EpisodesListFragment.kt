package com.example.aston_intensive_course_work.presentation.fragments.list_fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.aston_intensive_course_work.databinding.EpisodeListItemBinding
import com.example.aston_intensive_course_work.presentation.MainActivity
import com.example.aston_intensive_course_work.presentation.fragments.base_fragments.ItemsListBaseFragment
import com.example.aston_intensive_course_work.presentation.fragments.filters_fragment.EpisodeFiltersFragment
import com.example.aston_intensive_course_work.presentation.models.filter_models.EpisodeFilter
import com.example.aston_intensive_course_work.presentation.models.items_models.EpisodeItem
import com.example.aston_intensive_course_work.presentation.recycler_view.ItemsPagingAdapter
import com.example.aston_intensive_course_work.presentation.recycler_view.ItemsPagingAdapter.ItemsDiffUtils
import com.example.aston_intensive_course_work.presentation.recycler_view.ItemsViewHolder.EpisodeViewHolder
import kotlinx.coroutines.launch

class EpisodesListFragment : ItemsListBaseFragment<EpisodeItem, EpisodeFilter>() {

    private val adapter =
        ItemsPagingAdapter(
            onClick = onEpisodeItemClick,
            diffCallback = object : ItemsDiffUtils<EpisodeItem>() {},
            viewHolderClass = EpisodeViewHolder::class
        ) { inflater, parent, attachToParent ->
            EpisodeListItemBinding.inflate(inflater, parent, attachToParent)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemsListViewModel = ViewModelProvider(
            context as MainActivity,
            viewModelFactory
        )[EpisodesListViewModel::class.java]

        setUpFragment(
            adapter = adapter,
        )

        itemsListViewModel.itemsList.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
    }

    override fun showFiltersFragment() {
        EpisodeFiltersFragment().showFilterFragment(requireActivity())
    }
}