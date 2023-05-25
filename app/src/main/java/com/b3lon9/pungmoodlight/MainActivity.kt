package com.b3lon9.pungmoodlight

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NLog.v("onCreate()")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        vm = MainViewModel(this)

        binding.vm = vm
        // todo(internet connect) : check
        // todo(download wmv resources) : check

        // todo(AD removed) : check

        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
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

        binding.mainBase.startAnimation(fadeOutAnimation)

        initAdMob()

        val data = arrayOf("Berlin", "Moscow", "Tokyo", "Paris")
        binding.mainPicker.setMinValue(0);
        binding.mainPicker.setMaxValue(data.size-1);
        binding.mainPicker.setDisplayedValues(data);
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

    private fun initAdMob():Unit {
        MobileAds.initialize(this)

        val adRequest = AdRequest.Builder().build()
        binding.banner.loadAd(adRequest)
    }
}