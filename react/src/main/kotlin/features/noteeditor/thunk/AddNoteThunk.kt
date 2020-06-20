package features.noteeditor.thunk

import base.usecase.Either
import base.usecase.Failure
import base.usecase.UseCaseAsync
import data.Note
import dependencyinjection.KodeinEntry
import feature.noteslist.AddNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.kodein.di.instance
import redux.RAction
import redux.WrapperAction
import store.AppState
import store.RThunk
import store.nullAction

class AddNoteThunk(private val scope: CoroutineScope, private val note: Note) : RThunk {
    private val addNoteUseCaseAsync by KodeinEntry.di.instance<AddNote>()

    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {
        console.log("AddNoteThunk invoke")
        scope.launch {
            addNoteUseCaseAsync.executeAsync(
                note
            ) { result -> handleResult(dispatch, result) }
        }

        return nullAction
    }

    private fun handleResult(dispatch: (RAction) -> WrapperAction, result: Either<Failure, UseCaseAsync.None>) {
        when (result) {
            is Either.Left -> TODO()
            is Either.Right -> {/* empty */}
        }
    }
}