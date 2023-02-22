package com.example.sendingemail

import android.content.Intent
import android.location.Address
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.example.sendingemail.databinding.ActivityMainBinding
import javax.security.auth.Subject

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.sendBtn.setOnClickListener {

            val emailAddress = mainBinding.emailField.text.toString()
            val subject = mainBinding.subjectField.text.toString()
            val message = mainBinding.messageField.text.toString()

            sendEmail(emailAddress,subject,message)
        }
    }

    fun sendEmail(emailAddress: String, subject: String, message: String){
        val emailAddress = arrayOf(emailAddress)

        val emailIntent = Intent(Intent.ACTION_SEND)
//        emailIntent.data = Uri.parse("mailto:")
        emailIntent.type = "*/*"
        emailIntent.putExtra(Intent.EXTRA_EMAIL,emailAddress)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT,message)

        if(emailIntent.resolveActivity(packageManager)!= null){
            startActivity(Intent.createChooser(emailIntent,"Choose an App"))
        }


    }
}