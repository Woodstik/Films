package com.example.films.presentation.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.films.R

class DiscoverFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    companion object {
        fun newInstance(): DiscoverFragment {
            val bundle = Bundle()
            return DiscoverFragment().apply {
                arguments = bundle
            }
        }
    }
}
