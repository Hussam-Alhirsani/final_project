package com.example.shortstories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    val toast = MutableLiveData<String>()
    val loggedIn = MutableLiveData<Boolean>()

    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                toast.value = "Login successful"
                loggedIn.value = true
            }.addOnFailureListener {
                toast.value = "Check your data"
            }
    }
}