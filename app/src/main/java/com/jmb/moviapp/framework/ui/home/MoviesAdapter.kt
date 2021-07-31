package com.jmb.moviapp.framework.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.jmb.moviapp.databinding.ItemMovieBinding
import com.jmb.moviapp.domain.Movie
import com.jmb.moviapp.framework.ui.common.BaseViewHolder
import com.jmb.moviapp.framework.ui.common.loadUrl

class MoviesAdapter(private val listener: (Movie) -> Unit) :
    PagingDataAdapter<Movie, BaseViewHolder<*>>(
        MoviesDiffCallback
    ) {

    companion object {
        private val MoviesDiffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val viewBinding =
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MovieHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MovieHolder -> {
                getItem(position)?.let { holder.bind(it, position) }
            }
            else -> {
                throw IllegalStateException("ViewType no declarado ")
            }
        }
    }


    inner class MovieHolder(
        private val binding: ItemMovieBinding
    ) :
        BaseViewHolder<Movie>(binding.root) {
        override fun bind(item: Movie, position: Int) {
            binding.posterMovie
                .loadUrl("https://image.tmdb.org/t/p/w500/${item.posterPath}")
            binding.root.setOnClickListener { listener(item) }
        }
    }

}