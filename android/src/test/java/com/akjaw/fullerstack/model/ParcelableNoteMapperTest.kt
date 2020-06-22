package com.akjaw.fullerstack.model

import com.soywiz.klock.DateTime
import data.Note
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ParcelableNoteMapperTest {

    companion object {
        private val NOTE = Note(
            title = "title",
            content = "content",
            creationDate = DateTime(0)
        )
        private val PARCELABLE_NOTE = ParcelableNote(
            title = "title",
            content = "content",
            creationDate = DateTime(0)
        )
    }

    private lateinit var SUT: ParcelableNoteMapper

    @BeforeEach
    fun setUp(){
        SUT = ParcelableNoteMapper()
    }

    @Test
    fun `Correctly maps Note to ParcelableNote`(){
        val result = SUT.mapFrom(PARCELABLE_NOTE)

        assertEquals(result, NOTE)
    }

    @Test
    fun `Correctly maps ParcelableNote to Note`(){
        val result = SUT.mapTo(NOTE)

        assertEquals(result, PARCELABLE_NOTE)
    }
}
