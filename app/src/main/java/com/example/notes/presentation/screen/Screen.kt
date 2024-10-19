package com.example.notes.presentation.screen

sealed class Screen (val route: String){
    object MyNotes: Screen(route = "my_notes_screen")
    object AddNote: Screen(route = "add_note_screen")
    object NoteDetail : Screen(route = "note_details_screen/{id}/{title}/{body}") {
        fun passTitleAndBody(id: String, title: String, body: String): String {
            return "note_details_screen/$id/$title/$body"
        }
    }
}