package com.example.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReciver: BroadcastReceiver() {
    val channel_id = "1"

    override fun onReceive(context: Context?, intent: Intent?) {
        if(context != null){
            val builder = NotificationCompat.Builder(context,channel_id)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channel = NotificationChannel(channel_id,"1", NotificationManager.IMPORTANCE_HIGH)
                val manager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)

                builder.setSmallIcon(R.drawable.notification)
                    .setContentTitle("Title")
                    .setContentText("Notification Text")

            }
            else{
                builder.setSmallIcon(R.drawable.notification)
                    .setContentTitle("Notification title")
                    .setContentText("Notification")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            }

            val notificationManagerCompact = NotificationManagerCompat.from(context)
            notificationManagerCompact.notify(1,builder.build())
        }

    }
}