package com.example.aston_intensive_course_work.presentation.fragments.base_fragments

import android.content.Context
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.aston_intensive_course_work.R
import com.example.aston_intensive_course_work.presentation.MainActivity
import com.example.aston_intensive_course_work.presentation.MainViewModel
import com.example.aston_intensive_course_work.presentation.app.App
import com.example.aston_intensive_course_work.presentation.custom_view.CurtainView
import com.example.aston_intensive_course_work.presentation.di.ViewModelFactory
import com.example.aston_intensive_course_work.presentation.find
import com.example.aston_intensive_course_work.presentation.fragments.details_fragments.CharacterDetailsFragment
import com.example.aston_intensive_course_work.presentation.fragments.details_fragments.EpisodeDetailsFragment
import com.example.aston_intensive_course_work.presentation.fragments.details_fragments.LocationDetailsFragment
import com.example.aston_intensive_course_work.presentation.interfaces.Item
import com.example.aston_intensive_course_work.presentation.models.items_models.CharacterItem
import com.example.aston_intensive_course_work.presentation.models.items_models.EpisodeItem
import com.example.aston_intensive_course_work.presentation.models.items_models.LocationItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var mainViewModel: MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (context.applicationContext as App).appComponent.inject(this)
        mainViewModel =
            ViewModelProvider(context as MainActivity, viewModelFactory)[MainViewModel::class.java]
    }

    protected val onCharacterItemClick: (CharacterItem) -> Unit = { character ->
        mainViewModel.onUserSelectCharacter(character)
        beginTransaction(CharacterDetailsFragment())
    }

    protected val onEpisodeItemClick: (EpisodeItem) -> Unit = { episode ->
        mainViewModel.onUserSelectEpisode(episode)
        beginTransaction(EpisodeDetailsFragment())
    }

    protected val onLocationItemClick: (LocationItem) -> Unit = { location ->
        mainViewModel.onUserSelectLocation(location)
        beginTransaction(LocationDetailsFragment())
    }

    open fun beginTransaction(fragment: Fragment) {
        val curtain by requireActivity().find<CurtainView>(R.id.curtain_view)
        curtain.runAnimation()

        viewLifecycleOwner.lifecycleScope.launch {
            delay(curtain.animationDuration)

            parentFragment?.parentFragmentManager?.commit {
                setReorderingAllowed(true)
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                replace(R.id.fragment_container_main, fragment)
                addToBackStack(null)
            }
        }
    }

    protected fun <I : Item, VH : RecyclerView.ViewHolder> showLoadingStatus(
        adapter: PagingDataAdapter<I, VH>,
        recyclerView: RecyclerView,
        progressBar: ProgressBar
    ) {
        adapter.addLoadStateListener { state ->
            val refresh = state.refresh

            progressBar.isVisible = refresh is LoadState.Loading
            recyclerView.isVisible = refresh !is LoadState.Loading

            if (refresh is LoadState.NotLoading && adapter.itemCount == 0) {
                Toast.makeText(requireContext(), R.string.Toast, Toast.LENGTH_SHORT).show()
            }

            if (refresh is LoadState.Error) {
                onErrorOccur(refresh.error)
            }
        }
    }

    protected fun <I : Item, VH : RecyclerView.ViewHolder> setUpSwipeRefresher(
        adapter: PagingDataAdapter<I, VH>,
        swipeRefresher: SwipeRefreshLayout,
    ) {
        swipeRefresher.setOnRefreshListener {
            swipeRefresher.isRefreshing = false
            adapter.refresh()
        }
    }

    abstract fun onErrorOccur(error: Throwable)
}