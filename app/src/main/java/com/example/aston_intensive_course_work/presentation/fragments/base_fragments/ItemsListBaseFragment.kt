package com.example.aston_intensive_course_work.presentation.fragments.base_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aston_intensive_course_work.databinding.FragmentListBinding
import com.example.aston_intensive_course_work.presentation.base_view_models.ItemsListBaseViewModel
import com.example.aston_intensive_course_work.presentation.interfaces.Filter
import com.example.aston_intensive_course_work.presentation.interfaces.Item

abstract class ItemsListBaseFragment<I : Item, F : Filter> : BaseFragment() {

    protected lateinit var itemsListViewModel: ItemsListBaseViewModel<I, F>

    private var _binding: FragmentListBinding? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected fun <VH : RecyclerView.ViewHolder> setUpFragment(
        adapter: PagingDataAdapter<I, VH>,
    ) {
        showLoadingStatus(
            adapter = adapter,
            recyclerView = binding.recyclerView,
            progressBar = binding.progressBar
        )
        setUpRecyclerView(adapter = adapter)
        setUpSwipeRefresher(
            adapter = adapter,
            swipeRefresher = binding.swipeRefresher
        )
        binding.imageButtonFilter.setOnClickListener { showFiltersFragment() }
        setUpSearchView()
    }

    private fun <VH : RecyclerView.ViewHolder> setUpRecyclerView(
        adapter: PagingDataAdapter<I, VH>
    ) {
        binding.recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

    private fun setUpSearchView() {
        binding.searchView.setQuery(itemsListViewModel.getCurrentQuery(), false)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            private val DELAY = 1000L
            private var lastQueryInputTime = 0L

            override fun onQueryTextSubmit(query: String?): Boolean {
                return search(query)
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val currentTime = System.currentTimeMillis()

                return if (lastQueryInputTime == 0L || currentTime - lastQueryInputTime > DELAY) {
                    lastQueryInputTime = currentTime
                    search(newText)
                } else {
                    false
                }
            }
        })
    }

    private fun search(
        query: String?
    ): Boolean {
        if (query != null) itemsListViewModel.onUserQueryInput(query = query)
        return true
    }

    override fun onErrorOccur(error: Throwable) {
        itemsListViewModel.onErrorOccur(error = error)
    }

    abstract fun showFiltersFragment()
}