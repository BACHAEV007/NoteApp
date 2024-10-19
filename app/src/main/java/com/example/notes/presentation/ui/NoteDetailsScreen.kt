package com.example.notes.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notes.R
import com.example.notes.presentation.uistate.NoteContent
import com.example.notes.presentation.viewmodel.NoteViewModel

@Composable
fun NoteDetailsScreen (
    navController: NavController,
    goToMyNotesScreen: () -> Unit,
    noteViewModel: NoteViewModel,
    noteContent: NoteContent
) {
    var noteTitle by remember { mutableStateOf(noteContent.title.trim().takeIf { it.isNotEmpty() } ?: "") }
    var noteBody by remember { mutableStateOf(noteContent.body.trim().takeIf { it.isNotEmpty() } ?: "") }
    var isFocusedTitle by remember { mutableStateOf(false) }
    var isFocusedBody by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    val titleHint = "Title..."
    val bodyHint = "Type something..."
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 60.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            IconButton(
                onClick = { goToMyNotesScreen() },
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF3B3B3B))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.chevron_left),
                    contentDescription = "Добавить заметку",
                    tint = Color.White
                )
            }
            IconButton(
                onClick = {
                    if (isEditing) {
                        noteViewModel.editNote(noteContent.id, noteTitle, noteBody)
                        navController.popBackStack()
                    }
                    isEditing = !isEditing
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF3B3B3B))
            ) {
                Icon(
                    painter = painterResource(id = if (isEditing) R.drawable.accept_icon else R.drawable.edit_icon),
                    contentDescription = "Редактировать заметку",
                    tint = (if (isEditing) Color.Green else Color.White),
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        Spacer(
            modifier = Modifier.size(32.dp)
        )
        BasicTextField(
            value = if (noteTitle.isEmpty() && !isFocusedTitle) titleHint else noteTitle,
            onValueChange = { newValue -> noteTitle = newValue },
            textStyle = TextStyle(
                color = if (noteTitle.isEmpty() && !isFocusedTitle) Color.Gray else Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            ),
            enabled = isEditing,
            cursorBrush = SolidColor(Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .onFocusChanged { focusState ->
                    isFocusedTitle = focusState.isFocused
                    if (noteTitle.isEmpty() && !isFocusedTitle) noteTitle = ""
                }
        )
        Spacer(
            modifier = Modifier.padding(8.dp)
        )
        BasicTextField(
            value = if (noteBody.isEmpty() && !isFocusedBody) bodyHint else noteBody,
            onValueChange = { newValue -> noteBody = newValue },
            textStyle = TextStyle(
                color = if (noteBody.isEmpty() && !isFocusedBody) Color.Gray else Color.White,
                fontSize = 16.sp,
            ),
            enabled = isEditing,
            cursorBrush = SolidColor(Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .onFocusChanged { focusState ->
                    isFocusedBody = focusState.isFocused
                    if (noteBody.isEmpty() && !isFocusedBody) noteBody = ""
                }
        )
    }
}
