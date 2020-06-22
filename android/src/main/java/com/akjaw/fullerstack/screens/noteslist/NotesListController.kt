package com.akjaw.fullerstack.screens.noteslist

import android.os.Parcelable
import base.usecase.Either
import base.usecase.UseCaseAsync
import com.akjaw.fullerstack.model.ParcelableNote
import com.akjaw.fullerstack.screens.common.ScreenNavigator
import data.Mapper
import data.Note
import feature.noteslist.FetchNotes
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotesListController(
    private val screenNavigator: ScreenNavigator,
    private val fetchNotes: FetchNotes,
    private val parcelableNoteMapper: Mapper<ParcelableNote, Note>
) : NotesListViewMvc.Listener {

    internal sealed class NotesListState: Parcelable {
        @Parcelize
        object Uninitialized: NotesListState()

        @Parcelize
        class ShowingList(val notes: List<ParcelableNote>): NotesListState()
    }

    private lateinit var viewMvc: NotesListViewMvc
    private lateinit var scope: CoroutineScope
    internal var currentState: NotesListState = NotesListState.Uninitialized
        private set

    fun bindView(
        viewMvc: NotesListViewMvc,
        scope: CoroutineScope
    ) {
        this.viewMvc = viewMvc
        this.scope = scope
    }

    fun onStart() {
        viewMvc.registerListener(this)
        val currentState = this.currentState
        when (currentState) {
            is NotesListState.Uninitialized -> {
                initializeNotes()
            }
            is NotesListState.ShowingList -> {
                val notes = currentState.notes.map { parcelableNoteMapper.mapFrom(it) }
                viewMvc.setNotes(notes)
            }
        }
    }

    private fun initializeNotes() {
        viewMvc.showLoading()
        scope.launch {
            fetchNotes.executeAsync(UseCaseAsync.None()) { result ->
                viewMvc.hideLoading()
                when (result) {
                    is Either.Left -> viewMvc.showError() //TODO more elaborate
                    is Either.Right -> listenToNoteChanges(result.r)
                }
            }
        }
    }

    private fun listenToNoteChanges(notesFlow: Flow<List<Note>>) {
        scope.launch {
            notesFlow.collect { notes ->
                val parcelableNotes = notes.map { parcelableNoteMapper.mapTo(it) }
                currentState = NotesListState.ShowingList(parcelableNotes)
                viewMvc.setNotes(notes)
            }
        }
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    override fun onNoteClicked(title: String) {
        TODO("Not yet implemented")
    }

    override fun onAddNoteClicked() {
        screenNavigator.openAddNoteScreen()
    }

    internal fun getSavedState(): SavedState = SavedState(currentState)

    internal fun restoreSavedState(savedState: SavedState) {
        currentState = savedState.notesListState
    }

    @Parcelize
    internal data class SavedState(val notesListState: NotesListState): Parcelable

}
