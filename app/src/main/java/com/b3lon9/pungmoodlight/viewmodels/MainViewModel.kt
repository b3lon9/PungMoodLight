package com.b3lon9.pungmoodlight.viewmodels

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
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
    private var currentPath:File? = null

    var mainBreath:Boolean = true       /* breath screen */
    var mainMute:Boolean = false        /* sound mute */

    // sound
    var confNext:Boolean = false         /* play next sound */
    var confTimer:Int = 0                /* timer == 0 infinite */

    // color
    var confRandomColor:Boolean = false   /* setting random color */
    lateinit var confColor:Color          /* configure color */

    // ad
    var confRemoveAdvertise = false      /* toggle btn */


    fun popupSetting() {
        val dialogBuilder = AlertDialog.Builder(context)
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.setting_dialog)
        dialog.show()
    }

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

        currentPath = appPath

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
        NLog.d("...play()")
        //val intent = Intent(this, MusicService::class.java)
        //startService(intent)
        val mediaPlayer = MediaPlayer()
        val path = currentPath?.listFiles()?.first()?.path
        mediaPlayer.setDataSource(path)
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
        }
        mediaPlayer.prepare()
    }

    fun stop() {
        NLog.d("...stop()")
    }
}