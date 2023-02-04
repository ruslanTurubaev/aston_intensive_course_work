package com.example.aston_intensive_course_work.presentation.fragments.list_fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.aston_intensive_course_work.databinding.CharacterListItemBinding
import com.example.aston_intensive_course_work.presentation.MainActivity
import com.example.aston_intensive_course_work.presentation.fragments.base_fragments.ItemsListBaseFragment
import com.example.aston_intensive_course_work.presentation.fragments.filters_fragment.CharacterFiltersFragment
import com.example.aston_intensive_course_work.presentation.models.filter_models.CharacterFilters
import com.example.aston_intensive_course_work.presentation.models.items_models.CharacterItem
import com.example.aston_intensive_course_work.presentation.recycler_view.ItemsPagingAdapter
import com.example.aston_intensive_course_work.presentation.recycler_view.ItemsPagingAdapter.ItemsDiffUtils
import com.example.aston_intensive_course_work.presentation.recycler_view.ItemsViewHolder.CharacterViewHolder
import kotlinx.coroutines.launch

class CharactersListFragment : ItemsListBaseFragment<CharacterItem, CharacterFilters>() {

    private val adapter =
        ItemsPagingAdapter(
            onClick = onCharacterItemClick,
            diffCallback = object : ItemsDiffUtils<CharacterItem>() {},
            viewHolderClass = CharacterViewHolder::class
        ) { inflater, parent, attachToParent ->
            CharacterListItemBinding.inflate(inflater, parent, attachToParent)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemsListViewModel = ViewModelProvider(
            context as MainActivity,
            viewModelFactory
        )[CharactersListViewModel::class.java]

        setUpFragment(adapter = adapter)

        itemsListViewModel.itemsList.observe(viewLifecycleOwner) {

            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
    }

    override fun showFiltersFragment() {
        CharacterFiltersFragment().showFilterFragment(requireActivity())
    }
}