package com.example.aston_intensive_course_work.presentation.fragments.filters_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.aston_intensive_course_work.databinding.BottomSheetCharacterFiltersBinding
import com.example.aston_intensive_course_work.presentation.MainActivity
import com.example.aston_intensive_course_work.presentation.fragments.base_fragments.FiltersBaseFragment
import com.example.aston_intensive_course_work.presentation.fragments.list_fragments.CharactersListViewModel
import com.example.aston_intensive_course_work.presentation.models.filter_models.CharacterFilters

class CharacterFiltersFragment : FiltersBaseFragment() {

    private lateinit var characterListViewModel: CharactersListViewModel

    private var _binding: BottomSheetCharacterFiltersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        characterListViewModel = ViewModelProvider(
            context as MainActivity,
            viewModelFactory
        )[CharactersListViewModel::class.java]

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.liveDataGenderFilters.observe(viewLifecycleOwner) {
            binding.characterGenderFilter.setText(it)
        }

        mainViewModel.liveDataStatusFilters.observe(viewLifecycleOwner) {
            binding.characterStatusFilter.setText(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        _binding = BottomSheetCharacterFiltersBinding.inflate(inflater, container, false)
        return _binding as BottomSheetCharacterFiltersBinding
    }

    override fun setFilters() {
        binding.characterNameFilter.setText(characterListViewModel.charactersFilterSet.name)
        binding.characterStatusFilter.setText(characterListViewModel.charactersFilterSet.status)
        binding.characterSpeciesFilter.setText(characterListViewModel.charactersFilterSet.species)
        binding.characterTypeFilter.setText(characterListViewModel.charactersFilterSet.type)
        binding.characterGenderFilter.setText(characterListViewModel.charactersFilterSet.gender)

        binding.characterGenderFilter.showSoftInputOnFocus = false
        binding.characterStatusFilter.showSoftInputOnFocus = false

        binding.characterGenderFilter.setOnFocusChangeListener { view, b ->
            if (b) CharacterGenderFiltersFragment().showFilterFragment(requireActivity())
        }

        binding.characterStatusFilter.setOnFocusChangeListener { view, b ->
            if (b) CharacterStatusFilterFragment().showFilterFragment(requireActivity())
        }
    }

    override fun clearFilters() {
        binding.characterNameFilter.text.clear()
        binding.characterStatusFilter.text.clear()
        binding.characterSpeciesFilter.text.clear()
        binding.characterTypeFilter.text.clear()
        binding.characterGenderFilter.text.clear()

        mainViewModel.onUserSelectGenderFilter(genderFilter = "")
        mainViewModel.onUserSelectStatusFilter(statusFilter = "")
    }

    override fun setUsersFilters() {

        val filters = CharacterFilters(
            name = binding.characterNameFilter.text.toString(),
            status = binding.characterStatusFilter.text.toString(),
            species = binding.characterSpeciesFilter.text.toString(),
            type = binding.characterTypeFilter.text.toString(),
            gender = binding.characterGenderFilter.text.toString()
        )

        characterListViewModel.onUserFiltersApply(filters = filters)
        closeFilterFragment()
    }
}