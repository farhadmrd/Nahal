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

package com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.nahal.developer.family.nahal.R
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.utils.*

/** Container with a handle drawable. */
internal class SheetsHandle
@JvmOverloads constructor(
    val ctx: Context,
    attrs: AttributeSet? = null
) : LinearLayoutCompat(ctx, attrs) {

    companion object {
        private const val DEFAULT_CORNER_FAMILY = CornerFamily.ROUNDED
        private const val DEFAULT_CORNER_RADIUS = 8f
    }

    init {
        orientation = VERTICAL
        setPadding(8.toDp(), 8.toDp(), 8.toDp(), 8.toDp())

        val cornerFamily = intOfAttrs(
            ctx,
            R.attr.sheetsHandleCornerFamily
        ) ?: DEFAULT_CORNER_FAMILY

        val cornerRadius = dimensionOfAttrs(
            ctx,
            R.attr.sheetsHandleCornerRadius
        ) ?: DEFAULT_CORNER_RADIUS.toDp()

        val fillColor = colorOfAttr(ctx, R.attr.sheetsHandleFillColor).takeUnlessNotResolved()
                ?: ContextCompat.getColor(ctx, R.color.sheetsDividerColor)

        val borderColor =
            colorOfAttr(ctx, R.attr.sheetsHandleBorderColor).takeUnlessNotResolved()
                ?: ContextCompat.getColor(ctx, R.color.sheetsDividerColor)

        val borderWidth = dimensionOfAttrs(ctx, R.attr.sheetsHandleBorderWidth)

        val shapeModel = ShapeAppearanceModel().toBuilder().apply {
            setAllCorners(cornerFamily, cornerRadius)
        }.build()

        val drawable = MaterialShapeDrawable(shapeModel).apply {
            this.fillColor = ColorStateList.valueOf(fillColor)
            borderWidth?.let { setStroke(it, borderColor) }
        }

        val handleWidth = dimensionOfAttrs(ctx, R.attr.sheetsHandleWidth) ?: 28.getDp()
        val handleHeight = dimensionOfAttrs(ctx, R.attr.sheetsHandleHeight) ?: 4.getDp()

        addView(ImageView(ctx).apply {
            layoutParams = LayoutParams(
                handleWidth.toInt(),
                handleHeight.toInt()
            ).apply {
                setMargins(0, 8.toDp(), 0, 8.toDp())
            }
            gravity = Gravity.CENTER
            setImageDrawable(drawable)
        })
    }
}
