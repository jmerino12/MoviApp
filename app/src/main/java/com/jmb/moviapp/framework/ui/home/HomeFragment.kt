package com.jmb.moviapp.framework.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jmb.moviapp.R
import com.jmb.moviapp.databinding.ContentMainBinding
import com.jmb.moviapp.databinding.FragmentHomeBinding
import com.jmb.moviapp.domain.Movie

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var content: ContentMainBinding


    private lateinit var adapter: MoviesAdapter

    private val viewModel by viewModels<HomeViewModel>()

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
            adapter.setList(it)
            adapter.submitList(it)
        }

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