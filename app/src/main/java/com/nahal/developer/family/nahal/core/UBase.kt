package com.nahal.developer.family.nahal.core
/*
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
import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.transition.Transition
import android.transition.TransitionInflater
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.TransitionRes
import androidx.appcompat.app.AppCompatActivity
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.nahal.developer.family.nahal.event.Event.Companion.ShowErrorForTester


open class UBase : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        base = this
        context = applicationContext
        handler = Handler()
        displayMetrics = resources.displayMetrics
        layoutInflater = LayoutInflater.from(context)
        transitionInflater = TransitionInflater.from(context)
    }

    override fun attachBaseContext(base: Context) {
        try {
            super.attachBaseContext(base)
            MultiDex.install(this)
        } catch (e: Exception) {
            ShowErrorForTester(e, javaClass.name)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null
        var currentActivity: AppCompatActivity? = null
        var layoutInflater: LayoutInflater? = null
            private set
        var transitionInflater: TransitionInflater? = null
            private set
        var handler: Handler? = null
            private set
        var displayMetrics: DisplayMetrics? = null
            private set

        @SuppressLint("StaticFieldLeak")
        private var base: UBase? = null
        fun get(): UBase? {
            return base
        }

        fun getContext(): Context? {
            return if (currentActivity != null) {
                currentActivity
            } else context
        }

        fun inflateTransition(@TransitionRes res: Int): Transition {
            return transitionInflater!!.inflateTransition(res)
        }

        fun inflateLayout(@LayoutRes res: Int): View {
            return layoutInflater!!.inflate(res, null)
        }

        fun inflateLayout(@LayoutRes res: Int, root: ViewGroup?): View {
            return layoutInflater!!.inflate(res, root)
        }
    }
}