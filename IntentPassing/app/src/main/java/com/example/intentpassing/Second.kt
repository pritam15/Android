package com.example.intentpassing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Second : AppCompatActivity() {
    lateinit var backBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        backBtn = findViewById(R.id.backBtn)

        backBtn.setOnClickListener{
            var iBack: Intent
            iBack = Intent(this@Second,MainActivity::class.java)
            startActivity(iBack)
        }
    }
}