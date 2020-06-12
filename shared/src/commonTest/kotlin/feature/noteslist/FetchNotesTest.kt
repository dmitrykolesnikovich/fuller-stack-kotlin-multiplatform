package feature.noteslist

import base.usecase.Either
import base.usecase.Failure
import base.usecase.UseCaseAsync
import network.NetworkApiFake
import repository.NoteRepositoryFake
import runTest
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FetchNotesTest {

    private lateinit var noteApiFake : NetworkApiFake
    private lateinit var noteRepositoryFake: NoteRepositoryFake
    private lateinit var SUT : FetchNotes

    @BeforeTest
    fun setUp(){
        noteApiFake = NetworkApiFake()
        noteRepositoryFake = NoteRepositoryFake(noteApiFake)
        SUT = FetchNotes(noteRepositoryFake)
    }

    @JsName("callsTheRepositoryRefreshNotesOnce")
    @Test
    fun `executeAsync calls the repository refreshNotes once`() = runTest {
        SUT.executeAsync(UseCaseAsync.None()) {}
        assertEquals(1, noteRepositoryFake.refreshNotesCallCount)
    }

    @JsName("callsTheCallbackWithFlow")
    @Test
    fun `executeAsync success calls the callback with the repository flow`() = runTest {
        SUT.executeAsync(UseCaseAsync.None()) {
            val flow = (it as Either.Right).r
            assertEquals(noteRepositoryFake.notes, flow)
        }
    }

    @JsName("callsTheCallbackWithFailure")
    @Test
    fun `executeAsync failure calls the callback with ServerError`() = runTest {
        noteApiFake.willFail = true
        SUT.executeAsync(UseCaseAsync.None()) {
            val result = it as Either.Left
            assertEquals(Failure.ServerError, result.l)
        }
    }
}