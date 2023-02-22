package com.example.notetakingapplication.View

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notetakingapplication.Model.Note
import com.example.notetakingapplication.NoteApplication
import com.example.notetakingapplication.R
import com.example.notetakingapplication.ViewModel.NoteViewModel
import com.example.notetakingapplication.ViewModel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel
    lateinit var recylerView: RecyclerView
    lateinit var addActivityResultLauncher: ActivityResultLauncher<Intent>
    lateinit var updateActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recylerView = findViewById(R.id.recylerView)
        recylerView.layoutManager = LinearLayoutManager(this)
        val adapter = RecylerViewAdapter(this)
        recylerView.adapter = adapter

        registerActivityResultLauncher()

        val noteViewModelFactory =  NoteViewModelFactory((application as NoteApplication).repositort)

        noteViewModel = ViewModelProvider(this,noteViewModelFactory).get(NoteViewModel::class.java)

        noteViewModel.allNotes.observe(this) { notes ->
            adapter.setData(notes)
        }

        // delete iteam when swip left and right
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               noteViewModel.delete(adapter.getNote(viewHolder.adapterPosition))
            }

        }).attachToRecyclerView(recylerView)


    }

    fun registerActivityResultLauncher(){

        addActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { resultAddNote ->
                val resultCode = resultAddNote.resultCode
                val noteData = resultAddNote.data

                if(resultCode == RESULT_OK && noteData != null) {
                    val title = noteData?.getStringExtra("title").toString()
                    val description = noteData?.getStringExtra("description").toString()

                    val note = Note(title, description)

                    noteViewModel.insert(note)
                }

            })

            updateActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
                ActivityResultCallback { resultUpdateNote ->
                    val resultCode = resultUpdateNote.resultCode
                    val noteData = resultUpdateNote.data

                    if(resultCode == RESULT_OK && noteData != null){

                        val updateTitle = noteData.getStringExtra("updateTitle").toString()
                        val updateDescription = noteData.getStringExtra("updateDescription").toString()
                        val updateId = noteData.getIntExtra("updateId", -1)

                        val newNote = Note(updateTitle,updateDescription)
                        newNote.id = updateId

                        noteViewModel.update(newNote)

                    }

                })

    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_resource,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.add_note -> {
                val intent = Intent(this,AddNewNote::class.java)
                addActivityResultLauncher.launch(intent)
            }

            R.id.deleteAll -> {
                showDialog()
            }
        }
        return true
    }

    private fun showDialog() {
        val dialogMessage = AlertDialog.Builder(this)
        dialogMessage.setTitle("Delete All Notes")
        dialogMessage.setMessage("If click yes all notes will delete, if you want to delete a specific note , please swip right or left.")
        dialogMessage.setNegativeButton("No",DialogInterface.OnClickListener { dialog, which -> dialog.cancel()  })
        dialogMessage.setPositiveButton("Yes",DialogInterface.OnClickListener { dialog, which ->
            noteViewModel.deleteAllNotes()
        })

        dialogMessage.create().show()
    }
}