package com.example.notes.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.domain.model.Note
import com.example.notes.domain.usecase.AddNoteUseCase
import com.example.notes.domain.usecase.DeleteNoteUseCase
import com.example.notes.domain.usecase.EditNoteUseCase
import com.example.notes.domain.usecase.LoadNotesUseCase
import com.example.notes.presentation.uistate.NoteContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val editNoteUseCase: EditNoteUseCase,
    private val loadNotesUseCase: LoadNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {
    val notes: State<List<NoteContent>>
        get() = _notes
    private val _notes: MutableState<List<NoteContent>> = mutableStateOf(emptyList())

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            val loadedNotes = loadNotesUseCase()
            _notes.value = loadedNotes.map { note ->
                NoteContent(id = note.id, title = note.title, body = note.body)
            }
        }
    }

    fun addNote(title: String, body: String) {
        val noteId = UUID.randomUUID().toString()
        val newNote = NoteContent(
            id = noteId,
            title = title,
            body = body
        )
        _notes.value = _notes.value + newNote
        val noteToSave = Note(
            id = noteId,
            title = title,
            body = body
        )
        viewModelScope.launch(Dispatchers.IO) {
            addNoteUseCase(noteToSave)
        }
    }
    fun editNote(noteId: String, newTitle: String, newBody: String) {
        val updatedNotes = _notes.value.map { note ->
            if (note.id == noteId) {
                note.copy(title = newTitle, body = newBody)
            } else {
                note
            }
        }
        _notes.value = updatedNotes
        viewModelScope.launch(Dispatchers.IO) {
            editNoteUseCase(Note(noteId, newTitle, newBody))
        }
    }
    fun deleteNote(noteId: String) {
        _notes.value = _notes.value.filter { note -> note.id != noteId }
        viewModelScope.launch(Dispatchers.IO) {
            deleteNoteUseCase(noteId)
        }
    }
}

