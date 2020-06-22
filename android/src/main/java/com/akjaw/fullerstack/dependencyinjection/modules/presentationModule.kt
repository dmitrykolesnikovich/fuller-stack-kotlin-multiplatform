package com.akjaw.fullerstack.dependencyinjection.modules

import com.akjaw.fullerstack.model.ParcelableNote
import com.akjaw.fullerstack.model.ParcelableNoteMapper
import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import com.akjaw.fullerstack.screens.noteslist.NotesListController
import data.Mapper
import data.Note
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val presentationModule = DI.Module("presentationModule") {
    bind() from singleton { ViewMvcFactory(instance(), instance()) }
    bind<Mapper<ParcelableNote, Note>>() with singleton { ParcelableNoteMapper() }
    bind() from singleton { NotesListController(instance(), instance(), instance()) }
}
