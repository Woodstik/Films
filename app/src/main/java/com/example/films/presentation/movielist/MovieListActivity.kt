package com.example.films.presentation.movielist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.films.R
import com.example.films.data.enums.ErrorReason
import com.example.films.data.enums.LoadState
import com.example.films.data.models.MovieList
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val EXTRA_LIST_ID = "com.example.extras.extra_list_id"

fun Context.movieListIntent(listId: Long): Intent {
    return Intent(this, MovieListActivity::class.java)
        .putExtra(EXTRA_LIST_ID, listId)
}

class MovieListActivity : AppCompatActivity() {

    private val viewModel: MovieListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        viewModel.movieListState.observe(this, Observer { handleLoadMovieState(it) })

        viewModel.loadMovieList(intent.getLongExtra(EXTRA_LIST_ID, 0))
    }

    private fun handleLoadMovieState(state: LoadState<MovieList>) {
//        swipeRefresh.isRefreshing = state is LoadState.Loading
        when (state) {
            is LoadState.Error -> handleError(state.reason())
            is LoadState.Data -> Toast.makeText(this, getString(R.string.create_reminder_success), Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleError(reason: ErrorReason) {
        when (reason) {
            ErrorReason.HTTP -> Toast.makeText(this, getString(R.string.error_server), Toast.LENGTH_SHORT).show()
            ErrorReason.NETWORK -> Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show()
            ErrorReason.UNKNOWN -> Toast.makeText(this, getString(R.string.error_generic), Toast.LENGTH_SHORT).show()
        }
    }
}