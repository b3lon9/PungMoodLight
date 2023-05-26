package com.b3lon9.pungmoodlight.models

data class SettingModel(
    var isPlayNextSound:Boolean,
    var time:Time,
    var isRandomColor:Boolean,
    var isRemoveAdvertise:Boolean
) {
    class Time {
        var hour:Int = 0
        var minute:Int = 0
        var second:Int = 0
    }
}
