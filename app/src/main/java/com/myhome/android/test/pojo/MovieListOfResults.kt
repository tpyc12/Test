package com.myhome.android.test.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieListOfResults (
    @SerializedName("results")
    @Expose
    val results: List<MovieInfo>
)