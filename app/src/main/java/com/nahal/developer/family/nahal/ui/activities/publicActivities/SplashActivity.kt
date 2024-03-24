package com.nahal.developer.family.nahal.ui.activities.publicActivities

/**
 * Copyright (c) 2024 farhad moradi
 * farhadmrd@gmail.com
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.widget.ImageView
import com.nahal.developer.family.nahal.R
import com.nahal.developer.family.nahal.core.UAppCompatActivity
import com.nahal.developer.family.nahal.databinding.ActivitySplashBinding
import com.nahal.developer.family.nahal.features.public_feature.CreateAnim.createAnimation
import com.nahal.developer.family.nahal.features.public_feature.OnAnimationListener
import com.nahal.developer.family.nahal.features.public_feature.StarterAnimation
import com.nahal.developer.family.nahal.ui.activities.MainActivity

class SplashActivity : UAppCompatActivity() {

    //    private lateinit var binding: ActivitySplashBinding
    lateinit var imgLogo: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        binding = ActivitySplashBinding.inflate(layoutInflater)
//        Handler().postDelayed({
//            whatToDoNext()
//        }, 2000)
        InitUi()
        usingSplashClass()
    }

    private fun InitUi() {
        imgLogo=findViewById(R.id.imgLogo)
    }

    private fun usingSplashClass() {
        StarterAnimation(
            resList = getAnimList(),
            onAnimationListener = object : OnAnimationListener {
                override fun onRepeat() {

                }

                override fun onEnd() {
                    whatToDoNext()
                }

                override fun onStartAnim() {
                }
            }
        ).startSequentialAnimation(view = imgLogo)
    }

    private fun getAnimList(): ArrayList<Animation> {
        // create list of animations
        val animList: ArrayList<Animation> = ArrayList()

        animList.add(createAnimation(applicationContext, R.anim.no_animaiton))
        animList.add(createAnimation(applicationContext, R.anim.zoom_out_fast))
//        animList.add(createAnimation(applicationContext, R.anim.fade_in))
//        animList.add(createAnimation(applicationContext, R.anim.rotate))

        return animList
    }

    private fun whatToDoNext() {
//        binding.imgLogo.visibility = View.GONE
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}