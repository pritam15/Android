package com.example.listview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var listView: ListView
//    lateinit var countryList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById<ListView>(R.id.listView)
       val countryList = resources.getStringArray(R.array.countryName).asList()
        var adpterView = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,countryList)
        listView.adapter = adpterView

        listView.setOnItemClickListener { adapterView, view, position, id ->
            val countryName: String = adapterView.getItemAtPosition(position).toString()
            Toast.makeText(this@MainActivity,"You Click on $countryName",Toast.LENGTH_SHORT).show()
        }
    }
}