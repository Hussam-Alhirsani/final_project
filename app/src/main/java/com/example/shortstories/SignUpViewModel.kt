package com.example.shortstories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    val toast = MutableLiveData<String>()
    val accountCreated = MutableLiveData<Boolean>()

    fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                saveUserToFirebase(firstName, lastName, email)
            }
            .addOnFailureListener {
                toast.value = it.localizedMessage
            }
    }

    private fun saveUserToFirebase(firstName: String, lastName: String, email: String) {
        val map = mutableMapOf<String, String>()
        map["firstName"] = firstName
        map["lastName"] = lastName
        map["email"] = email
        db.collection("users")
            .document(email)
            .set(map)
            .addOnSuccessListener {
                toast.value = "Account created successfully"
                accountCreated.value = true
            }.addOnFailureListener {
                toast.value = it.localizedMessage
            }
    }
}