package com.myhome.android.test.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.myhome.android.test.api.ApiFactory
import com.myhome.android.test.database.AppDatabase
import com.myhome.android.test.pojo.MovieInfo
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    val page: Int = 1;

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val movies = db.movieInfoDao().getAllMovies()

    fun getMovieById(id: Int): LiveData<MovieInfo> {
        return db.movieInfoDao().getMovieById(id)
    }

    @JvmName("getMovies1")
    fun getMovies(): LiveData<List<MovieInfo>> {
        return movies
    }

    fun deleteAllMovies(){
        db.movieInfoDao().deleteAllMovies()
    }

    init {
        loadData(page)
    }

    fun loadData(page: Int) {
        val disposable =
            ApiFactory.apiService.getMovieList(page = page)
                .map { it.results }
                .subscribeOn(Schedulers.io())
                .subscribe({
                    db.movieInfoDao().insertMoviesList(it)
                    Log.d("TAG", it.toString())
                }, {
                    Log.d("TAG", it.message.toString())
                })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}