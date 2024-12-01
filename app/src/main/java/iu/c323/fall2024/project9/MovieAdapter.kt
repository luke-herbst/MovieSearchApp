package iu.c323.fall2024.project9

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import iu.c323.fall2024.project9.databinding.ItemMovieBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import network.Movie

private const val TAG = "MovieAdapter"

class MovieAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        private val movieRepository = MovieRepository()
        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            binding.movieYear.text = movie.year
            Glide.with(binding.moviePoster.context)
                .load(movie.poster)
                .into(binding.moviePoster)

            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val details = movieRepository.getMovieDetails(movie.imdbID)
                    binding.movieGenre.text = details.genre
                    binding.movieRating.text = details.imdbRating
                    binding.movieRuntime.text = details.runtime

                    binding.btnImdbLink.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/${movie.imdbID}"))
                        binding.root.context.startActivity(intent)
                    }
                    binding.btnShare.setOnClickListener {
                        val shareIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Check out this movie: ${movie.title}\nhttps://www.imdb.com/title/${movie.imdbID}")
                            type = "text/plain"
                        }
                        binding.root.context.startActivity(Intent.createChooser(shareIntent, "Share via"))
                    }
                } catch (ex: Exception) {
                    Log.e(TAG, "Failed to retrieve movie details", ex)
                }
            }
        }
    }
}
