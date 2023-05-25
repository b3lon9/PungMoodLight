package com.b3lon9.pungmoodlight

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.b3lon9.nlog.NLog
import com.b3lon9.pungmoodlight.databinding.ActivityMainBinding
import com.b3lon9.pungmoodlight.viewmodels.MainViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var vm:MainViewModel

    private val data = arrayOf("BonFire", "Typing", "CoffeeMachine", "Book", "Insect", "HeartBeat", "UnderWater", "WaterFall", "Bird")

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NLog.v("onCreate()")

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        vm = MainViewModel(this)

        binding.vm = vm
        // todo(internet connect) : check
        // todo(download wmv resources) : check

        // todo(AD removed) : check



        /*val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        fadeInAnimation.duration = 3000
        val fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        fadeOutAnimation.duration = 3000

        fadeInAnimation.setAnimationListener(object:Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.mainBase.startAnimation(fadeOutAnimation)
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })

        fadeOutAnimation.setAnimationListener(object:Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.mainBase.startAnimation(fadeInAnimation)
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })

        binding.mainBase.startAnimation(fadeOutAnimation)*/

        initAdMob()

        binding.mainPicker.apply {
            textSize = 120F
            minValue = 0
            maxValue = data.size - 1
            displayedValues = data
        }
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

        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onDestroy() {
        super.onDestroy()
        NLog.v("onDestroy()")

        vm.stop()
    }

    private fun initAdMob():Unit {
        MobileAds.initialize(this)

        val adRequest = AdRequest.Builder().build()
        binding.banner.loadAd(adRequest)
    }
}