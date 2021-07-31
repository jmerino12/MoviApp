package com.jmb.moviapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.jmb.moviapp.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        val navController = findNavController()
        val appbarconfi = AppBarConfiguration(navGraph = navController.graph)
        binding.toolbar.setupWithNavController(navController, appbarconfi)
        configActionBar()
        return binding.root
    }


    private fun configActionBar() {
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout: AppBarLayout, verticalOffset: Int ->
            val percentage =
                Math.abs(Math.abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange - 1)
            val colorValue = (percentage * 255).toInt()
            Log.i(tag, colorValue.toString())
            binding.toolbarLayout.setExpandedTitleColor(
                Color.rgb(
                    colorValue,
                    colorValue,
                    colorValue
                )
            )
            if (colorValue < 110) {
                binding.toolbarLayout.setCollapsedTitleTextColor(
                    Color.rgb(
                        156,
                        colorValue,
                        colorValue
                    )
                )
            }
        })
        configTitle()
    }

    private fun configTitle() {
        binding.toolbarLayout.title = "Mortal Kombat"

    }

}