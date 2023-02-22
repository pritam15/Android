package com.example.notetakingapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.notetakingapplication.R

class AddNewNote : AppCompatActivity() {

    lateinit var inputTitle : EditText
    lateinit var inputDescription : EditText
    lateinit var cancelBtn : Button
    lateinit var saveBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_note)

        supportActionBar?.title = "Add Note"

        inputTitle = findViewById(R.id.inputTitle)
        inputDescription = findViewById(R.id.inputDescription)
        cancelBtn = findViewById(R.id.cencelBtn)
        saveBtn = findViewById(R.id.saveBtn)

        cancelBtn.setOnClickListener {
            finish()
        }

        saveBtn.setOnClickListener {
            saveNote()
            finish()
        }


    }

    fun saveNote(){
        val title = inputTitle.text.toString()
        val description = inputDescription.text.toString()
        val intent = Intent()
        intent.putExtra("title",title)
        intent.putExtra("description",description)
        setResult(RESULT_OK,intent)
    }

}