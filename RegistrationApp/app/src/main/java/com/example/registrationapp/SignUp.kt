package com.example.registrationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.registrationapp.databinding.SignUpBinding
import com.example.registrationapp.databinding.SigninPageBinding
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    lateinit var binding: SignUpBinding
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setTitle("Sign Up")

        binding.signupBtn.setOnClickListener {
            val userEmail = binding.inputEmail.text.toString()
            val userPassword = binding.inputPassword.text.toString()

            signUp(userEmail, userPassword)

        }

    }

    private fun signUp(userEmail: String, userPassword: String) {
        auth.createUserWithEmailAndPassword(userEmail,userPassword)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this ,"User Created Successfully",Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, SignInPage::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this,task.exception?.toString(),Toast.LENGTH_LONG).show()
                }
            }


    }
}