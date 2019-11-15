package com.example.films.presentation.selectlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import com.example.films.data.enums.ErrorReason
import com.example.films.data.enums.LoadState
import com.example.films.data.models.MovieList
import com.example.films.presentation.createlist.CreateListDialogFragment
import com.example.films.utils.showDialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_select_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SelectListDialogFragment : BottomSheetDialogFragment() {

    private val model: SelectListViewModel by viewModel()

    private val adapter: SelectListAdapter by lazy { SelectListAdapter(selectListCallbacks) }

    companion object {
        private const val ARG_MOVIE_ID = "arg_movie_id"

        fun newInstance(id: Int): SelectListDialogFragment {
            val bundle = Bundle()
            bundle.putInt(ARG_MOVIE_ID, id)
            return SelectListDialogFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.movieListsState.observe(this, Observer { handleMovieListsState(it) })
        model.addMovieToListState.observe(this, Observer { handleAddMovieToListState(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_select_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnCreateList.setOnClickListener {
            activity?.showDialogFragment(CreateListDialogFragment.newInstance(arguments?.getInt(ARG_MOVIE_ID)!!))
            dismiss()
        }
        listUserLists.layoutManager = LinearLayoutManager(context)
        listUserLists.adapter = adapter
        model.loadLists()
    }

    private fun handleMovieListsState(state: LoadState<List<MovieList>>) {
        when (state) {
            is LoadState.Error -> handleError(state.reason())
            is LoadState.Data -> {
                adapter.setMovieLists(state.data)
            }
        }
    }

    private fun handleAddMovieToListState(state: LoadState<Boolean>) {
        when (state) {
            is LoadState.Error -> handleError(state.reason())
            is LoadState.Data -> {
                Toast.makeText(context, getString(R.string.add_movie_to_list_success), Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }

    private fun handleError(reason: ErrorReason) {
        when (reason) {
            ErrorReason.HTTP -> Toast.makeText(context, getString(R.string.error_server), Toast.LENGTH_SHORT).show()
            ErrorReason.NETWORK -> Toast.makeText(context, getString(R.string.error_network), Toast.LENGTH_SHORT).show()
            ErrorReason.UNKNOWN -> Toast.makeText(context, getString(R.string.error_generic), Toast.LENGTH_SHORT).show()
        }
    }

    private val selectListCallbacks = object : SelectListCallbacks {
        override fun onSelectList(movieList: MovieList) {
            model.addMovieToList( arguments?.getInt(ARG_MOVIE_ID)!!, movieList.id)
        }
    }
}