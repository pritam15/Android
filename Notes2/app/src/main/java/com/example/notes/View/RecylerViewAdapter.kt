package com.example.notes.View

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.notes.Model.Note
import com.example.notes.R


class RecylerViewAdapter(private val activity: MainActivity): RecyclerView.Adapter<RecylerViewAdapter.ViewHolder>(){

    var notes: List<Note> = ArrayList()


   class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
       val title : TextView = itemView.findViewById(R.id.notesTitle)
       val description: TextView = itemView.findViewById(R.id.notesDescription)
       val dateTime: TextView = itemView.findViewById(R.id.dateTime)
       val canceldtn : ImageView = itemView.findViewById(R.id.cancelIcon)
       val cardView: CardView = itemView.findViewById(R.id.cardView)
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.note_iteam,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNote: Note = notes[position]
        holder.title.text = currentNote.title
        holder.description.text = currentNote.description
        holder.dateTime.text = currentNote.time.toString()

        holder.canceldtn.setOnClickListener {
            activity.noteViewModel.delete(currentNote)
        }

        holder.cardView.setOnClickListener {
            val intent = Intent(activity,UpdateActivity::class.java)
            intent.putExtra("title",currentNote.title)
            intent.putExtra("description",currentNote.description)
            intent .putExtra("id",currentNote.id)
            intent.putExtra("dateTime",currentNote.time.toString())
            activity.updateActivityResultLauncher.launch(intent)

        }
    }

    override fun getItemCount(): Int {
       return notes.size
    }

    fun setData(listNotes: List<Note>){
            this.notes = listNotes
            notifyDataSetChanged()
    }

    fun getNote(position: Int): Note{
        return notes[position]
    }

}