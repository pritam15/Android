package com.example.todolist

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.NonCancellable.cancel

class MainActivity : AppCompatActivity() {

    lateinit var AddToDo: EditText
    lateinit var addBtn: Button
    lateinit var listView:ListView

    var fileHalper =  FileHalper()
    var list = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AddToDo = findViewById(R.id.add_field)
        addBtn = findViewById(R.id.add)
        listView = findViewById(R.id.list_view)

        list = fileHalper.readData(this@MainActivity)

        var arrayAdapter = ArrayAdapter(this@MainActivity,android.R.layout.simple_list_item_1,android.R.id.text1,list)

        listView.adapter = arrayAdapter

        addBtn.setOnClickListener {
            var iteamName: String = AddToDo.text.toString()
            list.add(iteamName)
            AddToDo.setText("")
            fileHalper.writeData(list,applicationContext)
            arrayAdapter.notifyDataSetChanged()
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            var alert = AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setCancelable(false)
            alert.setNegativeButton("No",DialogInterface.OnClickListener{ dialoginterface, i ->
                 dialoginterface.cancel()
            })
            alert.setMessage("Do you want to delete?")
            alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                list.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHalper.writeData(list,applicationContext)
            })
            alert.create().show()
        }

    }
}