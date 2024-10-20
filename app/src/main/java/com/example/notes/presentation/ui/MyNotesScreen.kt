package com.example.notes.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.notes.R
import com.example.notes.presentation.viewmodel.NoteViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyNotesScreen(
    goToAddNoteScreen: () -> Unit,
    goToNoteDetailsScreen: (String, String, String) -> Unit,
    modifier: Modifier = Modifier,
    noteViewModel: NoteViewModel,
) {
    var isHolding by remember { mutableStateOf(false) }
    val notesContent by noteViewModel.notes
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F1F1F))
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp, vertical = 60.dp)
        ){
            Row {
                Text(
                    text = "Notes",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 30.sp
                )
            }
            Spacer(
                modifier = Modifier.padding(16.dp)
            )
            if (notesContent.isEmpty()){
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally)
                    {
                        Image(
                            painter = painterResource(id = R.drawable.non_notes_image),
                            contentDescription = "Нет заметок",
                            contentScale = ContentScale.Fit,
                        )
                        Text(
                            text = "No notes. Let’s fix that!",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 22.sp
                        )
                }
            }
            else{
                LazyColumn {
                    items(notesContent) { note ->
                        Box(
                            modifier = Modifier
                                .background(Color(0xFF3B3B3B), RoundedCornerShape(8.dp))
                                .padding(16.dp)
                                .fillMaxWidth()
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {
                                            isHolding = true
                                        },
                                        onTap = {
                                            val noteTitle =
                                                if (note.title.isBlank()) " " else note.title
                                            val noteBody =
                                                if (note.body.isBlank()) " " else note.body
                                            goToNoteDetailsScreen(note.id, noteTitle, noteBody)
                                        }
                                    )
                                }
                        ) {

                            Text(
                                text = note.title,
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 28.sp,
                                color = Color.White,
                                modifier = modifier.padding(8.dp)
                            )

                            FloatingActionButton(
                                onClick = { noteViewModel.deleteNote(note.id) },
                                containerColor = Color(0xFF3B3B3B),
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .size(20.dp)
                            ) {
                                Icon(painter = painterResource(id = R.drawable.delete_icon),
                                    contentDescription = "Удалить заметку",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp))
                            }
                        }

                        Spacer(
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
            }
        FloatingActionButton(
            onClick = { goToAddNoteScreen() },
            containerColor = Color(0xFF3B3B3B),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(horizontal = 50.dp, vertical = 70.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "Добавить заметку",
                tint = Color.White
            )
        }
    }
}
