package com.example.fmcnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

const val FMCCHANNEL = "Firebase Notification"
class FmcNotification : FirebaseMessagingService() {


    companion object{
        var sharedPref : SharedPreferences? = null

        var token : String?
        get(){
            return sharedPref?.getString("token","")
        }
        set(value){
            sharedPref?.edit()?.putString("token",value)?.apply()
        }
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        token = newToken
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {

        Log.d("@@@@@", "onMessageReceived: ${message.data["title"]},${message.data["body"]}")
        super.onMessageReceived(message)

        Log.w("!!!!!!!!!!","${message.notification?.title}")

        val intent = Intent(this, MainActivity::class.java)

        val notificationManager : NotificationManager
            = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random.nextInt()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel(notificationManager)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("message",message.data["title"])
        val pendingIntent: PendingIntent
        pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getActivity(
                this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
        val notification = NotificationCompat.Builder(applicationContext, FMCCHANNEL)
            .setContentTitle(message.notification?.title.toString())
            .setContentText(message.notification?.body.toString())
            .setPriority(IMPORTANCE_HIGH)
            .setSubText(message.data["message"])
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.instagram_icon)
            .build()
        Log.d("######Notification", "onMessageReceived:${message.data["title"]} ${message.data["message"]}")
        notificationManager.notify(notificationID,notification)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun notificationChannel(Nm : NotificationManager){
        val channelName = " Notification Channel"
        val channel = NotificationChannel(FMCCHANNEL,channelName,IMPORTANCE_HIGH).apply {
            description = " My Notification Channel description"
            enableLights(true)
            lightColor = Color.BLUE
        }
        Nm.createNotificationChannel(channel)
    }
}