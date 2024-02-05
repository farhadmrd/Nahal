package com.nahal.developer.family.nahal.madules.fm_Toaster

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes


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

class ToasterBuilderKtx internal constructor(private val context: Context) {

    @DrawableRes
    var leftDrawableRes: Int = DEFAULT_VALUE

    @ColorRes
    var leftDrawableTint: Int = DEFAULT_VALUE

    @ColorRes
    var stripTint: Int = DEFAULT_VALUE

    var message: CharSequence = ""
    var duration: Int = Toaster.LENGTH_SHORT

    internal fun prepare(): Toaster {
        return Toaster.Config(
            message = message,
            duration = duration,
            leftDrawableRes = leftDrawableRes.takeIf { it != DEFAULT_VALUE },
            leftDrawableTint = leftDrawableTint.takeIf { it != DEFAULT_VALUE },
            stripTint = stripTint.takeIf { it != DEFAULT_VALUE },
        ).make(context)
    }

    companion object {
        private const val DEFAULT_VALUE = -1
    }
}
