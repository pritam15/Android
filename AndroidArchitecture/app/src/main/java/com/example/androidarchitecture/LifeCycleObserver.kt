package com.example.androidarchitecture

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class LifeCycleObserver : DefaultLifecycleObserver {


    override fun onCreate(owner: LifecycleOwner) {
      super.onCreate(owner)
       Log.d("Lifecycle Observer", "onCreate: Created")
    }

    override fun onStart(owner: LifecycleOwner) {
         super.onStart(owner)
       Log.d("Lifecycle Observer", "onStart: Start")
    }

    override fun onPause(owner: LifecycleOwner) {
         super.onPause(owner)
       Log.d("Lifecycle Observer", "onPause: pause")
    }

    override fun onResume(owner: LifecycleOwner) {
      super.onResume(owner)
       Log.d("Lifecycle Observer", "onResume: Resume")
    }

    override fun onStop(owner: LifecycleOwner) {
      super.onStop(owner)
       Log.d("Lifecycle Observer", "onStop: Stop")
    }

    override fun onDestroy(owner: LifecycleOwner) {
       super.onDestroy(owner)
       Log.d("Lifecycle Observer", "onDestroy: Destroy")
    }


}


