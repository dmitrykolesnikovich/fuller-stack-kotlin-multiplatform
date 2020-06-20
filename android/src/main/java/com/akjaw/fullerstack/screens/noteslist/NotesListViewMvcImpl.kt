package com.akjaw.fullerstack.screens.noteslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import com.akjaw.fullerstack.screens.noteslist.recyclerview.NotesListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import data.Note
import helpers.date.PatternProvider

class NotesListViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?,
    viewMvcFactory: ViewMvcFactory,
    patternProvider: PatternProvider
) : NotesListViewMvc() {

    override val rootView: View = inflater.inflate(R.layout.layout_notes_list, parent, false)
    private val notesListAdapter: NotesListAdapter = NotesListAdapter(
        ::onNoteClicked,
        viewMvcFactory,
        patternProvider.getNotesListItemPattern()
    )
    private val notesRecyclerView: RecyclerView = rootView.findViewById(R.id.notes_list)
    private val loadingIndicator: ProgressBar = rootView.findViewById(R.id.loading_indicator)
    private val fab: FloatingActionButton = rootView.findViewById(R.id.floating_action_button)

    init {
        notesRecyclerView.adapter = notesListAdapter
        notesRecyclerView.setHasFixedSize(true)
        notesRecyclerView.layoutManager = LinearLayoutManager(context)

        fab.setOnClickListener {
            listeners.forEach { it.onAddNoteClicked() }
        }
    }

    private fun onNoteClicked(title: String) {
        listeners.forEach { listeners ->
            listeners.onNoteClicked(title)
        }
    }

    override fun setNotes(notes: List<Note>) {
        notesListAdapter.setNotes(notes)
    }

    override fun showLoading() {
        loadingIndicator.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loadingIndicator.visibility = View.GONE
    }

}