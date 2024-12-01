package network

import com.squareup.moshi.Json
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "http://www.omdbapi.com/"

object RetrofitInstance {
    val api: OmdbApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OmdbApi::class.java)
    }
}

interface OmdbApi {
    @GET("/")
    suspend fun searchMovies(
        @Query("apikey") apiKey: String,
        @Query("s") searchQuery: String
    ): MovieSearchResponse

    @GET("/")
    suspend fun getMovieDetails(
        @Query("i") imdbID: String,
        @Query("apikey") apiKey: String
    ): MovieDetailsResponse
}

data class MovieSearchResponse(
    @Json(name = "Search") val search: List<Movie>?,
    @Json(name = "totalResults") val totalResults: String?,
    @Json(name = "Response") val response: String
)

data class Movie(
    @Json(name = "Title") val title: String,
    @Json(name = "Year") val year: String,
    @Json(name = "imdbID") val imdbID: String,
    @Json(name = "Type") val type: String,
    @Json(name = "Poster") val poster: String
)

data class MovieDetailsResponse(
    @Json(name = "Title") val title: String,
    @Json(name = "Year") val year: String,
    @Json(name = "Runtime") val runtime: String,
    @Json(name = "Genre") val genre: String,
    @Json(name = "Poster") val poster: String,
    @Json(name = "imdbRating") val imdbRating: String,
    @Json(name = "imdbID") val imdbID: String
)
