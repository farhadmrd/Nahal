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

package com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.RestrictTo
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
const val ANIM_ALPHA_MIN = 0f

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
const val ANIM_ALPHA_MAX = 1f

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
const val ANIM_DURATION_300 = 300L

/** Fade in a view. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun View.fadeIn(
    alpha: Float = ANIM_ALPHA_MAX,
    duration: Long = ANIM_DURATION_300,
    listener: (() -> Unit)? = null,
) {

    animate().alpha(alpha)
        .setDuration(duration)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .withEndAction { listener?.invoke() }
        .start()
}

/** Fade out a view. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun View.fadeOut(
    alpha: Float = ANIM_ALPHA_MIN,
    duration: Long = ANIM_DURATION_300,
    listener: (() -> Unit)? = null,
) {

    animate().alpha(alpha)
        .setDuration(duration)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .withEndAction { listener?.invoke() }
        .start()
}

/**
 * Lifecycle aware ValueAnimator wrapper.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class ValueAnimationListener(
    private val lifecycle: Lifecycle,
    private val valueStart: Float = ANIM_ALPHA_MIN,
    private val valueEnd: Float = ANIM_ALPHA_MAX,
    private val duration: Long = ANIM_DURATION_300,
    private val interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    private val updateListener: (Float) -> Unit,
    private val endListener: () -> Unit,
) : LifecycleObserver {

    private var valueAnimator: ValueAnimator? = null

    private val animatorUpdateListener = ValueAnimator.AnimatorUpdateListener { animation ->
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
            updateListener.invoke(animation.animatedValue as Float)
    }

    private val animatorEndListener = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
                endListener.invoke()
        }
    }

    init {
        start()
    }

    private fun start() {
        valueAnimator = ValueAnimator.ofFloat(valueStart, valueEnd)
        valueAnimator?.addUpdateListener(animatorUpdateListener)
        valueAnimator?.addListener(animatorEndListener)
        valueAnimator?.interpolator = interpolator
        valueAnimator?.duration = duration
        valueAnimator?.start()
    }

    fun cancel() {
        valueAnimator?.removeListener(animatorEndListener)
        valueAnimator?.removeUpdateListener(animatorUpdateListener)
        valueAnimator?.cancel()
        valueAnimator = null
    }

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() = Unit

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() = cancel()
}
