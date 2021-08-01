package com.jmb.moviapp.framework.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.jmb.moviapp.R
import com.jmb.moviapp.databinding.ContentMainBinding
import com.jmb.moviapp.databinding.FragmentHomeBinding
import com.jmb.moviapp.domain.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var content: ContentMainBinding


    private lateinit var adapter: MoviesAdapter

    private var movieJob: Job? = null

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
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.topAppBar)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        content.rvMovies.adapter = adapter
        viewModel.getMoviePaging().observe(viewLifecycleOwner) { result ->
            viewModel.firstFecth.observe(viewLifecycleOwner, {
                if (!it) {
                    GlobalScope.launch(Dispatchers.IO) {
                        adapter.submitData(result)
                    }
                }
            })

        }
        adapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.fragmentProgressBar.root.isVisible =
                loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.fragmentProgressBar.root.isVisible =
                loadState.source.refresh is LoadState.Loading
        }
    }


    fun onMovieClicked(movie: Movie) {
        viewModel.isNavigate()
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(
                movie
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
        val search: MenuItem = menu.findItem(R.id.m_search)
        val searchView = search.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.isNavigate()
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSearchMovieFragment(
                        query.toString()
                    )
                )
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}