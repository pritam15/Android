package com.example.registrationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.registrationapp.databinding.SigninPageBinding
import com.google.firebase.auth.FirebaseAuth

class SignInPage : AppCompatActivity() {
    lateinit var binding : SigninPageBinding
    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SigninPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setTitle("Sign In")

        binding.signinBtn.setOnClickListener {
            val userEmail = binding.inputEmail.text.toString()
            val userPassword = binding.inputPassword.text.toString()

            signIn(userEmail, userPassword)
        }

        binding.signupBtn.setOnClickListener {
            val intent = Intent(this@SignInPage,SignUp::class.java)
            startActivity(intent)
        }

        binding.signupWithPhoneNo.setOnClickListener { task ->
            val intent = Intent(this, SigninWithPhoneNo::class.java)
            startActivity(intent)
        }
    }

    fun signIn( userEmail : String,  userPassword : String){

        auth.signInWithEmailAndPassword(userEmail,userPassword)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"SignIn Successfully!! Welcome", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this,task.exception?.toString(),Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if(user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}