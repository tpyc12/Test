package com.myhome.android.test.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myhome.android.test.R
import com.myhome.android.test.api.ApiService.Companion.SMALL_POSTER_SIZE
import com.myhome.android.test.databinding.MovieItemBinding
import com.myhome.android.test.pojo.MovieInfo
import com.squareup.picasso.Picasso

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var movieInfoList: List<MovieInfo> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onPosterClickListener: OnPosterClickListener? = null

    var onReachEndListener: OnReachEndListener? = null

    interface OnPosterClickListener {
        fun onPosterClick(position: Int)
    }

    interface OnReachEndListener {
        fun onReachEnd()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        if (position > movieInfoList.size - 4 && onReachEndListener != null) {
            onReachEndListener?.onReachEnd()
        }
        val movie = movieInfoList[position]
        Picasso.get().load(movie.getFullImageUrl(SMALL_POSTER_SIZE)).into(holder.ivSmallPoster)
        holder.itemView.setOnClickListener {
            onPosterClickListener?.onPosterClick(position)
        }
    }

    override fun getItemCount(): Int = movieInfoList.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ui: MovieItemBinding = MovieItemBinding.bind(itemView)

        val ivSmallPoster = ui.ivSmallPoster
    }

    fun addMovies(movies: ArrayList<MovieInfo>) {
        movies.addAll(movies)
        notifyDataSetChanged()
    }
}