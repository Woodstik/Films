package com.example.films.presentation.createlist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.films.R
import kotlinx.android.synthetic.main.dialog_create_list.*

class CreateListDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(): CreateListDialogFragment {
            val bundle = Bundle()
            return CreateListDialogFragment().apply {
                arguments = bundle
            }
        }
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
            //TODO Need to create list with repo
            Toast.makeText(context, "Created List: ${inputTitle.text}", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    }
}