package com.example.recylerview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter (var contactList: ArrayList<ContactDetail>, var context:Context):
    RecyclerView.Adapter<ContactAdapter.ContacViewHolder>(){



    class ContacViewHolder(itemsView: View): RecyclerView.ViewHolder(itemsView){
        var personName: TextView = itemsView.findViewById(R.id.personName)
        var number: TextView = itemsView.findViewById(R.id.personNumber)
        var profileImg: ImageView = itemsView.findViewById(R.id.profileImg)
        var cardView: RelativeLayout = itemsView.findViewById(R.id.contact_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContacViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.contact_list,parent,false)
        return  ContacViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContacViewHolder, position: Int) {
        holder.personName.text = contactList.get(position).Name
        holder.number.text = contactList.get(position).Number
        holder.profileImg.setImageResource(contactList.get(position).profile_img)

        holder.cardView.setOnClickListener{
            var intent:Intent
            intent = Intent(context,ContactPage_2::class.java)
            intent.putExtra("name",contactList.get(position).Name)
            intent.putExtra("number",contactList.get(position).Number)
            intent.putExtra("img",contactList.get(position).profile_img)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return contactList.size
    }
}