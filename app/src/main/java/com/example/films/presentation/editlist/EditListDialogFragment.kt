package com.example.films.presentation.editlist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.films.R
import com.example.films.data.enums.ErrorReason
import com.example.films.data.enums.LoadState
import com.example.films.data.models.MovieList
import kotlinx.android.synthetic.main.dialog_edit_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditListDialogFragment : DialogFragment() {

    private val model: EditListViewModel by viewModel()

    companion object {
        private const val ARG_MOVIE_ID = "arg_movie_id"
        private const val ARG_LIST_ID = "arg_list_id"

        fun createListInstance(movieId: Int = 0): EditListDialogFragment {
            return newInstance(movieId = movieId)
        }

        fun editListInstance(listId: Long): EditListDialogFragment {
            return newInstance(listId = listId)
        }

        private fun newInstance(movieId: Int = 0, listId: Long = 0) : EditListDialogFragment {
            val bundle = Bundle()
            bundle.putInt(ARG_MOVIE_ID, movieId)
            bundle.putLong(ARG_LIST_ID, listId)
            return EditListDialogFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.submitListState.observe(this, Observer { handleCreateListState(it) })
        model.movieListState.observe(this, Observer { handleLoadListState(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_edit_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btnSubmit.isEnabled = s?.isNotBlank() ?: false
            }
        })
        btnCancel.setOnClickListener { dismiss() }
        btnSubmit.setOnClickListener {
            model.submit(inputTitle.text.toString())
        }
        val listId = arguments?.getLong(ARG_LIST_ID)!!
        model.initialize(arguments?.getInt(ARG_MOVIE_ID)!!, listId)
        textTitle.setText(if(listId > 0) R.string.edit_list_title else R.string.create_list_title)
        btnSubmit.setText(if(listId > 0) R.string.btn_save else R.string.btn_create)
    }

    override fun onStart() {
        super.onStart()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    }

    private fun handleCreateListState(state: LoadState<Unit>) {
        when (state) {
            is LoadState.Error -> handleError(state.reason())
            is LoadState.Data -> {
                Toast.makeText(context, getString(R.string.create_list_success), Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }

    private fun handleLoadListState(state: LoadState<MovieList>) {
        when (state) {
            is LoadState.Error -> handleError(state.reason())
            is LoadState.Data -> inputTitle.setText(state.data.title)
        }
    }

    private fun handleError(reason: ErrorReason) {
        when (reason) {
            ErrorReason.HTTP -> Toast.makeText(context, getString(R.string.error_server), Toast.LENGTH_SHORT).show()
            ErrorReason.NETWORK -> Toast.makeText(context, getString(R.string.error_network), Toast.LENGTH_SHORT).show()
            ErrorReason.UNKNOWN -> Toast.makeText(context, getString(R.string.error_generic), Toast.LENGTH_SHORT).show()
        }
    }
}