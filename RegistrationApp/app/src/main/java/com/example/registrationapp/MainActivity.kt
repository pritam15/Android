package com.example.registrationapp

import android.content.DialogInterface
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintSet.INVISIBLE
import androidx.core.view.isInvisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.registrationapp.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    val database = FirebaseDatabase.getInstance()
    val reference = database.reference
   lateinit var adapter : RecylerViewAdapter
    lateinit var userList : ArrayList<User>
     var imageNameList : ArrayList<String> = ArrayList()
    val stroageRef = FirebaseStorage.getInstance().reference

    lateinit var addBtn : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val freg = RegistartionPage()
         userList = ArrayList<User>()

        addBtn = findViewById(R.id.addBtn)

        binding.addBtn.setOnClickListener{
            fragmentTransaction.add(R.id.framLayout,freg).commit()
        }
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id = adapter.getUserID(viewHolder.adapterPosition)
                val imageName = adapter.getImageName(viewHolder.adapterPosition)
                val imageRef = stroageRef.child("Iamges/").child(imageName)
                reference.child("Users").child(id).removeValue()
                imageRef.delete()

            }

        }).attachToRecyclerView(binding.recylerView)
        dataRetrive()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.selectedDelete -> {
                showDialogBox()
            }
            R.id.signOut -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this,SignInPage::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialogBox() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Delete All")
        dialog.setMessage("Do you want to delete all")
        dialog.setNegativeButton("Cancel",DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })
        dialog.setPositiveButton("Yes",DialogInterface.OnClickListener { dialog, which ->

            for(imageName in imageNameList){
                stroageRef.child("Images/").child(imageName).delete()
            }
            reference.child("Users").removeValue().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"Deleted all",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this,task.exception?.toString(),Toast.LENGTH_LONG).show()
                }
            }
        }).show()
    }


    fun dataRetrive(){

        reference.child("Users").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
               for(eachUser in snapshot.children){
                   val user = eachUser.getValue(User::class.java)
                   if(user != null){
                       userList.add(user)
                       imageNameList.add(user.imageName)
                   }
               }

                adapter = RecylerViewAdapter(this@MainActivity,userList)
                binding.recylerView.layoutManager = LinearLayoutManager(this@MainActivity)
                binding.recylerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        actionBar
    }
}