package com.manish.moviesearch.data.repo

import com.manish.moviesearch.core.utils.Constants
import com.manish.moviesearch.data.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET(Constants.URL)
    suspend fun getMovie(@Query("apikey") apiKey:String, @Query("t") t: String) : Response<Movie>
}