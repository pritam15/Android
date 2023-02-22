package com.example.notes.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.EventLogTags.Description
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.GridView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.Model.Note
import com.example.notes.R
import com.example.notes.ViewModel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

   lateinit var  noteViewModel: NoteViewModel
   lateinit var gridView: GridView
   lateinit var recylerView : RecyclerView
   lateinit var recylerViewAdapter: RecylerViewAdapter
   lateinit var gridViewAdapter: GridViewAdapter
   lateinit var addActivityResultLauncher: ActivityResultLauncher<Intent>
   lateinit var updateActivityResultLauncher: ActivityResultLauncher<Intent>
   lateinit var  AllNotes : List<Note>
   lateinit var addBtn: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recylerViewAdapter = RecylerViewAdapter(this)
        recylerView = findViewById(R.id.recylerView)
        recylerView.adapter = recylerViewAdapter
        recylerView.layoutManager = GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false)

        addBtn = findViewById(R.id.addNote)
        addBtn.setOnClickListener {
            val intent = Intent(this,AddNewNote::class.java)
            addActivityResultLauncher.launch(intent)
        }

        activityResultLauncher()

        val viewModelFactory = NoteViewModel.NoteViewModelFactory((application as NoteApplication).repository)
        noteViewModel = ViewModelProvider(this,viewModelFactory).get(NoteViewModel::class.java)

        noteViewModel.allNotes.observe(this){ allNotes ->
            recylerViewAdapter.setData(allNotes)
            AllNotes = allNotes
        }

    }

    fun activityResultLauncher(){

        addActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback { addNote ->
            val resultCode = addNote.resultCode
            val noteData = addNote.data

            if(resultCode == RESULT_OK && noteData != null){
                val title = noteData.getStringExtra("titleInput").toString()
                val description = noteData.getStringExtra("descriptionInput").toString()
                val dateTime = noteData.getLongExtra("dateTimeInput",0)

                val note = Note(title,description)

                noteViewModel.insert(note)
            }
        })
       updateActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
       ActivityResultCallback { updateNote ->
           val resultCode = updateNote.resultCode
           val noteData = updateNote.data

           if(resultCode == RESULT_OK && noteData != null){
               val updateTitle = noteData.getStringExtra("updateTitle").toString()
               val updateDescription = noteData.getStringExtra("updateDescription").toString()
               val id = noteData.getIntExtra("updateID",-1)

               val updateNote = Note(updateTitle,updateDescription)
               updateNote.id = id

               noteViewModel.update(updateNote)
           }
       })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.new_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.addNew -> {
                val intent: Intent = Intent(this, AddNewNote::class.java)
                addActivityResultLauncher.launch(intent)

            }

            R.id.selectAll -> {

            }

            R.id.deleteAll -> {

            }

        }
        return true
    }
}



