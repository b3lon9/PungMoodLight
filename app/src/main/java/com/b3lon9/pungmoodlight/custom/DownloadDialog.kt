package com.b3lon9.pungmoodlight.custom

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.b3lon9.pungmoodlight.R
import com.b3lon9.pungmoodlight.databinding.DownloadDialogBinding

class DownloadDialog(private val context:Context) : Dialog(context) {
    private lateinit var binding:DownloadDialogBinding

    init {
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.download_dialog, null, false)
        setContentView(binding.root)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    override fun dismiss() {
        super.dismiss()

        binding.progressMsg.text = context.resources.getString(R.string.download_init)
    }

    fun progressMessage(progress:Int) {
        binding?.progressMsg?.text = String.format("%d%%", progress)
    }
}