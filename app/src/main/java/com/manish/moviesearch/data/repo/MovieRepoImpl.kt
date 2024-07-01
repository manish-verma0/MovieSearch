package com.manish.moviesearch.data.repo

import com.manish.moviesearch.core.utils.Constants
import com.manish.moviesearch.data.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepoImpl @Inject constructor(private val service: MovieService) {

    suspend fun getMovieByTitle(title:String): Flow<Movie> {
        return flow{
            service.getMovie(Constants.API_KEY, title).body()?.let { emit(it) }
        }
    }
}