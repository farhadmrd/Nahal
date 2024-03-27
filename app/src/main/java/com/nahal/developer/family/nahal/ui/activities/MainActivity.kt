package com.nahal.developer.family.nahal.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.android.material.card.MaterialCardView
import com.nahal.developer.family.nahal.R
import com.nahal.developer.family.nahal.core.UAppCompatActivity
import com.nahal.developer.family.nahal.event.SoundEngine.Companion.PlayAlarmSound
import com.nahal.developer.family.nahal.madules.fm_Toaster.FMToaster
import com.nahal.developer.family.nahal.ui.activities.testActivities.TestActivity


class MainActivity : UAppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    private lateinit var crdTest: MaterialCardView
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        PlayAlarmSound(this)
        FMToaster.Builder(this)
            .setDescription("برای خروج از برنامه، لطفا دوبار دکمه برگشت را لمس نمائید")
            .setDuration(FMToaster.LENGTH_LONG)
            .setTitle("خروج")
            .setStatus(FMToaster.Status.SUCCESS).show()

        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
        setupEvent()
    }

    private fun setupEvent() {
        crdTest.setOnClickListener {
            val intent = Intent(this@MainActivity, TestActivity::class.java)
            startActivity(intent)
        }
    }


    private fun initUi() {
        crdTest = findViewById(R.id.crdTest)
    }

}