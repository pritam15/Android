package com.example.jetnoteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetnoteapp.screen.Note
import com.example.jetnoteapp.screen.NoteViewModel
import com.example.jetnoteapp.ui.theme.JetNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
         MyApp {

//             val noteViewModel =  viewModel<NoteViewModel>()
                val noteViewModel = viewModel<NoteViewModel>()

                Note(noteViewModel = noteViewModel)
         }
        }
    }
}

@Composable
fun MyApp(content : @Composable ()  -> Unit){
    JetNoteAppTheme() {
      Surface(modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background) {
           content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        Note()
    }
}