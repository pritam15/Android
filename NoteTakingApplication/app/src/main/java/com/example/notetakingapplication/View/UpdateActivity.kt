package com.example.notetakingapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.notetakingapplication.R

class UpdateActivity : AppCompatActivity() {

    lateinit var updateTitle : EditText
    lateinit var updateDescription : EditText
    lateinit var updateCancelBtn : Button
    lateinit var updateSaveBtn : Button

    var currentId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        supportActionBar?.setTitle("Update Note")

        updateTitle = findViewById(R.id.updateTitle)
        updateDescription = findViewById(R.id.updateDescription)
        updateSaveBtn = findViewById(R.id.updateSaveBtn)
        updateCancelBtn = findViewById(R.id.updateCancelBtn)

        getAndSetData()

        updateCancelBtn.setOnClickListener {
            Toast.makeText(this , "Cancel",Toast.LENGTH_LONG).show()
            finish()
        }

        updateSaveBtn.setOnClickListener {
            updateNote()
            Toast.makeText(this,"Note Update",Toast.LENGTH_LONG).show()
            finish()
        }
    }

    fun updateNote(){

            val UpdateTitle = updateTitle.text.toString()
            val UpdateDescription = updateDescription.text.toString()

            val intent = Intent()
            intent.putExtra("updateTitle",UpdateTitle)
            intent.putExtra("updateDescription",UpdateDescription)

        if(currentId != -1){
            intent.putExtra("updateId", currentId)
            setResult(RESULT_OK,intent)
        }


    }

    fun getAndSetData(){

        val currentTitle = intent.getStringExtra("currentTitle").toString()
        val currentDescription = intent.getStringExtra("currentDescription").toString()
         currentId = intent.getIntExtra("currentId", -1)

        updateTitle.setText(currentTitle)
        updateDescription.setText(currentDescription)
    }
}