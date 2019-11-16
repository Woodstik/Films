package com.example.films.presentation.reminders

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import com.example.films.data.enums.ErrorReason
import com.example.films.data.enums.LoadState
import com.example.films.data.models.MovieReminder
import kotlinx.android.synthetic.main.activity_reminders.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

fun Context.remindersIntent() = Intent(this, RemindersActivity::class.java)

class RemindersActivity : AppCompatActivity() {

    private val model: RemindersViewModel by viewModel()
    private val adapter: ReminderAdapter by lazy { ReminderAdapter(reminderCallbacks)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)
        model.remindersState.observe(this, Observer { handleRemindersState(it) })
        setSupportActionBar(layoutToolbar.toolbar)
        supportActionBar!!.setTitle(R.string.title_reminders)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        listReminders.layoutManager = LinearLayoutManager(this)
        listReminders.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        listReminders.adapter = adapter
        swipeRefresh.setOnRefreshListener { model.loadReminders() }
        model.loadReminders()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleRemindersState(state: LoadState<List<MovieReminder>>) {
        swipeRefresh.isRefreshing = state is LoadState.Loading
        when (state) {
            is LoadState.Error -> handleError(state.reason())
            is LoadState.Data -> {
                textEmptyReminders.visibility = if(state.data.isEmpty()) View.VISIBLE else View.GONE
                adapter.setReminders(state.data)
            }
        }
    }

    private fun handleError(reason: ErrorReason) {
        when (reason) {
            ErrorReason.HTTP -> Toast.makeText(this, getString(R.string.error_server), Toast.LENGTH_SHORT).show()
            ErrorReason.NETWORK -> Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show()
            ErrorReason.UNKNOWN -> textEmptyReminders.visibility = View.VISIBLE
        }
    }

    private val reminderCallbacks = object : ReminderCallbacks{
        override fun onDelete(reminder: MovieReminder) {
            model.deleteReminder(reminder.id)
        }

        override fun onClickReminder(reminder: MovieReminder) {
            Toast.makeText(this@RemindersActivity, "Clicked reminder: ${reminder.id}", Toast.LENGTH_SHORT).show()
        }
    }
}