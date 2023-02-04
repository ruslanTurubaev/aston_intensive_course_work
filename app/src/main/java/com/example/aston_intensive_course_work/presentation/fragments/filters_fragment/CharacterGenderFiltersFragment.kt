package com.example.aston_intensive_course_work.presentation.fragments.filters_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.aston_intensive_course_work.R
import com.example.aston_intensive_course_work.databinding.BottomSheetCharacterGenderFiltersBinding
import com.example.aston_intensive_course_work.presentation.MainActivity
import com.example.aston_intensive_course_work.presentation.fragments.base_fragments.FiltersBaseFragment
import com.example.aston_intensive_course_work.presentation.fragments.list_fragments.CharactersListViewModel

class CharacterGenderFiltersFragment : FiltersBaseFragment() {

    private lateinit var characterListViewModel: CharactersListViewModel

    private var _binding: BottomSheetCharacterGenderFiltersBinding? = null
    private val binding get() = _binding!!

    override val expandedRatio = 0.6f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        characterListViewModel = ViewModelProvider(
            context as MainActivity,
            viewModelFactory
        )[CharactersListViewModel::class.java]

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        _binding = BottomSheetCharacterGenderFiltersBinding.inflate(inflater, container, false)
        return _binding as BottomSheetCharacterGenderFiltersBinding
    }

    override fun setFilters() {
        when (mainViewModel.liveDataGenderFilters.value) {
            getString(R.string.female) -> binding.radioButtonGenderFemale.isChecked = true
            getString(R.string.male) -> binding.radioButtonGenderMale.isChecked = true
            getString(R.string.genderless) -> binding.radioButtonGenderGenderless.isChecked = true
            getString(R.string.unknown) -> binding.radioButtonGenderUnknown.isChecked = true
        }
    }

    override fun clearFilters() {
        binding.radioGroupCharactersGenderFilter.clearCheck()
    }

    override fun setUsersFilters() {
        val radioButton =
            binding.root.findViewById<RadioButton>(binding.radioGroupCharactersGenderFilter.checkedRadioButtonId)
        if (radioButton != null) {
            mainViewModel.onUserSelectGenderFilter(genderFilter = radioButton.text.toString())
        } else {
            mainViewModel.onUserSelectGenderFilter(genderFilter = "")
        }

        closeFilterFragment()
    }
}