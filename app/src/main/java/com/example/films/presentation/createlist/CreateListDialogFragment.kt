package com.example.films.presentation.createlist

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
import kotlinx.android.synthetic.main.dialog_create_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class CreateListDialogFragment : DialogFragment() {

    private val model: CreateListViewModel by viewModel()

    companion object {
        private const val ARG_MOVIE_ID = "arg_movie_id"

        fun newInstance(movieId: Int = 0): CreateListDialogFragment {
            val bundle = Bundle()
            bundle.putInt(ARG_MOVIE_ID, movieId)
            return CreateListDialogFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.createListState.observe(this, Observer { handleCreateListState(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_create_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btnCreate.isEnabled = s?.isNotBlank() ?: false
            }
        })
        btnCancel.setOnClickListener { dismiss() }
        btnCreate.setOnClickListener {
            model.createMovieList(inputTitle.text.toString(), arguments?.getInt(ARG_MOVIE_ID)!!)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    }

    private fun handleCreateListState(state: LoadState<Boolean>) {
        when (state) {
            is LoadState.Error -> handleError(state.reason())
            is LoadState.Data -> {
                Toast.makeText(context, "List Created", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }

    private fun handleError(reason: ErrorReason) {
        when (reason) {
            ErrorReason.HTTP -> Timber.e("Http Error")
            ErrorReason.NETWORK -> Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show()
            ErrorReason.UNKNOWN -> { Timber.e("Unknown Error") }
        }
    }
}