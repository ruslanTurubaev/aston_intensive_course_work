package com.example.aston_intensive_course_work.presentation.fragments.filters_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.aston_intensive_course_work.R
import com.example.aston_intensive_course_work.databinding.BottomSheetCharacterStatusFilterBinding
import com.example.aston_intensive_course_work.presentation.MainActivity
import com.example.aston_intensive_course_work.presentation.fragments.base_fragments.FiltersBaseFragment
import com.example.aston_intensive_course_work.presentation.fragments.list_fragments.CharactersListViewModel

class CharacterStatusFilterFragment : FiltersBaseFragment() {

    private lateinit var characterListViewModel: CharactersListViewModel

    private var _binding: BottomSheetCharacterStatusFilterBinding? = null
    private val binding get() = _binding!!

    override val expandedRatio = 0.5f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        characterListViewModel = ViewModelProvider(
            context as MainActivity, viewModelFactory
        )[CharactersListViewModel::class.java]

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        _binding = BottomSheetCharacterStatusFilterBinding.inflate(inflater, container, false)
        return _binding as BottomSheetCharacterStatusFilterBinding
    }

    override fun setFilters() {
        when (mainViewModel.liveDataStatusFilters.value) {
            getString(R.string.alive) -> binding.radioButtonStatusAlive.isChecked = true
            getString(R.string.dead) -> binding.radioButtonStatusDead.isChecked = true
            getString(R.string.unknown) -> binding.radioButtonStatusUnknown.isChecked = true
        }
    }

    override fun clearFilters() {
        binding.radioGroupCharactersStatusFilter.clearCheck()
    }

    override fun setUsersFilters() {
        val radioButton =
            binding.root.findViewById<RadioButton>(binding.radioGroupCharactersStatusFilter.checkedRadioButtonId)
        if (radioButton != null) {
            mainViewModel.onUserSelectStatusFilter(statusFilter = radioButton.text.toString())
        } else {
            mainViewModel.onUserSelectStatusFilter(statusFilter = "")
        }

        closeFilterFragment()
    }
}