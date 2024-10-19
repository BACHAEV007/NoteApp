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
import com.example.notes.presentation.viewmodel.NoteViewModel

@Composable
fun AddNoteScreen (
    navController: NavController,
    goToMyNotesScreen: () -> Unit,
    noteViewModel: NoteViewModel
){
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var isFocusedTitle by remember { mutableStateOf(false) }
    var isFocusedBody by remember { mutableStateOf(false) }
    val titleHint = "Title..."
    val bodyHint = "Type something..."
    Column (
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 60.dp)
    ){
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ){
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
                    noteViewModel.addNote(title, body)
                    navController.popBackStack()
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF3B3B3B))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.save_button),
                    contentDescription = "Добавить заметку",
                    tint = Color.White
                )
            }
        }
        Spacer(
            modifier = Modifier.size(32.dp)
        )
        BasicTextField(
            value = if (title.isEmpty() && !isFocusedTitle) titleHint else title,
            onValueChange = { newValue -> title = newValue },
            textStyle = TextStyle(
                color = if (title.isEmpty() && !isFocusedTitle) Color.Gray else Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            ),
            cursorBrush = SolidColor(Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .onFocusChanged { focusState ->
                    isFocusedTitle = focusState.isFocused
                    if (title.isEmpty() && !isFocusedTitle) title = ""
                }
        )
        Spacer(
            modifier = Modifier.padding(8.dp)
        )
        BasicTextField(
            value = if (body.isEmpty() && !isFocusedBody) bodyHint else body,
            onValueChange = { newValue -> body = newValue },
            textStyle = TextStyle(
                color = if (body.isEmpty() && !isFocusedBody) Color.Gray else Color.White,
                fontSize = 16.sp,
            ),
            cursorBrush = SolidColor(Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .onFocusChanged { focusState ->
                    isFocusedBody = focusState.isFocused
                    if (body.isEmpty() && !isFocusedBody) body = ""
                }
        )
    }
}