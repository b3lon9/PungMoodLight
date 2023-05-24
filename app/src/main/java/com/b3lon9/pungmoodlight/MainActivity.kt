package com.b3lon9.pungmoodlight

import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.b3lon9.nlog.NLog
import com.b3lon9.pungmoodlight.constant.MusicFile
import com.b3lon9.pungmoodlight.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NLog.v("onCreate()")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // todo(internet connect) : check
        // todo(download wmv resources) : check

        // todo(AD removed) : check

        // firebaseInit(Firebase.storage)
    }

    override fun onResume() {
        super.onResume()
        NLog.v("onResume()")
    }

    override fun onPause() {
        super.onPause()
        NLog.v("onPause()")
    }

    override fun onStop() {
        super.onStop()
        NLog.v("onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        NLog.v("onDestroy()")
    }

    fun download(v: View) {
        // music folder
        val rootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)

        // theme folder
        val themePath = File("$rootPath/pungmoodlight")

        if (!themePath.exists()) {
            NLog.d("...not exists : ${themePath.path}")

            val success = themePath.mkdir()
            if (!success) {
                Toast.makeText(this, resources.getString(R.string.toast_mkdir_fail), Toast.LENGTH_SHORT).show()
                return
            }
        }

        // app folder
        val appPath = File("$themePath/${MusicFile.FIRE}")
        currentPath = appPath

        if (!appPath.exists()) {
            NLog.d("...not exists : ${appPath.path}")
            if (!appPath.mkdir()) {
                Toast.makeText(this, resources.getString(R.string.toast_mkdir_fail), Toast.LENGTH_SHORT).show()
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

    private var currentPath:File? = null
    fun play(v: View) {
        NLog.d("...list : ${currentPath?.listFiles() }}")
    }
}