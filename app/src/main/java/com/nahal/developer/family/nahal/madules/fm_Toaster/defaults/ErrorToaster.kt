package com.nahal.developer.family.nahal.madules.fm_Toaster.defaults
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
import android.content.Context
import com.nahal.developer.family.nahal.R
import com.nahal.developer.family.nahal.madules.fm_Toaster.Toaster
import com.nahal.developer.family.nahal.madules.fm_Toaster.utils.Colors

internal object ErrorToaster : DefaultToaster {
    override fun create(context: Context, message: CharSequence, duration: Int): Toaster {
        return Toaster.Config(
            message = message,
            leftDrawableRes = R.drawable.ic_baseline_warning_24,
            leftDrawableTint = Colors.ERROR,
            stripTint = Colors.ERROR,
            duration = duration,
        ).make(context)
    }
}