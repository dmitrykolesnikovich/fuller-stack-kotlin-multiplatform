package com.akjaw.fullerstack.screens.noteslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import base.usecase.Either
import base.usecase.UseCaseAsync
import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import com.akjaw.fullerstack.screens.common.base.BaseFragment
import data.Note
import feature.noteslist.FetchNotesListUseCaseAsync
import kotlinx.coroutines.launch
import org.kodein.di.instance

class NotesListFragment : BaseFragment(), NotesListViewMvc.Listener {

    private val viewMvcFactory: ViewMvcFactory by instance<ViewMvcFactory>()
    private val fetchNotesListUseCaseAsync: FetchNotesListUseCaseAsync by instance<FetchNotesListUseCaseAsync>()
    private lateinit var viewMvc: NotesListViewMvc

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewMvc = viewMvcFactory.getNotesListViewMvc(container)

        return viewMvc.rootView
    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)

        fetchNotesList()
    }

    override fun onStop() {
        super.onStop()
        viewMvc.unregisterListener(this)
    }

    override fun onNoteClicked(title: String) {
        Toast.makeText(context, title, Toast.LENGTH_SHORT).show()
    }

    private fun fetchNotesList() {
        lifecycleScope.launch {
            fetchNotesListUseCaseAsync.executeAsync(
                UseCaseAsync.None()
            ) { result ->
                when (result) {
                    is Either.Left -> onNoteListFetchFail()
                    is Either.Right -> onNoteListFetchSuccess(result.r)
                }
            }
        }
    }

    private fun onNoteListFetchSuccess(notes: List<Note>) {
        viewMvc.setNotes(notes)
    }

    private fun onNoteListFetchFail() {
        TODO("Not yet implemented")
    }
}
