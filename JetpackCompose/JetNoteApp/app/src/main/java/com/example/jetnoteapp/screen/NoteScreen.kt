package com.example.jetnoteapp.screen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetnoteapp.R
import com.example.jetnoteapp.components.InputTextField
import com.example.jetnoteapp.components.NoteCard
import com.example.jetnoteapp.components.SaveButton
import com.example.jetnoteapp.data.NotesDataSource
import com.example.jetnoteapp.model.Notes
import kotlinx.coroutines.flow.collect


@SuppressLint("SuspiciousIndentation")
@Composable
fun Note(noteViewModel : NoteViewModel = viewModel()){
    val notesList = noteViewModel.noteList.collectAsState().value


        NoteScreen(
            notesList,
            onNotesAdd = {
               noteViewModel.addNote(it)
            },
            onDeleteNotes = {
               noteViewModel.removeNote(it)
            })

}




@Composable
fun NoteScreen(
                notes:List<Notes>,
                onNotesAdd : (Notes) -> Unit,
                onDeleteNotes: (Notes) -> Unit) {

    val context = LocalContext.current

    Column(modifier = Modifier.padding(6.dp)) {
        TopAppBar(title = {
                          Text(text = stringResource(id = R.string.app_name))
        },
            actions = {
                Icon(imageVector = Icons.Default.Notifications,
                    contentDescription = "Notification Icon")
            },
        modifier = Modifier.padding(6.dp),
        backgroundColor = Color.LightGray)


        // Input Text Field

         var title by remember {
             mutableStateOf("")
         }

        var description by remember {
            mutableStateOf("")
        }

        // Title Input
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            InputTextField(text = title, lable = "Title", onTextChange = {
                if(it.all { char ->
                        char.isLetter() || char.isWhitespace()
                    })title = it
            } )

            InputTextField(text = description, lable = "Write Note", onTextChange = {
                if(it.all { char ->
                        char.isLetter() || char.isWhitespace()
                    })description = it
            } )


            SaveButton(text = "Save", modifier = Modifier.padding(top = 6.dp, bottom = 6.dp),
                onClick = {
                    if(title.isNotEmpty() && description.isNotEmpty()){
                        onNotesAdd(Notes(title = title, description = description))
                        title= ""
                        description = ""
                        Toast.makeText(context,"Note Add",Toast.LENGTH_SHORT).show()
                    }
            })
        }

        Divider(modifier = Modifier.padding(8.dp),
                color = Color.DarkGray, thickness = 2.dp)

        LazyColumn(modifier = Modifier.clickable {
            onDeleteNotes
        }){
            items(notes){ note ->
                 NoteCard(note = note, onNoteClicked = {onDeleteNotes(note)})
                Log.d("OnNoteAdd", "${note.title} ${note.description}")
            }

        }
    }
}




@Preview(showBackground = true)
@Composable
fun NoteScreenPreview(){
    Note()
}