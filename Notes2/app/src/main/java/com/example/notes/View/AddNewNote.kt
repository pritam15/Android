package com.example.notes.View

import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.notes.R
import org.w3c.dom.Text
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class AddNewNote : AppCompatActivity() {

    lateinit var titleInput : EditText
    lateinit var dateTime : TextView
    lateinit var descriptionInput: TextView
    lateinit var saveBtn : Button
    lateinit var cancelBtn : Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_note)

        supportActionBar?.setTitle("Create New Note")

        titleInput = findViewById(R.id.titleInput)
        dateTime = findViewById(R.id.dateTimeInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        cancelBtn = findViewById(R.id.cancelBtn)
        saveBtn = findViewById(R.id.saveBtn)

        val calender = Calendar.getInstance()

        saveBtn.setOnClickListener {
            saveNote()
            Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show()
            finish()


        }

        cancelBtn.setOnClickListener {
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveNote(){
        val replyintent = Intent()
        val titleInput = titleInput.text.toString()
        val descriptionInput = descriptionInput.text.toString()
        val dateTimeInput = LocalDateTime.now()
        val formater = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

        replyintent.putExtra("titleInput",titleInput)
        replyintent.putExtra("descriptionInput", descriptionInput)
        replyintent.putExtra("dateTimeInput", dateTimeInput)
        setResult(RESULT_OK,replyintent)
    }
}