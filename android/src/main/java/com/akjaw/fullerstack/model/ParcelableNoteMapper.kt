package com.akjaw.fullerstack.model

import data.Mapper
import data.Note

class ParcelableNoteMapper : Mapper<ParcelableNote, Note> {

    override fun mapFrom(input: ParcelableNote): Note {
        return Note(
            title = input.title,
            content = input.content,
            creationDate = input.creationDate
        )
    }

    override fun mapTo(input: Note): ParcelableNote {
        return ParcelableNote(
            title = input.title,
            content = input.content,
            creationDate = input.creationDate
        )
    }

}
