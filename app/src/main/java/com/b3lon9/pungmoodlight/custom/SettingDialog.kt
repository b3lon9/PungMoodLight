package com.b3lon9.pungmoodlight.custom

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.b3lon9.nlog.NLog
import com.b3lon9.pungmoodlight.R
import com.b3lon9.pungmoodlight.databinding.SettingDialogBinding

class SettingDialog(private val context:Context) : Dialog(context) {
    private lateinit var binding:SettingDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NLog.d("..onCreate()")
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.setting_dialog, null, false)
        setContentView(binding.root)

        val layoutParams = FrameLayout.LayoutParams(binding.root.layoutParams)
        layoutParams.width = (context.resources.displayMetrics.widthPixels * 0.9).toInt()
        binding.root.layoutParams = layoutParams


        init()
    }

    override fun show() {
        super.show()
        NLog.d("..show()")
    }

    override fun dismiss() {
        super.dismiss()
        NLog.d("..dismiss()")
    }

    private fun init():Unit{
        binding.hour.apply {
            minValue = 0
            maxValue = 23
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                textSize = context.resources.getFloat(R.dimen.setting_timepicker_text_size)
            }
        }

        binding.minute.apply {
            minValue = 0
            maxValue = 60
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                textSize = context.resources.getFloat(R.dimen.setting_timepicker_text_size)
            }
        }

        binding.seconds.apply {
            minValue = 0
            maxValue = 60
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                textSize = context.resources.getFloat(R.dimen.setting_timepicker_text_size)
            }
        }
    }
}