package com.example.films.presentation.movielist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import com.example.films.data.enums.ErrorReason
import com.example.films.data.enums.LoadState
import com.example.films.data.models.MovieList
import com.example.films.presentation.editlist.EditListDialogFragment
import com.example.films.utils.displayError
import com.example.films.utils.showDialogFragment
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.activity_movie_list.layoutToolbar
import kotlinx.android.synthetic.main.activity_movie_list.swipeRefresh
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val EXTRA_LIST_ID = "com.example.extras.extra_list_id"
private const val EXTRA_LIST_TITLE = "com.example.extras.extra_list_title"

fun Context.movieListIntent(listId: Long, title: String): Intent {
    return Intent(this, MovieListActivity::class.java)
        .putExtra(EXTRA_LIST_ID, listId)
        .putExtra(EXTRA_LIST_TITLE, title)
}

class MovieListActivity : AppCompatActivity(), SwipeToDeleteMovieCallback.SwipeListener {

    private val viewModel: MovieListViewModel by viewModel()

    private val adapter by lazy { MovieListAdapter(movieListCallbacks) }

    private val listId by lazy { intent.getLongExtra(EXTRA_LIST_ID, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        viewModel.movieListState.observe(this, Observer { handleLoadMovieListState(it) })
        viewModel.deleteMovieListState.observe(this, Observer { handleDeleteListState(it) })

        setSupportActionBar(layoutToolbar.toolbar)
        supportActionBar!!.title = intent.getStringExtra(EXTRA_LIST_TITLE)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        listMovieList.layoutManager = LinearLayoutManager(this)
        listMovieList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        listMovieList.adapter = adapter
        swipeRefresh.setOnRefreshListener { viewModel.loadMovieList(listId) }

        viewModel.loadMovieList(listId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movie_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.item_delete -> {
                viewModel.deleteList(listId)
                true
            }
            R.id.item_rename -> {
                val dialog = EditListDialogFragment.editListInstance(listId)
                dialog.listener = object : EditListDialogFragment.EditListListener {
                    override fun onSuccess() = viewModel.loadMovieList(listId)
                }
                showDialogFragment(dialog)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDelete(position: Int) {
        val movieId = adapter.getMovieId(position)
        adapter.remove(position)
        viewModel.deleteMovie(listId, movieId)
    }

    private fun handleLoadMovieListState(state: LoadState<MovieList>) {
        swipeRefresh.isRefreshing = state is LoadState.Loading
        when (state) {
            is LoadState.Error -> displayError(state.reason())
            is LoadState.Data -> displayMovieList(state.data)
        }
    }

    private fun handleDeleteListState(state: LoadState<Unit>) {
        swipeRefresh.isRefreshing = state is LoadState.Loading
        when (state) {
            is LoadState.Error -> displayError(state.reason())
            is LoadState.Data -> {
                Toast.makeText(this, getString(R.string.movie_list_deleted), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun displayMovieList(moveList: MovieList) {
        supportActionBar!!.title = moveList.title
        textListStatus.visibility = if(moveList.movies.isEmpty()) View.VISIBLE else View.GONE
        adapter.setMovieList(moveList)
    }

    private val movieListCallbacks = object : MovieListCallbacks{
        override fun deleteMovie(movieId: Int) {
            viewModel.deleteMovie(listId, movieId)
        }
    }
}