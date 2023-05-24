package com.b3lon9.pungmoodlight

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.b3lon9.nlog.NLog

/**
 * onCreate() - onStartCommand() - service - onDestroy()
 *
 * onCreate() : 서비스 생성 시에만 호출 - 초기 과정 수행
 * onStartCommand() : 원하는 동작 과정 수행
 * 다른 앱에서 service를 재실행하면 onCreate()는 호출되지 않고, onStartCommand()만 호출 됨
 * */
class MusicService:Service() {
    override fun onCreate() {
        super.onCreate()
        NLog.v("onCreate()")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        NLog.v("onStartCommand intent:$intent, flags:$flags, startId:$startId")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        NLog.v("onDestroy()")
    }

    override fun onBind(p0: Intent?): IBinder? {
        NLog.v("onBind()")
        return null
    }
}