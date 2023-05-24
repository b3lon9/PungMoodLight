package com.b3lon9.pungmoodlight

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.b3lon9.nlog.NLog
import com.b3lon9.pungmoodlight.constant.MusicFile
import com.b3lon9.pungmoodlight.databinding.ActivityMainBinding
import com.b3lon9.pungmoodlight.viewmodels.MainViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var vm:MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NLog.v("onCreate()")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        vm = MainViewModel(this)

        binding.vm = vm
        // todo(internet connect) : check
        // todo(download wmv resources) : check

        // todo(AD removed) : check
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

        vm.stop()
    }
}