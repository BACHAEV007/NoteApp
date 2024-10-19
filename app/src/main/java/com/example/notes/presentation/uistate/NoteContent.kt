package com.example.notes.presentation.uistate

import java.util.UUID

data class NoteContent(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val body: String = ""
)
