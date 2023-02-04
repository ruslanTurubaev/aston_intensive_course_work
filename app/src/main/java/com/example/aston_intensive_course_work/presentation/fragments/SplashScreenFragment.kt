package com.example.aston_intensive_course_work.presentation.fragments

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.example.aston_intensive_course_work.R
import com.example.aston_intensive_course_work.databinding.FragmentSplashScreenBinding
import com.example.aston_intensive_course_work.presentation.getHalfWindowHeight
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val y = requireContext().getHalfWindowHeight()

        val fadeInAnimation = ObjectAnimator.ofFloat(binding.imageViewPortal, "alpha", 1f, 0f)
        fadeInAnimation.duration = 200

        val translationAnimation =
            ObjectAnimator.ofFloat(binding.imageViewLogoText, "translationY", y)
        translationAnimation.duration = 1000

        val animationSet = AnimatorSet().apply {
            play(fadeInAnimation).before(translationAnimation)
        }

        binding.imageViewPortal.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext().applicationContext,
                R.anim.portal_anim
            )
        )

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                delay(2000)

                animationSet.start()

                val animationDrawable = binding.splashScreenRoot.background as AnimationDrawable
                animationDrawable.setEnterFadeDuration(30)
                animationDrawable.setExitFadeDuration(500)
                animationDrawable.start()
            }

            launch {
                delay(3750)

                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    replace(R.id.fragment_container_main, ItemsListFragment())
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}