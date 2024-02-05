package com.nahal.developer.family.nahal.ui.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.nahal.developer.family.nahal.R
import com.nahal.developer.family.nahal.core.UAppCompatActivity
import com.nahal.developer.family.nahal.madules.fm_Toaster.colorFull.FMToaster

class MainActivity : UAppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true

        FMToaster.Builder(this)
            .setDescription("برای خروج از برنامه، لطفا دوبار دکمه برگشت را لمس نمائید")
            .setDuration(FMToaster.LENGTH_LONG)
            .setTitle("خروج")
            .setStatus(FMToaster.Status.WARNING).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}