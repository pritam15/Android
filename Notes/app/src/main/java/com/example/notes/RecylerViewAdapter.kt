package com.example.notes

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RecylerViewAdapter(var Title:): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        lateinit var titleView: TextView
        lateinit var hint1TextView: TextView
        lateinit var hint2TextView: TextView
    }
}