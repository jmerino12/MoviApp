package com.jmb.moviapp.framework.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jmb.moviapp.databinding.ItemMovieBinding
import com.jmb.moviapp.domain.Movie
import com.jmb.moviapp.framework.ui.common.BaseViewHolder
import com.jmb.moviapp.loadUrl

class MoviesAdapter(private val listener: (Movie) -> Unit) :
    ListAdapter<Movie, BaseViewHolder<*>>(MoviesDiffCallback()) {

    private lateinit var items: List<Movie>

    fun setList(list: List<Movie>) {
        items = list
    }

    class MoviesDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
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
                holder.bind(items[position], position)
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