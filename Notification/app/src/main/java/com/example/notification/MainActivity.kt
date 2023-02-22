package com.example.notification

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notification.databinding.ActivityMainBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.android.material.timepicker.TimeFormat.CLOCK_12H
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.sendNotification.setOnClickListener {
          val timePicker = MaterialTimePicker.Builder()
              .setTimeFormat(TimeFormat.CLOCK_12H)
              .setHour(currentHour)
              .setMinute(currentMinute)
              .setTitleText("Set Notification Time")
              .build()
            timePicker.show(supportFragmentManager,"1")
            timePicker.addOnPositiveButtonClickListener{
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.hour)
                calendar.set(Calendar.MINUTE,timePicker.minute)
                calendar.set(Calendar.SECOND,0)
                calendar.set(Calendar.MILLISECOND,0)

                val intent = Intent(applicationContext,NotificationReciver::class.java)
                val pendingIntent = if(Build.VERSION.SDK_INT >= 23){
                    PendingIntent.getBroadcast(applicationContext
                        ,100
                        ,intent
                        ,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
                }
                else{
                    PendingIntent.getBroadcast(applicationContext
                        ,100
                        ,intent
                        ,PendingIntent.FLAG_UPDATE_CURRENT)
                }

                val alaramManager: AlarmManager =  getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alaramManager.setInexactRepeating(AlarmManager.RTC_WAKEUP
                    ,calendar.timeInMillis
                    ,AlarmManager.INTERVAL_DAY
                    ,pendingIntent)
                Toast.makeText(applicationContext,"Alaram has been Set", Toast.LENGTH_LONG).show()
            }
        }


    }

}