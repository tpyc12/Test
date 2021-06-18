package com.myhome.android.test.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.myhome.android.test.pojo.MovieInfo
import io.reactivex.Single

@Dao
interface MovieInfoDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<MovieInfo>>

    @Query("SELECT * FROM movies WHERE id =:movieId ")
    fun getMovieById(movieId: Int): LiveData<MovieInfo>

    @Query("DELETE FROM movies")
    fun deleteAllMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoviesList(moviesList: List<MovieInfo>)
}