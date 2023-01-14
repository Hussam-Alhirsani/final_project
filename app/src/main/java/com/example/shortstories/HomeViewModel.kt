package com.example.shortstories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel: ViewModel() {

    val loggedOut = MutableLiveData<Boolean>()

    val storyListLiveData = MutableLiveData<List<Story>>()
    val userLiveData = MutableLiveData<User>()

    init {
        getStories()
        getUser()
    }

    private fun getUser() {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.email ?: "")
            .addSnapshotListener { snapshhot, err ->
                snapshhot?.toObject(User::class.java)?.let { user ->
                    userLiveData.value = user
                }
            }
    }

    private fun getStories() {
        FirebaseFirestore.getInstance()
            .collection("stories")
            .addSnapshotListener { querySnapshot, error ->
                val storyList = mutableListOf<Story>()
                querySnapshot?.documents?.map {
                    val story = it.toObject(Story::class.java)
                    if (story != null) {
                        storyList.add(story)
                    }
                    storyListLiveData.value = storyList
                }
            }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        loggedOut.value = true
    }
}