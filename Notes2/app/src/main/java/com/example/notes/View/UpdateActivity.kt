package com.example.notes.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Config.DEBUG
import android.util.Log
import android.util.Log.DEBUG
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Update
import com.example.notes.BuildConfig.DEBUG
import com.example.notes.R

class UpdateActivity : AppCompatActivity() {

    lateinit var titleUpdate : EditText
    lateinit var descriptionUpdate : EditText
    lateinit var dateTimeUpdate : TextView
    lateinit var cancelBtnUpdate : Button
    lateinit var saveBtnUpdate : Button

    var currentId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        supportActionBar?.title = "Update Note"
         titleUpdate = findViewById(R.id.titleUpdate)
         descriptionUpdate = findViewById(R.id.descriptionUpdate)
         dateTimeUpdate = findViewById(R.id.dateTimeUpdate)
         cancelBtnUpdate = findViewById(R.id.cancelBtnUpdate)
         saveBtnUpdate = findViewById(R.id.saveBtnUpdate)

        titleUpdate.setText(intent.getStringExtra("title"))
        descriptionUpdate.setText(intent.getStringExtra("description"))
        currentId = intent.getIntExtra("id",-1)

        saveBtnUpdate.setOnClickListener {
            val updateTitle = titleUpdate.text.toString()
            val updateDescription = descriptionUpdate.text.toString()

           val intent = Intent()
            intent.putExtra("updateTitle",updateTitle)
            intent.putExtra("updateDescription",updateDescription)

            if(currentId != -1){
                intent.putExtra("updateID",currentId)
                setResult(RESULT_OK,intent)
            }

            Toast.makeText(this,"Update",Toast.LENGTH_LONG).show()
            finish()

        }

        cancelBtnUpdate.setOnClickListener {
            Toast.makeText(this,"Back",Toast.LENGTH_LONG).show()
            finish()
        }



    }
}