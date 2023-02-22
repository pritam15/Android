package com.example.recylerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class ContactPage_2 : AppCompatActivity() {
    lateinit var P_img: ImageView
    lateinit var P_name: TextView
    lateinit var P_number: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_page2)

        P_img = findViewById(R.id.personImage)
        P_name = findViewById(R.id.contactPerson)
        P_number = findViewById(R.id.contactNumber)

       var intent: Intent = getIntent()

       var name = intent.getStringExtra("name")
        var number = intent.getStringExtra("number")
        var img = intent.getIntExtra("img",0)

        P_img.setImageResource(img)
        P_name.text = name
        P_number.text  = number


    }
}