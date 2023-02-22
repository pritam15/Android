package com.example.notetakingapplication.View

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notetakingapplication.Model.Note
import com.example.notetakingapplication.R

class RecylerViewAdapter(private val activity: MainActivity) : RecyclerView.Adapter<RecylerViewAdapter.viewHolder>() {

    var notes: List<Note> = ArrayList()

     class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
         val title = itemView.findViewById<TextView>(R.id.title)
         val description = itemView.findViewById<TextView>(R.id.description)
         val cardView = itemView.findViewById<CardView>(R.id.cardView)
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
       val view : View = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
      val currentNote : Note = notes[position]
        holder.title.text = currentNote.title
        holder.description.text = currentNote.descripton

        holder.cardView.setOnClickListener {
            val intent = Intent(activity,UpdateActivity::class.java)
            intent.putExtra("currentTitle", currentNote.title)
            intent.putExtra("currentDescription",currentNote.descripton)
            intent.putExtra("currentId",currentNote.id)
            activity.updateActivityResultLauncher.launch(intent)
        }

    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setData(myNotes: List<Note>){
        this.notes = myNotes
        notifyDataSetChanged()
    }

    fun getNote(position: Int) : Note{
        return notes[position]
    }
}