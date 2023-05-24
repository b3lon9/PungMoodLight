package com.b3lon9.pungmoodlight

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.b3lon9.nlog.NLog
import com.b3lon9.pungmoodlight.databinding.ActivityMainBinding
import com.b3lon9.pungmoodlight.viewmodels.MainViewModel

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