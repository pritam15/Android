package com.example.registrationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.registrationapp.databinding.SigninWithPhoneNoBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.Objects
import java.util.concurrent.TimeUnit

class SigninWithPhoneNo : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()
    lateinit var mCallbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var  binding : SigninWithPhoneNoBinding
    private var OTP = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = SigninWithPhoneNoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.getOTPBtn.setOnClickListener {
            val mobileNo = binding.mobileNoInput.text.toString()

            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(mobileNo)
                .setTimeout(60L,TimeUnit.SECONDS)
                .setActivity(this@SigninWithPhoneNo)
                .setCallbacks(mCallbacks)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)

            binding.OTPfield.visibility = View.VISIBLE
            binding.OTPVerificationBtn.visibility = View.VISIBLE
        }

        binding.OTPVerificationBtn.setOnClickListener {
            signin()
        }

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                TODO("Not yet implemented")
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                TODO("Not yet implemented")
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                OTP = p0
            }
        }

    }

    private fun signin(){
        val otp = binding.OTPfield.text.toString()
        val cerdential = PhoneAuthProvider.getCredential(OTP,otp)
        signInWithCerdential(cerdential)
    }

    private fun signInWithCerdential(cerdential: PhoneAuthCredential){
           auth.signInWithCredential(cerdential).addOnCompleteListener {
               if(it.isSuccessful){
                   val intent = Intent(this@SigninWithPhoneNo,MainActivity::class.java)
                   startActivity(intent)
                   finish()
               }
               else{
                   Toast.makeText(this,"OTP Verification Faield",Toast.LENGTH_LONG).show()
               }
           }
    }
}