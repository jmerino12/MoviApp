package com.jmb.moviapp.framework.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.jmb.moviapp.databinding.FragmentMovieDetailBinding
import com.jmb.moviapp.loadUrl

class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    val args: MovieDetailFragmentArgs by navArgs()

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
        configPoster()
        configTitle()
    }

    private fun configPoster() {
        binding.imgCover.loadUrl("https://image.tmdb.org/t/p/w500/${args.movie.backdropPath}")
    }

    private fun configTitle() {
        binding.toolbarLayout.title = args.movie.title
        binding.overview.text = args.movie.overview

    }

}