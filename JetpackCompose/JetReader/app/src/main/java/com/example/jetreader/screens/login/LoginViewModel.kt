package com.example.jetreader.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetreader.model.MUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    val loadingState = MutableStateFlow(LoadingState.IDLE)

    val auth : FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)
    val loading : LiveData<Boolean> = _loading

    fun signInWithEmailAndPassword(email : String, password : String,  home : () -> Unit) = viewModelScope.launch{
         try{
             auth.signInWithEmailAndPassword(email,password)
                 .addOnCompleteListener { task ->
                     if(task.isSuccessful){
                         home()
                     }
                     else{
                         Log.d("User", "signInWithEmailAndPassword ${task.result}")
                     }
                 }
        }
        catch (ex : Exception){
            Log.d("User" , "execption ${ex.localizedMessage}")
        }
    }


    fun createUserWithEmailAndPassword(email : String, password : String,home : () -> Unit){

        if(_loading.value == false){
            _loading.value = true

            try {
                auth.createUserWithEmailAndPassword(email,password,)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            val displayName = task.result.user?.email?.split("@")?.get(0)
                            createUser(displayName)
                            home()
                        }
                        else{
                            Log.d("User", "createUserWithEmailAndPassword ${task.result.toString()}")
                        }
                    }
            }
            catch (ex : Exception){
                Log.d("User", "exception  ${ex.localizedMessage}")
            }

            _loading.value = false
        }

    }

    private fun createUser(displayName: String?) {
            val userId = auth.currentUser?.uid
            val user = MUser(userId = userId.toString(),
                            displayName = displayName.toString(),
                            avatarUrl = "",
                            quote = "",
                            profession = "Developer",
                            id = null
                            ).toMap()


        FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d("User", "User Add to FireStore ${it.id}")
            }
            .addOnFailureListener {
                Log.d("User", "Execption ${it.localizedMessage}")
            }
    }

}











