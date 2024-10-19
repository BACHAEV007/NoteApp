package com.example.notes.data.repository

import com.example.notes.data.datasource.NoteDataSource
import com.example.notes.data.entity.Note
import com.example.notes.domain.repository.NoteRepository

class NoteRepositoryImpl(
    private val noteDataSource: NoteDataSource
) : NoteRepository {

    override suspend fun addNote(note: com.example.notes.domain.model.Note) {
        val dataNote = Note(id = note.id, title = note.title, body = note.body)
        noteDataSource.saveNote(dataNote)
    }

    override suspend fun updateNote(note: com.example.notes.domain.model.Note) {
        val dataNote = Note(id = note.id, title = note.title, body = note.body)
        noteDataSource.updateNote(dataNote)
    }
    override suspend fun deleteNote(noteId: String) {
        noteDataSource.deleteNote(noteId)
    }
    override suspend fun loadNotes(): List<com.example.notes.domain.model.Note> {
        val notes = noteDataSource.loadNotes()
        return notes.map { note ->
            com.example.notes.domain.model.Note(note.id, note.title, note.body)
        }
    }
}