package iu.c323.fall2024.project9

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import iu.c323.fall2024.project9.databinding.FragmentSearchBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "MovieSearchFragment"

class SearchFragment: Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = checkNotNull(_binding) {
        "Cannot access binding because it is null. Is the view visible?"
    }

    private val movieViewModel: MovieViewModel by viewModels()

    private lateinit var firestoreDb: FirebaseFirestore

    private var currentQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.movies.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.fragment_search)
        val searchItem: MenuItem = binding.toolbar.menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as? SearchView

        firestoreDb = FirebaseFirestore.getInstance()

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "QueryTextSubmit: $query")
                movieViewModel.searchMovies(query ?: "")
                currentQuery = query.toString()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "QueryTextChanged: $newText")
                return false
            }
        })
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieViewModel.movies.collect { movies ->
                    Log.d(TAG, "Movies received: $movies")
                    binding.movies.adapter = MovieAdapter(movies)

                    saveQuery(currentQuery)
                }
            }
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_item_sign_out -> {
                    val auth = FirebaseAuth.getInstance()
                    auth.signOut()
                    Toast.makeText(requireContext(), "Signed out", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.search_to_login)
                    true
                }
                R.id.menu_item_feedback -> {
                    sendFeedback()
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun sendFeedback() {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("lsherbst@iu.edu"))
            putExtra(Intent.EXTRA_SUBJECT, "Feedback")
        }
        startActivity(emailIntent)
    }

    private fun saveQuery(query: String) {
        Log.d(TAG, "Saving query: $query with resultNumber: ${movieViewModel.numberOfMovies}")
        val auth = FirebaseAuth.getInstance()

        val userQuery = hashMapOf(
            "query" to query,
            "timestamp" to System.currentTimeMillis(),
            "result_number" to movieViewModel.numberOfMovies,
            "username" to auth.currentUser?.email.toString()
        )

        firestoreDb.collection("queries")
            .add(userQuery)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Query added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding query", e)
            }
    }
}
