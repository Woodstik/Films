package com.example.films.presentation.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import com.example.films.data.enums.ErrorReason
import com.example.films.data.enums.LoadState
import com.example.films.data.models.Movie
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

fun Context.searchIntent() = Intent(this, SearchActivity::class.java)

class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModel()
    private val searchAdapter = SearchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorWindowBackground)
        viewModel.movies.observe(this, Observer { handleSearchState(it) })
        btnBack.setOnClickListener { onBackPressed() }
        btnClear.setOnClickListener { editTextSearch.setText("") }
        listResults.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = searchAdapter
            addItemDecoration(DividerItemDecoration(this@SearchActivity, DividerItemDecoration.VERTICAL))
        }
        editTextSearch.requestFocus()
        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.queryChanged(s.toString())
                btnClear.visibility = if (s.toString().isEmpty()) View.GONE else View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun handleSearchState(state: LoadState<List<Movie>>) {
        swipeRefresh.isRefreshing = state is LoadState.Loading
        when (state) {
            is LoadState.Error -> handleError(state.reason())
            is LoadState.Data -> {
                textSearchStatus.visibility = if (state.data.isEmpty()) View.VISIBLE else View.GONE
                searchAdapter.setMovies(state.data)
            }
        }
    }

    private fun handleError(reason: ErrorReason) {
        when (reason) {
            ErrorReason.HTTP -> Timber.e("Http Error")
            ErrorReason.NETWORK -> Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show()
            ErrorReason.UNKNOWN -> {
                Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
