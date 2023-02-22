package com.example.recylerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {


    lateinit var adapter: ContactAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var recylerView: RecyclerView = findViewById(R.id.recylerView)
        recylerView.layoutManager = LinearLayoutManager(this@MainActivity)
        var contactList = ArrayList<ContactDetail>()
        contactList.add(ContactDetail("A","6350124436",R.drawable.businessman1))
        contactList.add(ContactDetail("B","9799231020",R.drawable.bussiness_man2))
        contactList.add(ContactDetail("C","7023425004",R.drawable.man_1))
        contactList.add(ContactDetail("D","6350124436",R.drawable.man))
        contactList.add(ContactDetail("E","6350124436",R.drawable.businessman1))
        contactList.add(ContactDetail("F","9799231020",R.drawable.bussiness_man2))
        contactList.add(ContactDetail("G","7023425004",R.drawable.man_1))
        contactList.add(ContactDetail("H","6350124436",R.drawable.man))

        adapter = ContactAdapter(contactList,this@MainActivity)

        recylerView.adapter = adapter


    }
}