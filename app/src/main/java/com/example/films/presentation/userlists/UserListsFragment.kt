package com.example.films.presentation.userlists

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
import com.example.films.data.models.UsersMovieLists
import com.example.films.presentation.createlist.CreateListDialogFragment
import com.example.films.presentation.selectlist.SelectListDialogFragment
import com.example.films.utils.openUrl
import kotlinx.android.synthetic.main.fragment_lists.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class UserListsFragment : Fragment() {

    private val model: UserListsViewModel by viewModel()
    private val userListsAdapter by lazy { UserListsAdapter(remindersCallbacks) }

    companion object {
        fun newInstance(): UserListsFragment {
            val bundle = Bundle()
            return UserListsFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.usersMovieLists.observe(this, Observer { handleUserListsState(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnCreateList.setOnClickListener { createList() }
        listUserLists.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = userListsAdapter
        }
        model.loadLists()
        swipeRefresh.setOnRefreshListener { model.loadLists() }
        btnRetry.setOnClickListener { model.loadLists() }
    }

    private fun handleUserListsState(state: LoadState<UsersMovieLists>) {
        swipeRefresh.isRefreshing = state is LoadState.Loading
        when (state) {
            is LoadState.Error -> handleError(state.reason())
            is LoadState.Data -> {
                groupLoadStatus.visibility = View.GONE
                userListsAdapter.setUserLists(state.data)
            }
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

    private val remindersCallbacks = object : RemindersCallbacks {
        override fun onClickTrailer(url: String) {
            context?.openUrl(url)
        }
    }
}
