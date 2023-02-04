package com.example.aston_intensive_course_work.presentation.fragments.base_fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.aston_intensive_course_work.R
import com.example.aston_intensive_course_work.databinding.FiltersButtonsBinding
import com.example.aston_intensive_course_work.presentation.MainActivity
import com.example.aston_intensive_course_work.presentation.MainViewModel
import com.example.aston_intensive_course_work.presentation.app.App
import com.example.aston_intensive_course_work.presentation.di.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

abstract class FiltersBaseFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var mainViewModel: MainViewModel
    private lateinit var behavior: BottomSheetBehavior<FrameLayout>

    open val expandedRatio = 0.75F

    override fun getTheme() = R.style.BottomSheetFragmentStyle

    private lateinit var filtersButtonsBinding: FiltersButtonsBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (context.applicationContext as App).appComponent.inject(this)
        mainViewModel =
            ViewModelProvider(context as MainActivity, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = onCreateViewBinding(inflater = inflater, container = container)
        filtersButtonsBinding = FiltersButtonsBinding.bind(binding.root)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheetLayout =
            this.dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout

        behavior = BottomSheetBehavior.from(bottomSheetLayout)

        bottomSheetLayout.layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT
        behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        behavior.halfExpandedRatio = expandedRatio

        setFilters()
        setOnButtonsClickListener()
    }

    private fun setOnButtonsClickListener() {
        filtersButtonsBinding.buttonClear.setOnClickListener { clearFilters() }
        filtersButtonsBinding.buttonAccept.setOnClickListener { setUsersFilters() }
    }

    fun showFilterFragment(activity: FragmentActivity) {
        this.show(
            activity.supportFragmentManager,
            createTag(this::class.java)
        )
    }

    fun closeFilterFragment() {
        this.dismiss()
    }

    private fun createTag(clazz: Class<*>): String {
        return clazz.name + "@" + clazz.hashCode()
    }

    abstract fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding
    abstract fun setFilters()
    abstract fun clearFilters()
    abstract fun setUsersFilters()
}