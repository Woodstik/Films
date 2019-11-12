package com.example.films.presentation.selectlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import com.example.films.data.enums.ErrorReason
import com.example.films.data.enums.LoadState
import com.example.films.data.models.MovieList
import com.example.films.presentation.createlist.CreateListDialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_select_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SelectListDialogFragment : BottomSheetDialogFragment() {

    private val model: SelectListViewModel by viewModel()

    private val adapter: SelectListAdapter by lazy { SelectListAdapter() }

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
        model.movieLists.observe(this, Observer { handleMovieListsState(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_select_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnCreateList.setOnClickListener {
            createList()
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

    private fun handleError(reason: ErrorReason) {
        when (reason) {
            ErrorReason.HTTP -> Timber.e("Http Error")
            ErrorReason.NETWORK -> Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show()
            ErrorReason.UNKNOWN -> { }
        }
    }

    private fun createList() {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        val prev = activity?.supportFragmentManager?.findFragmentByTag("dialog")
        if(prev != null){
            transaction?.remove(prev)
        }
        transaction?.addToBackStack(null)
        val dialog = CreateListDialogFragment.newInstance()
        dialog.show(activity?.supportFragmentManager, "dialog")
    }
}