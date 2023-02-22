package com.example.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    lateinit var first_btn : Button
    lateinit var second_btn : Button
    lateinit var third_btn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFregment(FragmentFisrt(),false)

        first_btn = findViewById(R.id.first_btn)
        second_btn = findViewById(R.id.second_btn)
        third_btn = findViewById(R.id.third_btn)

        first_btn.setOnClickListener {
            loadFregment(FragmentFisrt(),true)
        }

        second_btn.setOnClickListener {
            loadFregment(FragmentSecond(),true)
        }

        third_btn.setOnClickListener {
            loadFregment(FragmentThird(),true)
        }

    }

    fun loadFregment(freg: Fragment,flag:Boolean){
        var fm : FragmentManager = supportFragmentManager
        var ft : FragmentTransaction = fm.beginTransaction()

        if(flag == false){
            ft.add(R.id.container,freg)
            ft.commit()
        }
        else{
            ft.replace(R.id.container,freg)
            ft.addToBackStack(null)
            ft.commit()
        }

    }


}