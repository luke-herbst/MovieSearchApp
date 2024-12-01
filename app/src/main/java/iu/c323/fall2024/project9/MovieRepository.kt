package iu.c323.fall2024.project9

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import network.OmdbApi
import network.Movie
import network.MovieDetailsResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieRepository {

    private val omdbApi: OmdbApi

    init {
        val okHttpClient = OkHttpClient.Builder().build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

        omdbApi = retrofit.create(OmdbApi::class.java)
    }

    suspend fun searchMovies(query: String): List<Movie> {
        return omdbApi.searchMovies("f4abb924", query).search ?: emptyList()
    }

    suspend fun getMovieDetails(imdbID: String): MovieDetailsResponse{
        return omdbApi.getMovieDetails(imdbID, "f4abb924")
    }

}
