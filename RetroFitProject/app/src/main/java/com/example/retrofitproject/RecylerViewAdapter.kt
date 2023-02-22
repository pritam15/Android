package com.example.retrofitproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitproject.databinding.ItemBinding

class RecylerViewAdapter(val postList: ArrayList<Posts>) :
    RecyclerView.Adapter<RecylerViewAdapter.viewHolder>() {

    class viewHolder(val binding : ItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int){
       val currentPost = postList[position]
        holder.binding.tv1.text = currentPost.id.toString()
        holder.binding.tv2.text = currentPost.userId.toString()
        holder.binding.tv3.text = currentPost.title
        holder.binding.tv4.text = currentPost.subTitle
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}