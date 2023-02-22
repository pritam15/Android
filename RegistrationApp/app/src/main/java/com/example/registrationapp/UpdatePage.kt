package com.example.registrationapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.registrationapp.databinding.UpdatePageBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.util.*

class UpdatePage : Fragment() {
    val database = FirebaseDatabase.getInstance()
    val reference = database.reference.child("Users")
    lateinit var binding : UpdatePageBinding
    lateinit var applicationContext : Context
    var stroageRef = FirebaseStorage.getInstance().reference

    var imageUri: Uri? = null

    lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        applicationContext  = requireActivity().applicationContext
        binding = UpdatePageBinding.inflate(layoutInflater)
         bundle = this.requireArguments()
        activityResultLauncher()

        if (bundle != null) {
            binding.updateName.setText(bundle.getString("userName"))
            binding.updateAge.setText(bundle.getInt("userAge",0).toString())
            binding.updateEmail.setText(bundle.getString("userEmail"))
            Picasso.get().load(bundle.getString("imageUrl").toString())
                .into(binding.updateProfileImage)
        }

        binding.updateBtn.setOnClickListener {
            uplodeImage()
        }
        binding.updateProfileImage.setOnClickListener {
            chooseImage()
        }
        return binding.root
    }

    fun updateData(url : String, imageName: String){
        val Name = binding.updateName.text.toString()
        val Age = binding.updateAge.text.toString().toInt()
        val Email = binding.updateEmail.text.toString()
        val id = bundle.getString("id").toString()
        var userMap = mutableMapOf<String,Any>()
        userMap["id"] = id
        userMap["userName"] = Name
        userMap["userAge"] = Age
        userMap["userEmail"] = Email
        userMap["url"] = url
        userMap["imageName"] = imageName
        Log.d("###########", userMap.toString())

        reference.child(id).updateChildren(userMap).addOnCompleteListener{ task ->
            if(task.isSuccessful){
                Toast.makeText(requireContext(),"Updated",Toast.LENGTH_SHORT).show()
                binding.updateBtn.isClickable = true
                binding.updateProgressBar.visibility = View.GONE
                binding.textView.visibility = View.VISIBLE
                binding.updateProfileImage.visibility = View.VISIBLE
                Log.d("###########", userMap.toString())
                activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()

            }
        }
            .addOnFailureListener {
                throw UnsupportedOperationException(it)
            }
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)

    }

    fun chooseImage(){
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            activityResultLauncher.launch(intent)
    }

    fun activityResultLauncher(){
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                val resultCode = it.resultCode
                val imageData = it.data

                if(resultCode == Activity.RESULT_OK && imageData != null){
                    imageUri = imageData.data

                    imageUri?.let {
                        Picasso.get().load(it).into(binding.updateProfileImage)
                    }
                }
            })
    }

    fun uplodeImage(){
        binding.updateBtn.isClickable = false
        binding.updateProgressBar.visibility = View.VISIBLE
        binding.textView.visibility = View.GONE
        binding.updateProfileImage.visibility = View.GONE

        //UUID
        val imageName = bundle.getString("imageName").toString()
        val imageRef =  stroageRef.child("Images").child(imageName)

        imageUri?.let { uri ->
            imageRef.putFile(uri).addOnSuccessListener {
                Toast.makeText(applicationContext,"Image Updated",Toast.LENGTH_LONG).show()

                val imageUploadedRef = stroageRef.child("Images").child(imageName)
                imageUploadedRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    updateData(imageUrl, imageName)
                }.addOnFailureListener {
                    Toast.makeText(applicationContext,it.localizedMessage,Toast.LENGTH_LONG).show()
                }



            }.addOnFailureListener{
                Toast.makeText(applicationContext,it.localizedMessage,Toast.LENGTH_LONG).show()
            }


        }
    }
}