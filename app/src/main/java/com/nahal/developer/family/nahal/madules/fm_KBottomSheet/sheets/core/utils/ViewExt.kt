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

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.annotation.RestrictTo
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel


/**
 * Clip the top corners of a ImageView. Works with any [CornerFamily].
 * Replaces the current background drawable with a custom drawable with clipped corners.
 * The previously set background color of the ImageView is automatically applied to the new background drawable.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun AppCompatImageView.clipTopCorners(@CornerFamily cornerFamily: Int, cornerRadius: Float) {

    val colorDrawable = background as ColorDrawable?
    val backgroundColor = colorDrawable?.color ?: Color.TRANSPARENT

    val shapeModel = ShapeAppearanceModel().toBuilder().apply {
        setTopRightCorner(cornerFamily, cornerRadius)
        setTopLeftCorner(cornerFamily, cornerRadius)
    }.build()

    val shapeDrawable = MaterialShapeDrawable(shapeModel).apply {
        fillColor = ColorStateList.valueOf(backgroundColor)
    }

    background = shapeDrawable
    clipToOutline = true
}

