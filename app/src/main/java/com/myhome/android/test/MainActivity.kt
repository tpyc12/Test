package com.myhome.android.test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.myhome.android.test.adapters.MovieAdapter
import com.myhome.android.test.databinding.ActivityMainBinding
import com.myhome.android.test.viewModel.MovieViewModel

class MainActivity : AppCompatActivity() {

    private var downloadPage: Int = 1

    private lateinit var viewModel: MovieViewModel

    private lateinit var ui: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)

        val adapter = MovieAdapter()

        ui.rvMoviesList.layoutManager = GridLayoutManager(this, 2)
        ui.rvMoviesList.adapter = adapter

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        viewModel.movies.observe(this, {
            adapter.movieInfoList = it
        })

        adapter.onPosterClickListener = object : MovieAdapter.OnPosterClickListener{
            override fun onPosterClick(position: Int) {
                val movie = adapter.movieInfoList[position]
                Log.d("TAG", movie.id.toString())
                val intent = DetailActivity.newIntent(
                    this@MainActivity,
                    movie.id
                )
                startActivity(intent)
            }
        }

        adapter.onReachEndListener = object : MovieAdapter.OnReachEndListener {
            override fun onReachEnd() {
                downloadPage++
                viewModel.loadData(downloadPage)
            }
        }
    }
}