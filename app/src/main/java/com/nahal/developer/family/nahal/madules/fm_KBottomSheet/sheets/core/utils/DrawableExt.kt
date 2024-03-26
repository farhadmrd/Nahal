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

import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.Px
import androidx.annotation.RestrictTo
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.core.graphics.component3
import androidx.core.graphics.component4
import java.text.SimpleDateFormat
import java.util.*

/**
 * Taken from android ktx library [androidx.core.graphics.drawable|.
 *
 * Return a [Bitmap] representation of this [Drawable].
 *
 * If this instance is a [BitmapDrawable] and the [width], [height], and [config] match, the
 * underlying [Bitmap] instance will be returned directly. If any of those three properties differ
 * then a new [Bitmap] is created. For all other [Drawable] types, a new [Bitmap] is created.
 *
 * @param width Width of the desired bitmap. Defaults to [Drawable.getIntrinsicWidth].
 * @param height Height of the desired bitmap. Defaults to [Drawable.getIntrinsicHeight].
 * @param config Bitmap config of the desired bitmap. Null attempts to use the native config, if
 * any. Defaults to [Config.ARGB_8888] otherwise.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun Drawable.toBitmap(
    @Px width: Int = intrinsicWidth,
    @Px height: Int = intrinsicHeight,
    config: Config? = null
): Bitmap {
    if (this is BitmapDrawable) {
        if (config == null || bitmap.config == config) {
            // Fast-path to return original. Bitmap.createScaledBitmap will do this check, but it
            // involves allocation and two jumps into native code so we perform the check ourselves.
            if (width == intrinsicWidth && height == intrinsicHeight) {
                return bitmap
            }
            return Bitmap.createScaledBitmap(bitmap, width, height, true)
        }
    }

    val (oldLeft, oldTop, oldRight, oldBottom) = bounds

    val bitmap = Bitmap.createBitmap(width, height, config ?: Config.ARGB_8888)
    setBounds(0, 0, width, height)
    draw(Canvas(bitmap))

    setBounds(oldLeft, oldTop, oldRight, oldBottom)
    return bitmap
}