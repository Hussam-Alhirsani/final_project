package com.example.shortstories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddStoryViewModel : ViewModel() {

    private val currentUser = FirebaseAuth.getInstance().currentUser

    val storyAdded = MutableLiveData<Boolean>()

    fun addStoryToDatabase(data: Map<String, String>) {
        FirebaseFirestore.getInstance()
            .collection("stories")
            .add(data)
            .addOnSuccessListener {
                storyAdded.value = true
            }
    }
}