package com.b3lon9.pungmoodlight.viewmodels

import androidx.lifecycle.ViewModel
import com.b3lon9.pungmoodlight.models.SettingModel

class SettingViewModel : ViewModel() {
    private lateinit var data: SettingModel
    private lateinit var listener: SettingDataListener

    interface SettingDataListener {
        fun onSettingData(data: SettingModel)
    }

    init {
        val time = SettingModel.Time()

        data = SettingModel(false, time, false, false)
    }

    fun setSettingListener(listener: SettingDataListener) {
        this.listener = listener
    }

    fun apply():Unit {

        listener.onSettingData(data)
    }
}