package com.jmb.moviapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jmb.moviapp.databinding.ContentMainBinding
import com.jmb.moviapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var content: ContentMainBinding


    private lateinit var adapter: MoviesAdapter

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

    private fun setupRecyclerView() {
        val moviesList = arrayListOf<Movie>(
            Movie("Motarl Kombat"),
            Movie("Avenger End Game"),
            Movie("Avenger End Game"),
            Movie("Avenger End Game"),
            Movie("Avenger End Game"),
            Movie("Avenger End Game"),
            Movie("Avenger End Game"),
            Movie("Avenger End Game")
        )
        content.rvMovies.adapter = adapter
        adapter.setList(moviesList)
        adapter.submitList(moviesList)
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