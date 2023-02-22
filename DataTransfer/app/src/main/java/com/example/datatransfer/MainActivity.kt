package com.example.datatransfer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userInput_name : EditText = findViewById(R.id.userInput_name)
        val userInput_phoneNO : EditText = findViewById(R.id.userInput_phoneNo)
        val btn_tarnsfer : Button = findViewById(R.id.btn_transfer_toActivity)



        btn_tarnsfer.setOnClickListener {
            val name = userInput_name.text.toString()
            val phoneNO = userInput_phoneNO.text.toString().toLong()

//            Data Transfer to Second Activity Using Bundle
                val bundle = Bundle()

                bundle.putString("name", name)
                bundle.putLong("phoneNo", phoneNO)

                val intent: Intent = Intent(this@MainActivity, SecondActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)



        }
    }
}