package com.example.notes.View

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.notes.Model.Note
import com.example.notes.R

class GridViewAdapter: BaseAdapter() {

    lateinit var title: TextView
    lateinit var description: TextView
    lateinit var dateTime: TextView
    lateinit var deleteBtn: ImageView
    var notes: List<Note> = ArrayList()


    override fun getCount(): Int {
        return notes.size
    }

    override fun getItem(position: Int): Any {
        return notes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.note_iteam, parent, false)
        val currentNote: Note = getItem(position) as Note

        title = view.findViewById(R.id.notesTitle)
        description = view.findViewById(R.id.notesDescription)
        dateTime = view.findViewById(R.id.dateTime)
        deleteBtn = view.findViewById(R.id.cancelIcon)

        title.text = currentNote.title
        description.text = currentNote.description
        dateTime.text = currentNote.time.toString()


        return view
    }

    fun setData(list: List<Note>) {
        this.notes = list
        notifyDataSetChanged()
    }



}