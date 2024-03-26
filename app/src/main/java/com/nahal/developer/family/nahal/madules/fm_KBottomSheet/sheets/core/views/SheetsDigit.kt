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

package com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.nahal.developer.family.nahal.R
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.utils.colorOfAttrs

/** Custom TextView used for the digits of the [SheetNumericalInput]. */
class SheetsDigit
@JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    styleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(ctx, attrs, styleAttr) {

    init {

        val a = ctx.obtainStyledAttributes(attrs, R.styleable.SheetsDigit, styleAttr, 0)

        val height = a.getDimensionPixelSize(R.styleable.SheetsDigit_sheetsDigitLineHeight, 0)
        height.takeIf { it != 0 }?.let { lineHeight = height }

        val colorDefault = colorOfAttrs(ctx, R.attr.sheetsContentColor, android.R.attr.textColorPrimary)
        val color = a.getColor(R.styleable.SheetsDigit_sheetsDigitColor, colorDefault)
        setTextColor(color)

        val fontResId = a.getResourceId(R.styleable.SheetsDigit_sheetsDigitFont, 0)
        fontResId.takeIf { it != 0 }?.let { typeface = ResourcesCompat.getFont(ctx, it) }

        val spacing =
            a.getFloat(R.styleable.SheetsDigit_sheetsDigitLetterSpacing, 0f)
        spacing.takeIf { it != 0f }?.let { letterSpacing = it }

        a.recycle()
    }
}
