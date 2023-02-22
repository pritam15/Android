package com.example.intentpassing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var nextBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nextBtn = findViewById(R.id.nextBtn)

        nextBtn.setOnClickListener{
           var  inext: Intent

           inext = Intent(this@MainActivity,Second::class.java)
            startActivity(inext)
        }


    }
}











