package com.example.listfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val secondFragment = SecondFragment()
        setContentView(R.layout.activity_second)
        val fm : FragmentManager = supportFragmentManager
        val ft : FragmentTransaction = fm.beginTransaction()


        val position = intent.getIntExtra("position",0)
        val textView: TextView = findViewById(R.id.textView1)
        textView.text = position.toString()
        val bundle= Bundle()
        bundle.putInt("position", position)
        secondFragment.arguments = bundle

        ft.add(R.id.frame_container,secondFragment)
        ft.commit()
    }
}