package com.example.registrationapp

import android.app.Activity.RESULT_OK
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
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.registrationapp.databinding.RegistartionPageBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.util.UUID
import java.util.jar.Manifest


class RegistartionPage : Fragment() {

   lateinit var binding : RegistartionPageBinding
   lateinit var applicationContext : Context

    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var reference = database.reference.child("Users")
    var stroageRef = FirebaseStorage.getInstance().reference

    var imageUri: Uri? = null

    lateinit var activityResultLauncher : ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      applicationContext  = requireActivity().applicationContext
        binding = RegistartionPageBinding.inflate(layoutInflater)

        activityResultLauncher()

        binding.registerBtn.setOnClickListener {
            uplodeImage()
        }
        binding.profileImage.setOnClickListener {
            chooseImage()
        }

        return binding.root
    }



    fun saveUserData(url : String, imageName : String){
        val name = binding.inputName.text.toString()
        val age = binding.inputAge.text.toString().toInt()
        val email = binding.inputEmail.text.toString()
        val id = reference.push().key.toString()

        val user = User(id,name,age,email,url, imageName)
        reference.child(id).setValue(user).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
                activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
                binding.progressBar.visibility = View.GONE
                binding.profileImage.visibility = View.VISIBLE
                binding.textView.visibility = View.VISIBLE
                binding.profileImage.isClickable = true
                activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
            }
            else{
                Toast.makeText(requireContext(),"Not Saved",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun chooseImage(){

        // user permission if user is not give permission at installation time


        if(ContextCompat.checkSelfPermission(applicationContext,android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){ // if permission is not granted at installation time the if block is executed and ask for permission from user
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }
        else{ // if permission the permission is granted at time of installation then else block will be executed
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            activityResultLauncher.launch(intent)

        }
    }

    // this block is run when user grant the permission at run time
    val activityPermissionLauncher =  registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it){
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            activityResultLauncher.launch(intent)
        }
    }

    // this fun is for start activity for choosing an image and set it on an image view
    fun activityResultLauncher(){
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                val resultCode = it.resultCode
                val imageData = it.data

                if(resultCode == RESULT_OK && imageData != null){
                    imageUri = imageData.data

                    imageUri?.let {
                        Picasso.get().load(it).into(binding.profileImage)
                    }
                }
            })
    }

    fun uplodeImage(){
        binding.profileImage.isClickable = false
        binding.progressBar.visibility = View.VISIBLE
        binding.textView.visibility = View.GONE
        binding.profileImage.visibility = View.GONE

        //UUID
        val imageName = UUID.randomUUID().toString()
       val imageRef =  stroageRef.child("Images").child(imageName)

        imageUri?.let { uri ->
            imageRef.putFile(uri).addOnSuccessListener {
//                Toast.makeText(applicationContext,"Image Uploded",Toast.LENGTH_LONG).show()

                val imageUploadedRef = stroageRef.child("Images").child(imageName)
                imageUploadedRef.downloadUrl.addOnSuccessListener { uri ->
                    Toast.makeText(applicationContext,"image url",Toast.LENGTH_LONG).show()
                    val imageUrl = uri.toString()
                    saveUserData(imageUrl,imageName)
                }.addOnFailureListener {
                    Toast.makeText(applicationContext,it.localizedMessage,Toast.LENGTH_LONG).show()
                }



            }.addOnFailureListener{
                Toast.makeText(applicationContext,it.localizedMessage,Toast.LENGTH_LONG).show()
            }


        }
    }
}