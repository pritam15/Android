package com.example.registrationapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.registrationapp.databinding.ItemViewBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class RecylerViewAdapter(val context: Context,val usersList:ArrayList<User>) : RecyclerView.Adapter<RecylerViewAdapter.Viewholder>() {

    class Viewholder(val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val currentUser = usersList[position]

        holder.binding.userName.text = currentUser.userName
        holder.binding.userAge.text = currentUser.userAge.toString()
        holder.binding.userEmail.text = currentUser.userEmail

        val imageUrl = currentUser.imageUrl
        Picasso.get().load(imageUrl).into(holder.binding.imageView, object: Callback{
            override fun onSuccess() {
              Log.d("@@@@@@@@@@","Run")
            }

            override fun onError(e: Exception?) {
                TODO("Not yet implemented")
            }
        })

       holder.binding.Item.setOnClickListener { view ->
           val appCompactActivity = view.context as AppCompatActivity
           val updatePage = UpdatePage()

           val bundle = Bundle()
           bundle.putString("userName",currentUser.userName)
           bundle.putInt("userAge",currentUser.userAge)
           bundle.putString("userEmail",currentUser.userEmail)
           bundle.putString("id",currentUser.id)
           bundle.putString("imageUrl",currentUser.imageUrl)
           bundle.putString("imageName",currentUser.imageName)
           updatePage.arguments = bundle
           appCompactActivity.supportFragmentManager.beginTransaction()
               .add(R.id.framLayout,updatePage).commit()

       }

    }

    override fun getItemCount(): Int {
       return usersList.size
    }

    fun getUserID(position: Int) : String{
        return usersList[position].id
    }

    fun getImageName(position: Int) : String{
        return  usersList[position].imageName
    }
}