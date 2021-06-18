package com.myhome.android.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.myhome.android.test.api.ApiService.Companion.BIG_POSTER_SIZE
import com.myhome.android.test.databinding.ActivityDetailBinding
import com.myhome.android.test.viewModel.MovieViewModel
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieViewModel

    private lateinit var ui: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        ui = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(ui.root)
        if (!intent.hasExtra(EXTRA_ID)) {
            finish()
            return
        }
        val id = intent.getIntExtra(EXTRA_ID, -1)
        Log.d("TAG", id.toString())
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        id.let {
            viewModel.getMovieById(it).observe(this,{ movie ->
                ui.tvTitle.text = movie.title.toString()
                ui.tvOriginalTitle.text = movie.originalTitle.toString()
                ui.tvRating.text = movie.voteAverage.toString()
                ui.tvOverview.text = movie.overview.toString()
                ui.tvReleaseDate.text = movie.releaseDate.toString()
                Picasso.get().load(movie.getFullImageUrl(BIG_POSTER_SIZE)).into(ui.ivBigPoster)
            })
        }
    }

    companion object {
        const val EXTRA_ID = "id"

        fun newIntent(context: Context, id: Int?): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_ID, id)
            return intent
        }
    }
}