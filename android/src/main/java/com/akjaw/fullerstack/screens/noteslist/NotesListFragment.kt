package com.akjaw.fullerstack.screens.noteslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import com.akjaw.fullerstack.screens.common.base.BaseFragment
import org.kodein.di.instance

class NotesListFragment : BaseFragment() {

    companion object {
        private const val SAVED_INSTANCE = "SAVED_INSTANCE"
    }

    private val viewMvcFactory: ViewMvcFactory by instance<ViewMvcFactory>()
    private val notesListController: NotesListController by instance<NotesListController>()
    private lateinit var viewMvc: NotesListViewMvc

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(SAVED_INSTANCE, notesListController.getSavedState())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewMvc = viewMvcFactory.getNotesListViewMvc(container)

        val savedState = savedInstanceState
            ?.getParcelable<NotesListController.SavedState>(SAVED_INSTANCE)

        if(savedState != null){
            notesListController.restoreSavedState(savedState)
        }

        notesListController.bindView(viewMvc, lifecycleScope)
        return viewMvc.rootView
    }

    override fun onStart() {
        super.onStart()
        notesListController.onStart()
    }

    override fun onStop() {
        super.onStop()
        notesListController.onStop()
    }
}
