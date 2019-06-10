package com.example.films.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.films.R
import com.example.films.data.enums.LoadState
import com.example.films.data.models.HomeMovies
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val model: HomeViewModel by viewModel()

    private val homeAdapter by lazy {
        HomeAdapter()
    }

    companion object {
        fun newInstance(): HomeFragment {
            val bundle = Bundle()
            return HomeFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.movies.observe(this, Observer { handleMovieState(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gridLayoutManager = GridLayoutManager(context, resources.getInteger(R.integer.home_max_span))
        listHome.layoutManager = gridLayoutManager
        listHome.adapter = homeAdapter
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = resources.getInteger(homeAdapter.getItemSpan(position))
        }
        model.loadMovies()
    }

    private fun handleMovieState(state: LoadState<HomeMovies>) {
        when (state) {
            is LoadState.Error -> {
                TODO("Show error")
            }
            is LoadState.Loading -> {
                TODO("Show loading")
            }
            is LoadState.Data -> homeAdapter.setMovies(state.data)
        }
    }
}
