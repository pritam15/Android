package com.example.smssending

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    lateinit var messageBox: EditText
    lateinit var phoneNoBox: EditText
    var userMassage : String = ""
    var phoneNumber: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageBox = findViewById(R.id.message_box)
        phoneNoBox = findViewById(R.id.phoneNo_box)
        val sendBtn: Button = findViewById(R.id.send_btn)


        sendBtn.setOnClickListener {
            userMassage = messageBox.text.toString()
            phoneNumber = phoneNoBox.text.toString()
            
            sendSMS(userMassage,phoneNumber)
            

        }

    }
    
    fun sendSMS(message:String,phoneNo: String){
        
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.SEND_SMS) !=
       PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS),1)
        }
        else
        {
            val smsManager : SmsManager
            if(Build.VERSION.SDK_INT >= 23)
            {
               smsManager  = this.getSystemService(SmsManager::class.java)
            }
            else
            {
                smsManager  = SmsManager.getDefault()
            }

            smsManager.sendTextMessage(phoneNumber,null,userMassage,null,null)
            messageBox.setText("")
            phoneNoBox.setText("")
        }
        
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 1 && grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            val smsManager : SmsManager
            if(Build.VERSION.SDK_INT >= 23)
            {
                smsManager  = this.getSystemService(SmsManager::class.java)
            }
            else
            {
                smsManager  = SmsManager.getDefault()
            }
            smsManager.sendTextMessage(phoneNumber,null,userMassage,null,null)
            Toast.makeText(this,"Message Send Sucessfully",Toast.LENGTH_LONG).show()

            messageBox.setText("")
            phoneNoBox.setText("")

        }
    }
}