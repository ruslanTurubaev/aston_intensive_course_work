package com.example.aston_intensive_course_work.presentation.fragments.base_fragments

import android.widget.ProgressBar
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.aston_intensive_course_work.R
import com.example.aston_intensive_course_work.presentation.base_view_models.ItemDetailsBaseViewModel
import com.example.aston_intensive_course_work.presentation.custom_view.CurtainView
import com.example.aston_intensive_course_work.presentation.find
import com.example.aston_intensive_course_work.presentation.interfaces.Item
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class ItemDetailsBaseFragment<I : Item, SI : Item> : BaseFragment() {

    protected lateinit var itemDetailsViewModel: ItemDetailsBaseViewModel<I, SI>

    protected fun <VH : RecyclerView.ViewHolder> setUpFragment(
        adapter: PagingDataAdapter<SI, VH>,
        recyclerView: RecyclerView,
        progressBar: ProgressBar,
        toolbar: Toolbar,
        swipeRefresher: SwipeRefreshLayout
    ) {
        setUpToolBar(toolbar = toolbar)
        setFields()
        setUpRecyclerView(adapter = adapter, recyclerView = recyclerView)
        setUpSwipeRefresher(
            adapter = adapter,
            swipeRefresher = swipeRefresher
        )
        showLoadingStatus(adapter = adapter, recyclerView = recyclerView, progressBar = progressBar)
    }

    private fun setUpToolBar(toolbar: Toolbar) {
        requireActivity().setActionBar(toolbar)
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    protected open fun <VH : RecyclerView.ViewHolder> setUpRecyclerView(
        adapter: PagingDataAdapter<SI, VH>,
        recyclerView: RecyclerView
    ) {
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    override fun beginTransaction(fragment: Fragment) {
        viewLifecycleOwner.lifecycleScope.launch {
            val curtain by requireActivity().find<CurtainView>(R.id.curtain_view)
            curtain.runAnimation()

            launch {
                delay(curtain.animationDuration)

                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    replace(R.id.fragment_container_main, fragment)
                    addToBackStack(null)
                }
            }
        }
    }

    override fun onErrorOccur(error: Throwable) {
        itemDetailsViewModel.onErrorOccur(error = error)
    }

    abstract fun setFields()
}