package com.b3lon9.pungmoodlight

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.b3lon9.nlog.NLog
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var storage:FirebaseStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storage = Firebase.storage

        firebaseInit()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun firebaseInit() {
        var storageRef = storage.reference

        // download file
        val pathReference = storageRef.child("bonfire/bonfire1.wav")
    }
}