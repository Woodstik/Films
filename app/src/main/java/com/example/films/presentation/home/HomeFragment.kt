package com.example.films.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import com.example.films.data.enums.ErrorReason
import com.example.films.data.enums.LoadState
import com.example.films.data.models.HomeMovies
import com.example.films.data.models.Movie
import com.example.films.presentation.selectlist.SelectListDialogFragment
import com.example.films.utils.openUrl
import com.example.films.utils.showDialogFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()

    private val homeAdapter by lazy { HomeAdapter(newReleaseCallbacks, upcomingCallbacks) }

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
        viewModel.moviesState.observe(this, Observer { handleMovieState(it) })
        viewModel.createReminderState.observe(this, Observer { handleCreateReminderState(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listHome.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = homeAdapter
        }
        viewModel.loadMovies()
        swipeRefresh.setOnRefreshListener { viewModel.loadMovies() }
        btnRetry.setOnClickListener { viewModel.loadMovies() }
    }

    private fun handleMovieState(state: LoadState<HomeMovies>) {
        swipeRefresh.isRefreshing = state is LoadState.Loading
        when (state) {
            is LoadState.Error -> handleError(state.reason())
            is LoadState.Data -> {
                groupLoadStatus.visibility = View.GONE
                homeAdapter.setMovies(state.data)
            }
        }
    }

    private fun handleCreateReminderState(state: LoadState<Unit>) {
        swipeRefresh.isRefreshing = state is LoadState.Loading
        when (state) {
            is LoadState.Error -> handleError(state.reason())
            is LoadState.Data -> Toast.makeText(context, getString(R.string.create_reminder_success), Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleError(reason: ErrorReason) {
        when (reason) {
            ErrorReason.HTTP -> Toast.makeText(context, getString(R.string.error_server), Toast.LENGTH_SHORT).show()
            ErrorReason.NETWORK -> Toast.makeText(context, getString(R.string.error_network), Toast.LENGTH_SHORT).show()
            ErrorReason.UNKNOWN -> {
                groupLoadStatus.visibility = View.VISIBLE
            }
        }
    }

    private val newReleaseCallbacks = object : NewReleaseCallbacks {
        override fun onAddToList(movie: Movie) {
            activity?.showDialogFragment(SelectListDialogFragment.newInstance(movie.id), "bottomSheet")
        }

        override fun onTrailer(url: String) {
            context?.openUrl(url)
        }

        override fun onShare(movie: Movie) {
            Toast.makeText(context, "Share: ${movie.title}", Toast.LENGTH_SHORT).show()
        }

        override fun onClick(movie: Movie) {
            Toast.makeText(context, "Click: ${movie.title}", Toast.LENGTH_SHORT).show()
        }
    }

    private val upcomingCallbacks = object : UpcomingCallbacks {
        override fun onRemind(movie: Movie) {
            viewModel.createReminder(movie.id)
        }

        override fun onClick(movie: Movie) {
            Toast.makeText(context, "Click: ${movie.title}", Toast.LENGTH_SHORT).show()
        }
    }
}
