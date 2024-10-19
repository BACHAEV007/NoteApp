package com.example.notes.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.notes.presentation.screen.Screen
import com.example.notes.presentation.common.Constants.DETAIL_ARGUMENT_KEY
import com.example.notes.presentation.common.Constants.DETAIL_ARGUMENT_KEY1
import com.example.notes.presentation.common.Constants.DETAIL_ARGUMENT_KEY2
import com.example.notes.presentation.ui.AddNoteScreen
import com.example.notes.presentation.ui.MyNotesScreen
import com.example.notes.presentation.ui.NoteDetailsScreen
import com.example.notes.presentation.uistate.NoteContent
import com.example.notes.presentation.viewmodel.NoteViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    noteViewModel: NoteViewModel
){
    NavHost(
        navController = navController,
        startDestination = Screen.MyNotes.route
    ){
        composable(
            route = Screen.MyNotes.route
        ){
            MyNotesScreen(
                goToAddNoteScreen = {
                    navController.navigate(Screen.AddNote.route)
                },
                goToNoteDetailsScreen = { id, title, body ->
                    val encodedTitle = Uri.encode(title)
                    val encodedBody = Uri.encode(body)
                    navController.navigate(Screen.NoteDetail.passTitleAndBody(id, encodedTitle, encodedBody))
                },
                noteViewModel = noteViewModel
            )
        }
        composable(
            route = Screen.AddNote.route
        ){
            AddNoteScreen(
                navController = navController,
                goToMyNotesScreen = {
                    navController.navigate(Screen.MyNotes.route){
                        popUpTo(Screen.MyNotes.route) {
                            inclusive = true
                        }
                    }

                },
                noteViewModel = noteViewModel
            )
        }
        composable(
            route = "note_details_screen/{id}/{title}/{body}",
            arguments = listOf(
                navArgument(DETAIL_ARGUMENT_KEY){
                    type = NavType.StringType
                },
                navArgument(DETAIL_ARGUMENT_KEY1){
                    type = NavType.StringType
                },
                navArgument(DETAIL_ARGUMENT_KEY2){
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val body = backStackEntry.arguments?.getString("body") ?: ""

            NoteDetailsScreen(
                navController = navController,
                goToMyNotesScreen = {
                    navController.navigate(Screen.MyNotes.route){
                        popUpTo(Screen.MyNotes.route){
                            inclusive = true
                        }
                    }
                },
                noteViewModel = noteViewModel,
                noteContent = NoteContent(id, title, body)
            )
        }
    }
}


