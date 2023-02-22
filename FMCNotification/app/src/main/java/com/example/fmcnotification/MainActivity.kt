package com.example.fmcnotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessaging.getInstance
import com.google.gson.Gson
import com.onesignal.OneSignal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

const val TOPIC = "/topics/myTopic"
const val CHANNEL_ID = "Test Notification"


class MainActivity : AppCompatActivity() {
    val TAG = "Main_Activity"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getInstance().subscribeToTopic(TOPIC)
        val btnSend: Button = findViewById(R.id.sendData)
        val title: EditText = findViewById(R.id.title)
        val message: EditText = findViewById(R.id.message)
        val token: EditText = findViewById(R.id.token)
        var msg = ""
        if (intent != null) {
            msg = intent.getStringExtra("message").toString()
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        FmcNotification.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)


        getInstance().token.addOnCompleteListener { task ->
            FmcNotification.token = task.result
            if (!task.isSuccessful) {
                Log.w("Fatching FMC Token Feild", task.exception)
                return@addOnCompleteListener
            }

            token.setText(task.result)
        }

        btnSend.setOnClickListener {
            val Title = title.text.toString()
            val Message = message.text.toString()
            val recipientToken = token.text.toString()

            Log.d("NotificationData", "$Title,$Message,    Token $recipientToken")

            if (Title.isNotEmpty() && Message.isNotEmpty() && recipientToken.isNotEmpty()) {

                PushNotification(
                    NotificationData(Title, Message), recipientToken
                ).also {
                    sendNotification(it)
                    Log.d("NotificationData", "$Title,$Message,    Token $recipientToken")
                    Log.d("NotificationData", "${it.data.title},${it.data.message},    ${it.to}")
                }

            }
        }

    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO)
        .launch {

            try {
                val response = RetrofitInstance.api.postNotification(notification)
                if (response.isSuccessful) {
                    Log.d(TAG, "On MessageSend ${Gson().toJson(response.toString())}")
                    Log.d("Send Message", "OnMessage Send Data ${notification.data.title}")
                } else {
                    Log.d(TAG, response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }
        }
}











