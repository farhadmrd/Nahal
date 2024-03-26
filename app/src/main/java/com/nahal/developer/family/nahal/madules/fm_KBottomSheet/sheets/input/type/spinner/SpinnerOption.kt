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

@file:Suppress("unused")

package com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.input.type.spinner

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Data class to hold the spinner options
 */
class SpinnerOption internal constructor() {

    internal var drawable: Drawable? = null
        private set

    @DrawableRes
    internal var drawableRes: Int? = null
        private set

    internal var text: String? = null
        private set

    @StringRes
    internal var textRes: Int? = null
        private set

    @ColorRes
    internal var drawableTintRes: Int? = null
        private set

    constructor(text: String) : this() {
        this.drawableRes = drawableRes
        this.text = text
    }

    constructor(textRes: Int) : this() {
        this.drawable = drawable
        this.textRes = textRes
    }

    constructor(@StringRes textRes: Int, drawable: Drawable) : this() {
        this.drawable = drawable
        this.textRes = textRes
    }

    constructor(text: String, drawable: Drawable) : this() {
        this.drawable = drawable
        this.text = text
    }

    constructor(text: String, @DrawableRes drawableRes: Int) : this() {
        this.drawableRes = drawableRes
        this.text = text
    }

    constructor(@StringRes textRes: Int, @DrawableRes drawableRes: Int) : this() {
        this.drawableRes = drawableRes
        this.textRes = textRes
    }

    /** Set tint of drawable. */
    fun drawableTintRes(@ColorRes drawableTintRes: Int): SpinnerOption {
        this.drawableTintRes = drawableTintRes
        return this
    }
}