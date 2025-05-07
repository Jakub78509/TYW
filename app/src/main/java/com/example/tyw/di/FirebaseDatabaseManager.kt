package com.example.tyw.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object FirebaseDatabaseManager {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://tyw-android-project-default-rtdb.europe-west1.firebasedatabase.app")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun uploadDataForUser(weight: Double, date: Long?) {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val userId = user.uid
            val userRef: DatabaseReference = database.getReference("users").child(userId).child("weightData")

            val data = mapOf(
                "weight" to weight,
                "date" to date
            )

            userRef.push().setValue(data)
        }
    }
}
