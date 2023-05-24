package com.b3lon9.pungmoodlight.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.b3lon9.nlog.NLog
import com.b3lon9.pungmoodlight.MusicService
import com.b3lon9.pungmoodlight.R
import com.b3lon9.pungmoodlight.constant.MusicFile
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

@SuppressLint("StaticFieldLeak")
class MainViewModel(private val context:Context) : ViewModel() {
    private val resources = context.resources

    fun download() {
        // music folder
        val rootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)

        // theme folder
        val themePath = File("$rootPath/pungmoodlight")

        if (!themePath.exists()) {
            NLog.d("...not exists : ${themePath.path}")

            val success = themePath.mkdir()
            if (!success) {
                Toast.makeText(context, resources.getString(R.string.toast_mkdir_fail), Toast.LENGTH_SHORT).show()
                return
            }
        }

        // app folder
        val appPath = File("$themePath/${MusicFile.FIRE}")

        if (!appPath.exists()) {
            NLog.d("...not exists : ${appPath.path}")
            if (!appPath.mkdir()) {
                Toast.makeText(context, resources.getString(R.string.toast_mkdir_fail), Toast.LENGTH_SHORT).show()
                return
            }
        }

        // firebase - bonfire
        val storage = Firebase.storage
        val list = storage.reference.child(MusicFile.FIRE)

        list.listAll()
            .addOnSuccessListener {
                it.items.forEach {
                    it.getFile(File("$appPath/${it.name}"))
                        .addOnCompleteListener {
                            NLog.d("...download Complete!")
                        }
                        .addOnProgressListener {
                            NLog.v("...progress... : ${it.storage}... ${it.bytesTransferred}/${it.totalByteCount}")
                        }
                }
            }
            .addOnFailureListener {
                NLog.e("...Failure...${list.name}")
            }

    }

    fun play() {

        //val intent = Intent(this, MusicService::class.java)
        //startService(intent)
    }

    fun stop() {

    }
}