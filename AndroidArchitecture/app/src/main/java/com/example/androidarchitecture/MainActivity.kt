package com.example.androidarchitecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelProvider
import kotlin.math.log

class MainActivity : AppCompatActivity(), LifecycleOwner {
    lateinit var mainViewModel : MainViewModel
    lateinit var textview : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycle.addObserver(LifeCycleObserver())
       mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

       textview = findViewById(R.id.textView)
        setText()

        Log.d("Lifecycle Owner", "onCreate: ")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle Owner", "onStart: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle Owner", "onPause: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Lifecycle Owner", "onRestart: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle Owner", "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle Owner", "onDestroy: ")
    }





    fun setText(){
        textview.text = mainViewModel.count.toString()
    }

    fun increment(v: View){
        mainViewModel.increment()
        setText()
    }

    override fun getLifecycle(): Lifecycle {
        return super.getLifecycle()
    }

}



