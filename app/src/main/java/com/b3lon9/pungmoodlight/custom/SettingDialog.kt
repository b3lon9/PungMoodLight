package com.b3lon9.pungmoodlight.custom

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.NumberPicker
import androidx.databinding.DataBindingUtil
import com.b3lon9.nlog.BuildConfig
import com.b3lon9.nlog.NLog
import com.b3lon9.pungmoodlight.R
import com.b3lon9.pungmoodlight.databinding.SettingDialogBinding
import com.b3lon9.pungmoodlight.viewmodels.SettingViewModel
import com.b3lon9.pungmoodlight.viewmodels.SettingViewModel.SettingDataListener

class SettingDialog(private val context:Context) : Dialog(context) {
    private lateinit var binding:SettingDialogBinding
    private var vm:SettingViewModel = SettingViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NLog.d("..onCreate()")
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.setting_dialog, null, false)

        setContentView(binding.root)

        init()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        NLog.d("..onAttachedToWindow()")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        NLog.d("..onDetachedFromWindow()")
    }

    override fun show() {
        super.show()
        NLog.d("..show()")
    }

    override fun dismiss() {
        super.dismiss()
        NLog.d("..dismiss()")
    }

    fun settingDataListener(listener:SettingDataListener) {
        vm.setSettingListener(listener)
    }


    private fun init():Unit{
        binding.dialog = this
        binding.vm = vm

        val layoutParams = FrameLayout.LayoutParams(binding.root.layoutParams)
        layoutParams.width = (context.resources.displayMetrics.widthPixels * 0.9).toInt()
        binding.root.layoutParams = layoutParams

        binding.hour.apply {
            minValue = 0
            maxValue = 23
            setFormatter(formatter)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                textSize = context.resources.getFloat(R.dimen.setting_timepicker_text_size)
            }
        }

        binding.minute.apply {
            minValue = 0
            maxValue = 60
            setFormatter(formatter)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                textSize = context.resources.getFloat(R.dimen.setting_timepicker_text_size)
            }
        }

        binding.seconds.apply {
            minValue = 0
            maxValue = 60
            setFormatter(formatter)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                textSize = context.resources.getFloat(R.dimen.setting_timepicker_text_size)
            }
        }
    }

    private val formatter = object:NumberPicker.Formatter {
        override fun format(i: Int): String {
            return String.format("%02d", i)
        }

    }
}