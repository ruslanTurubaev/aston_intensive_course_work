package com.example.aston_intensive_course_work.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.example.aston_intensive_course_work.R
import com.example.aston_intensive_course_work.databinding.FragmentItemsListBinding
import com.example.aston_intensive_course_work.presentation.fragments.list_fragments.CharactersListFragment
import com.example.aston_intensive_course_work.presentation.fragments.list_fragments.EpisodesListFragment
import com.example.aston_intensive_course_work.presentation.fragments.list_fragments.LocationListFragment

class ItemsListFragment : Fragment() {

    private var _binding: FragmentItemsListBinding? = null
    private val binding get() = _binding!!
    private var isJustStarted = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isJustStarted && savedInstanceState == null) {
            replaceFragment(fragment = CharactersListFragment())
        }

        binding.bottomNavigationMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_characters -> replaceFragment(fragment = CharactersListFragment())
                R.id.menu_locations -> replaceFragment(fragment = LocationListFragment())
                R.id.menu_episodes -> replaceFragment(fragment = EpisodesListFragment())
            }
            true
        }
    }

    override fun onStop() {
        super.onStop()
        isJustStarted = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.commit {
            setReorderingAllowed(true)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            replace(R.id.fragment_container, fragment)
        }
    }
}