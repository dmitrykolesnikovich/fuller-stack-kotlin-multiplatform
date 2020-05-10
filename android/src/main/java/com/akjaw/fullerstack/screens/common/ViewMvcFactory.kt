package com.akjaw.fullerstack.screens.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.akjaw.fullerstack.screens.notes_list.NotesListViewMvc

class ViewMvcFactory(private val layoutInflater: LayoutInflater){
    fun getNotesListViewMvc(parent: ViewGroup?) = NotesListViewMvc(layoutInflater, parent)
}