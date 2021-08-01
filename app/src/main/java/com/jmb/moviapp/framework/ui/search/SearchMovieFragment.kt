package com.jmb.moviapp.framework.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.jmb.moviapp.databinding.ContentMainBinding
import com.jmb.moviapp.databinding.FragmentSearchMovieBinding
import com.jmb.moviapp.domain.Movie
import com.jmb.moviapp.framework.ui.home.MoviesAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SearchMovieFragment : Fragment() {

    val args: SearchMovieFragmentArgs by navArgs()
    private var searchJob: Job? = null


    private var _binding: FragmentSearchMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var content: ContentMainBinding
    private lateinit var adapter: MoviesAdapter
    private val viewModel: SearchMovieViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(
            this,
            SearchMovieViewModel.Factory()
        ).get(SearchMovieViewModel::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MoviesAdapter(::onMovieClicked)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        content = binding.content
        val navController = findNavController()
        val appbarconfi = AppBarConfiguration(navGraph = navController.graph)
        binding.topAppBar.setupWithNavController(navController, appbarconfi)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topAppBar.title = "Buscando: ${args.nameMovie}"
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        content.rvMovies.adapter = adapter
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchRepo(args.nameMovie).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    fun onMovieClicked(movie: Movie) {
        findNavController().navigate(
            SearchMovieFragmentDirections.actionSearchMovieFragmentToMovieDetailFragment(
                movie
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}