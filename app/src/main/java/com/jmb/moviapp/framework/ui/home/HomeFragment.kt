package com.jmb.moviapp.framework.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jmb.moviapp.R
import com.jmb.moviapp.databinding.ContentMainBinding
import com.jmb.moviapp.databinding.FragmentHomeBinding
import com.jmb.moviapp.domain.Movie
import com.jmb.moviapp.framework.ui.common.Resource
import com.jmb.moviapp.framework.ui.common.toast

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var content: ContentMainBinding


    private lateinit var adapter: MoviesAdapter

    private val viewModel: HomeViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(
            this,
            HomeViewModel.Factory(activity.application)
        ).get(HomeViewModel::class.java)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MoviesAdapter(::onMovieClicked)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        content = binding.content
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMovies(getString(R.string.apiKey))
    }

    private fun setupRecyclerView() {
        content.rvMovies.adapter = adapter
        viewModel.movies.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    searching(true)
                    retry(false)

                }
                is Resource.Success -> {
                    searching(false)
                    retry(false)

                    adapter.setList(it.data)
                    adapter.submitList(it.data)
                }
                is Resource.Failure -> {
                    retry(true)
                    Log.e(tag, it.toString())
                    requireContext().toast(it.toString())
                    searching(false)

                }
            }

        }

    }

    private fun searching(show: Boolean) = if (show) {
        binding.fragmentProgressBar.root.visibility = View.VISIBLE
    } else {
        binding.fragmentProgressBar.root.visibility = View.GONE
    }

    private fun retry(show: Boolean) = if (show) {
        binding.retry.visibility = View.VISIBLE
        binding.retry.setOnClickListener { viewModel.getMovies(getString(R.string.apiKey)) }
    } else {
        binding.retry.visibility = View.GONE
    }

    fun onMovieClicked(movie: Movie) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(
                movie
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}