package com.nahal.developer.family.nahal.madules.fm_Adapter.animation
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
import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator

/**
 * item 缩放进入的动画
 * An animation to scale item in, changing item's scaleX and scaleY from default 0.5f to 1.0f in default 300ms.(Using a DecelerateInterpolator with default factor.)
 */
class ScaleInAnimation @JvmOverloads constructor(
    private val duration: Long = 300,
    private val mFrom: Float = DEFAULT_SCALE_FROM
) : ItemAnimator {

    private val interpolator = DecelerateInterpolator()

    override fun animator(view: View): Animator {
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", mFrom, 1f)

        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", mFrom, 1f)

        val animatorSet = AnimatorSet()
        animatorSet.duration = duration
        animatorSet.interpolator = interpolator
        animatorSet.play(scaleX).with(scaleY)

        return animatorSet
    }

    companion object {
        private const val DEFAULT_SCALE_FROM = .5f
    }
}