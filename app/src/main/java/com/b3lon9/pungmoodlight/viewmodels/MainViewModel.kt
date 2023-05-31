package com.b3lon9.pungmoodlight.viewmodels

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Environment
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.b3lon9.nlog.NLog
import com.b3lon9.pungmoodlight.MusicService
import com.b3lon9.pungmoodlight.R
import com.b3lon9.pungmoodlight.constant.MusicFile
import com.b3lon9.pungmoodlight.custom.SettingDialog
import com.b3lon9.pungmoodlight.models.SettingModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FileDownloadTask.TaskSnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.util.Dictionary

@SuppressLint("StaticFieldLeak")
class MainViewModel(private val context:Context) : ViewModel() {
    private val resources = context.resources
    private var currentPath:File? = null
    private var settingDialog:SettingDialog? = SettingDialog(context)

    // path
    val musicFolderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
    val rootFolderPath = "$musicFolderPath/pungmoodlight"

    val data = arrayOf(
        resources.getString(R.string.sound_bonfire),
        resources.getString(R.string.sound_typing),
        resources.getString(R.string.sound_coffeemachine),
        resources.getString(R.string.sound_book),
        resources.getString(R.string.sound_insect),
        resources.getString(R.string.sound_heartbeat),
        resources.getString(R.string.sound_underwater),
        resources.getString(R.string.sound_bird)
    )

    private val sounds = arrayOf("bonfire", "typing", "coffeemachine", "book", "insect", "heartbeat", "underwater", "bird")

    var isSoundFolderAll = ObservableBoolean(true)

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

    fun popupDownload(fileName:String) {
        val dialog = AlertDialog.Builder(context)
            .setTitle("다운로드 알림")
            .setMessage("$fileName 음원 파일을 다운로드 받으세요 !")
            .setPositiveButton("네") { dialog, i ->
                run {
                    NLog.d("... dialog : $dialog, i : $i")
                    val storage = Firebase.storage

                    val dialog = Dialog(context)
                    dialog.setContentView(R.layout.download_dialog)
                    dialog.show()

                    if (fileName.equals(resources.getString(R.string.sound_all))) {

                    } else {
                        val folderName = sounds[data.indexOf(fileName)]
                        NLog.v("...folderName : $folderName")

                        val destinationFilePath = "$rootFolderPath/$folderName"
                        val list = storage.reference.child(folderName)

                        val dicBytes = HashMap<String, Int>()
                        val dicTotal = HashMap<String, Int>()

                        list.listAll()
                            .addOnSuccessListener {
                                it.items.forEach {ref ->
                                    ref.getFile(File("$destinationFilePath/${ref.name}"))
                                        .addOnCompleteListener { snapshot ->
                                            // Toast.makeText(context, resources.getString(R.string.toast_download_complete), Toast.LENGTH_SHORT).show()
                                            NLog.d("...Complete : $snapshot")
                                        }
                                        .addOnSuccessListener { snapshot ->
                                            updateProgress(dicBytes, dicTotal, snapshot)
                                        }
                                        .addOnProgressListener {snapshot ->
                                            updateProgress(dicBytes, dicTotal, snapshot)
                                        }
                                }
                            }
                    }
                }
            }
            .create()

        dialog.show()
    }

    private fun updateProgress(dicBytes:HashMap<String, Int>, dicTotal:HashMap<String, Int>,snapshot:TaskSnapshot) {
        val storageName = snapshot.storage.name
        val storageBytes = snapshot.bytesTransferred.toInt()
        val storageTotal = snapshot.totalByteCount.toInt()

        dicBytes[storageName] = storageBytes
        dicTotal[storageName] = storageTotal


    }


    fun popupSetting() {
        // val dialogBuilder = AlertDialog.Builder(context)
        settingDialog?.let {
            it.settingDataListener(listener)
            it.show()
        }
    }

    fun pickerChangeItem(idx:Int) {
        NLog.d("... pickerChangeItem : $idx")
        NLog.d("... pickerChangeItem : ${sounds[idx]}")
        folderCheck(idx)
    }

    /**
     * setting Dialog -> data listener
     * @data - SettingModel
     * */
    private val listener = object:SettingViewModel.SettingDataListener{
        override fun onSettingData(data: SettingModel) {
            NLog.d("...onSettingData : $data")

            settingDialog?.let {
                it.dismiss()
            }
        }
    }

    fun rootFolderCheck():Boolean {
        var isCreateFolder = true

        val rootFolder = File(rootFolderPath)

        if (!rootFolder.exists()) {
            if (!rootFolder.mkdir()) {
                isCreateFolder = false
            }
        }

        return isCreateFolder
    }

    private fun folderCheck(folderIdx:Int):Unit {
        val soundFolder = File("$rootFolderPath/${sounds[folderIdx]}")
        NLog.d("...folderCheck : ${soundFolder.path}")

        if (!soundFolder.exists()) {
            if (!soundFolder.mkdir()) {
                Toast.makeText(context, resources.getString(R.string.toast_mkdir_fail), Toast.LENGTH_SHORT).show()
                return
            } else NLog.d("... make soundFolder : $soundFolder")
        }

        if (soundFolder.listFiles()!!.isEmpty()) {
            popupDownload(data[folderIdx])
        }
    }

    fun download() {
        // theme folder
        val themePath = File(rootFolderPath)

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