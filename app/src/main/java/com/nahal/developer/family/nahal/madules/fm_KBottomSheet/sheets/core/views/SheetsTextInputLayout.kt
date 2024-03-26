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
import android.content.res.ColorStateList
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout
import com.nahal.developer.family.nahal.R
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.utils.getPrimaryColor
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.utils.toDp


/** Custom TextInputLayout. */
class SheetsTextInputLayout
@JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    styleAttrs: Int = com.google.android.material.R.attr.textInputStyle,
) : TextInputLayout(ctx, attrs, styleAttrs) {

    companion object {
        private const val DEFAULT_CORNER_RADIUS = 8f
    }

    init {

        val a = ctx.obtainStyledAttributes(attrs, R.styleable.SheetsTextInputLayout, 0, 0)

        val cornerRadius = a.getDimension(
            R.styleable.SheetsTextInputLayout_sheetsTextInputLayoutCornerRadius,
            DEFAULT_CORNER_RADIUS.toDp()
        )

        val topLeftCornerRadius = a.getDimension(
            R.styleable.SheetsTextInputLayout_sheetsTextInputLayoutTopLeftCornerRadius,
            cornerRadius
        )

        val topRightCornerRadius = a.getDimension(
            R.styleable.SheetsTextInputLayout_sheetsTextInputLayoutTopRightCornerRadius,
            cornerRadius
        )

        val bottomLeftRadius = a.getDimension(
            R.styleable.SheetsTextInputLayout_sheetsTextInputLayoutBottomLeftCornerRadius,
            cornerRadius
        )

        val bottomRightRadius = a.getDimension(
            R.styleable.SheetsTextInputLayout_sheetsTextInputLayoutBottomRightCornerRadius,
            cornerRadius
        )

        setBoxCornerRadii(
            topLeftCornerRadius,
            topRightCornerRadius,
            bottomLeftRadius,
            bottomRightRadius
        )

        val primaryColor = getPrimaryColor(ctx)

        val endIconColor = a.getColor(
            R.styleable.SheetsTextInputLayout_sheetsTextInputLayoutEndIconColor,
            0
        )
        endIconColor.takeIf { it != 0 }?.let { setEndIconTintList(ColorStateList.valueOf(it)) }

        val helperTextColor = a.getColor(
            R.styleable.SheetsTextInputLayout_sheetsTextInputLayoutHelperTextColor,
            primaryColor
        )
        setHelperTextColor(ColorStateList.valueOf(helperTextColor))

        val boxFocusedStrokeColor = a.getColor(
            R.styleable.SheetsTextInputLayout_sheetsTextInputLayoutBoxStrokeColor,
            primaryColor
        )

        val states = arrayOf(intArrayOf(android.R.attr.state_enabled))
        val colors = intArrayOf(boxFocusedStrokeColor)
        setBoxStrokeColorStateList(ColorStateList(states, colors))

        val hintColor = a.getColor(
            R.styleable.SheetsTextInputLayout_sheetsTextInputLayoutHintTextColor,
            primaryColor
        )
        defaultHintTextColor = ColorStateList.valueOf(hintColor)

        val boxStrokeErrorColor = a.getColor(
            R.styleable.SheetsTextInputLayout_sheetsTextInputLayoutBoxStrokeErrorColor,
            0
        )
        boxStrokeErrorColor.takeIf { it != 0 }?.let { setBoxStrokeErrorColor(ColorStateList.valueOf(it)) }

        val errorTextColor = a.getColor(
            R.styleable.SheetsTextInputLayout_sheetsTextInputLayoutErrorTextColor,
            0
        )
        errorTextColor.takeIf { it != 0 }?.let {
            setErrorTextColor(ColorStateList.valueOf(it))
        }

        val errorDrawableColor = a.getColor(
            R.styleable.SheetsTextInputLayout_sheetsTextInputLayoutErrorDrawableColor,
            0
        )
        errorDrawableColor.takeIf { it != 0 }?.let {
            setErrorIconTintList(ColorStateList.valueOf(it))
        }


        a.recycle()
    }
}
