package com.jmb.moviapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jmb.moviapp.databinding.ItemMovieBinding

class MoviesAdapter(private val listener: (Movie) -> Unit) :
    ListAdapter<Movie, BaseViewHolder<*>>(MoviesDiffCallback()) {

    private lateinit var items: List<Movie>

    fun setList(list: List<Movie>) {
        items = list
    }

    class MoviesDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.name == newItem.name
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
                .loadUrl("https://media.gettyimages.com/photos/captain-america-the-first-avenger-movie-poster-picture-id458467163?s=612x612")
            binding.root.setOnClickListener { listener(item) }
        }
    }

}