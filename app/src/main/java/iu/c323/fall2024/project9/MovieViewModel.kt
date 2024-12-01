package iu.c323.fall2024.project9

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import network.Movie
import network.MovieDetailsResponse

private const val TAG ="MovieViewModel"
class MovieViewModel : ViewModel() {
    private val movieRepository = MovieRepository()

    private val _movies: MutableStateFlow<List<Movie>> = MutableStateFlow(emptyList())
    val movies: StateFlow<List<Movie>> get() = _movies.asStateFlow()

    var numberOfMovies: Int = 0

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                val movieList = movieRepository.searchMovies(query)
                numberOfMovies = movieList.size
                _movies.value = movieList
                Log.d(TAG, "Number of Movies: $numberOfMovies")
            } catch (ex: Exception) {
                _movies.value = emptyList()
                Log.e(TAG, "Failed to search movies", ex)
            }
        }
    }
}



