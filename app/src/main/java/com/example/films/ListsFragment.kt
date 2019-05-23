package com.example.films

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ListsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lists, container, false)
    }

    companion object {
        fun newInstance(): ListsFragment {
            val bundle = Bundle()
            return ListsFragment().apply {
                arguments = bundle
            }
        }
    }
}
