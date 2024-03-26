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
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.nahal.developer.family.nahal.R
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.ButtonStyle
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.utils.*

internal typealias ButtonClickListener = () -> Unit

/** Container that contains a button. */
class SheetButtonContainer
@JvmOverloads constructor(
    val ctx: Context,
    attrs: AttributeSet? = null,
) : LinearLayoutCompat(ctx, attrs) {

    companion object {
        private const val DEFAULT_CORNER_FAMILY = CornerFamily.ROUNDED
        private const val DEFAULT_CORNER_RADIUS = 8f
        private const val BUTTON_MIN_WIDTH = 120
        private const val BUTTON_ICON_PADDING = 12
    }

    private var negativeBtn: MaterialButton? = null
    private var positiveBtn: MaterialButton? = null

    init {
        orientation = VERTICAL
    }

    private fun createButton(
        style: ButtonStyle? = null,
        buttonColor: Int? = null,
        btnText: String,
        btnDrawable: Drawable?,
        btnListener: ButtonClickListener,
        negative: Boolean,
        shapeModel: ShapeAppearanceModel.Builder,
    ) {

        val buttonStyleAttr = if (negative) R.attr.sheetsNegativeButtonType
        else R.attr.sheetsPositiveButtonType

        val buttonStyleValue = intOfAttrs(ctx, buttonStyleAttr) ?: ButtonStyle.TEXT.ordinal
        val buttonStyle = style ?: ButtonStyle.values()[buttonStyleValue]

        val primaryColor = colorOfAttrs(
            ctx,
            R.attr.sheetsButtonColor,
            R.attr.sheetsPrimaryColor,
            androidx.appcompat.R.attr.colorPrimary
        )

        val btnWidthLayoutParam =
            intOfAttrs(ctx, R.attr.sheetsButtonWidth) ?: ViewGroup.LayoutParams.WRAP_CONTENT

        gravity = Gravity.CENTER

        val outlinedButtonBorderWidth = dimensionOfAttrs(
            ctx,
            if (negative) R.attr.sheetsNegativeButtonOutlinedButtonBorderWidth else R.attr.sheetsPositiveButtonOutlinedButtonBorderWidth,
            R.attr.sheetsButtonOutlinedButtonBorderWidth
        )

        val outlinedButtonBorderColor = colorOfAttrs(
            ctx,
            if (negative) R.attr.sheetsNegativeButtonOutlinedButtonBorderColor else R.attr.sheetsPositiveButtonOutlinedButtonBorderColor,
            R.attr.sheetsButtonOutlinedButtonBorderColor
        )

        val mainButtonColor = buttonColor ?: colorOfAttrsOrNull(
            ctx,
            if (negative) R.attr.sheetsNegativeButtonColor else R.attr.sheetsPositiveButtonColor,
            R.attr.sheetsButtonColor
        ) ?: primaryColor

        val rippleColor = getHighlightOfColor(mainButtonColor)

        addView(SheetsButton(ctx, null, buttonStyle.styleRes).apply {

            layoutParams =
                ViewGroup.LayoutParams(btnWidthLayoutParam, ViewGroup.LayoutParams.WRAP_CONTENT)

            text = btnText
            btnDrawable?.let { icon = btnDrawable }
            iconGravity = MaterialButton.ICON_GRAVITY_TEXT_START
            iconPadding = BUTTON_ICON_PADDING.toDp()
            iconTint = ColorStateList.valueOf(mainButtonColor)
            minWidth = BUTTON_MIN_WIDTH.toDp()
            minimumWidth = BUTTON_MIN_WIDTH.toDp()

            setOnClickListener { btnListener.invoke() }

            when (buttonStyle) {
                ButtonStyle.TEXT, ButtonStyle.OUTLINED -> {
                    setRippleColor(ColorStateList.valueOf(rippleColor))
                    setTextColor(mainButtonColor)
                }
                ButtonStyle.NORMAL -> {
                    icon?.setColorFilter(currentTextColor, PorterDuff.Mode.SRC_ATOP)
                    setBackgroundColor(mainButtonColor)
                }
            }

            shapeAppearanceModel = shapeModel.apply {
                when (buttonStyle) {
                    ButtonStyle.TEXT -> {
                        strokeWidth =
                            0 /* Set border stroke width to zero to remove the border and simulate a normal TextButton. */
                    }
                    ButtonStyle.OUTLINED -> {
                        outlinedButtonBorderColor.takeUnlessNotResolved()
                            ?.let { strokeColor = ColorStateList.valueOf(it) }
                        outlinedButtonBorderWidth?.let { strokeWidth = it.toInt() }
                    }
                    else -> {
                    }
                }
            }.build()

        }.also { if (negative) negativeBtn = it else positiveBtn = it })
    }

    /** Setup a negative button. */
    fun setupNegativeButton(
        buttonStyle: ButtonStyle?,
        buttonColor: Int?,
        btnText: String,
        btnDrawable: Drawable?,
        btnListener: ButtonClickListener,
    ) {

        val parentFamily = R.attr.sheetsButtonCornerFamily
        val parentRadius = R.attr.sheetsButtonCornerRadius

        val negParentFamily = R.attr.sheetsNegativeButtonCornerFamily
        val negParentRadius = R.attr.sheetsNegativeButtonCornerRadius

        val negBtnBottomLeftFamily = intOfAttrs(
            ctx,
            R.attr.sheetsNegativeButtonBottomLeftCornerFamily,
            negParentFamily,
            parentFamily
        ) ?: DEFAULT_CORNER_FAMILY

        val negBtnBottomRightFamily = intOfAttrs(
            ctx,
            R.attr.sheetsNegativeButtonBottomRightCornerFamily,
            negParentFamily,
            parentFamily
        ) ?: DEFAULT_CORNER_FAMILY

        val negBtnTopLeftFamily = intOfAttrs(
            ctx,
            R.attr.sheetsNegativeButtonTopLeftCornerFamily,
            negParentFamily,
            parentFamily
        ) ?: DEFAULT_CORNER_FAMILY

        val negBtnTopRightFamily = intOfAttrs(
            ctx,
            R.attr.sheetsNegativeButtonTopRightCornerFamily,
            negParentFamily,
            parentFamily
        ) ?: DEFAULT_CORNER_FAMILY

        val negBtnBottomLeftRadius = dimensionOfAttrs(
            ctx,
            R.attr.sheetsNegativeButtonBottomLeftCornerRadius,
            negParentRadius,
            parentRadius
        ) ?: DEFAULT_CORNER_RADIUS

        val negBtnBottomRightRadius = dimensionOfAttrs(
            ctx,
            R.attr.sheetsNegativeButtonBottomRightCornerRadius,
            negParentRadius,
            parentRadius
        ) ?: DEFAULT_CORNER_RADIUS

        val negBtnTopLeftRadius = dimensionOfAttrs(
            ctx,
            R.attr.sheetsNegativeButtonTopLeftCornerRadius,
            negParentRadius,
            parentRadius
        ) ?: DEFAULT_CORNER_RADIUS

        val negBtnTopRightRadius = dimensionOfAttrs(
            ctx,
            R.attr.sheetsNegativeButtonTopRightCornerRadius,
            negParentRadius,
            parentRadius
        ) ?: DEFAULT_CORNER_RADIUS

        val shapeModel = ShapeAppearanceModel().toBuilder().apply {
            setBottomLeftCorner(negBtnBottomLeftFamily, negBtnBottomLeftRadius.toDp())
            setBottomRightCorner(negBtnBottomRightFamily, negBtnBottomRightRadius.toDp())
            setTopLeftCorner(negBtnTopLeftFamily, negBtnTopLeftRadius.toDp())
            setTopRightCorner(negBtnTopRightFamily, negBtnTopRightRadius.toDp())
        }

        createButton(buttonStyle, buttonColor, btnText, btnDrawable, btnListener, true, shapeModel)
    }

    /** Setup a positive button. */
    fun setupPositiveButton(
        buttonStyle: ButtonStyle?,
        buttonColor: Int?,
        btnText: String,
        btnDrawable: Drawable?,
        btnListener: ButtonClickListener,
    ) {

        val parentFamily = R.attr.sheetsButtonCornerFamily
        val parentRadius = R.attr.sheetsButtonCornerRadius

        val posParentFamily = R.attr.sheetsPositiveButtonCornerFamily
        val posParentRadius = R.attr.sheetsPositiveButtonCornerRadius

        val posBtnBottomLeftFamily = intOfAttrs(
            ctx,
            R.attr.sheetsPositiveButtonBottomLeftCornerFamily,
            posParentFamily,
            parentFamily
        ) ?: DEFAULT_CORNER_FAMILY

        val posBtnBottomRightFamily = intOfAttrs(
            ctx,
            R.attr.sheetsPositiveButtonBottomRightCornerFamily,
            posParentFamily,
            parentFamily
        ) ?: DEFAULT_CORNER_FAMILY

        val posBtnTopLeftFamily = intOfAttrs(
            ctx,
            R.attr.sheetsPositiveButtonTopLeftCornerFamily,
            posParentFamily,
            parentFamily
        ) ?: DEFAULT_CORNER_FAMILY

        val posBtnTopRightFamily = intOfAttrs(
            ctx,
            R.attr.sheetsPositiveButtonTopRightCornerFamily,
            posParentFamily,
            parentFamily
        ) ?: DEFAULT_CORNER_FAMILY

        val posBtnBottomLeftRadius = dimensionOfAttrs(
            ctx,
            R.attr.sheetsPositiveButtonBottomLeftCornerRadius,
            posParentRadius,
            parentRadius
        ) ?: DEFAULT_CORNER_RADIUS

        val posBtnBottomRightRadius = dimensionOfAttrs(
            ctx,
            R.attr.sheetsPositiveButtonBottomRightCornerRadius,
            posParentRadius,
            parentRadius
        ) ?: DEFAULT_CORNER_RADIUS

        val posBtnTopLeftRadius = dimensionOfAttrs(
            ctx,
            R.attr.sheetsPositiveButtonTopLeftCornerRadius,
            posParentRadius,
            parentRadius
        ) ?: DEFAULT_CORNER_RADIUS

        val posBtnTopRightRadius = dimensionOfAttrs(
            ctx,
            R.attr.sheetsPositiveButtonTopRightCornerRadius,
            posParentRadius,
            parentRadius
        ) ?: DEFAULT_CORNER_RADIUS

        val shapeModel = ShapeAppearanceModel().toBuilder().apply {
            setBottomLeftCorner(posBtnBottomLeftFamily, posBtnBottomLeftRadius.toDp())
            setBottomRightCorner(posBtnBottomRightFamily, posBtnBottomRightRadius.toDp())
            setTopLeftCorner(posBtnTopLeftFamily, posBtnTopLeftRadius.toDp())
            setTopRightCorner(posBtnTopRightFamily, posBtnTopRightRadius.toDp())
        }

        createButton(buttonStyle, buttonColor, btnText, btnDrawable, btnListener, false, shapeModel)
    }

    /** Make positive button clickable. */
    fun positiveButtonClickable(isClickable: Boolean) {
        this.positiveBtn?.isClickable = isClickable
    }

    /** Set positive button listener. */
    fun negativeButtonListener(btnListener: ButtonClickListener) {
        this.negativeBtn?.setOnClickListener { btnListener.invoke() }
    }

    /** Set positive button listener. */
    fun positiveButtonListener(btnListener: ButtonClickListener) {
        this.positiveBtn?.setOnClickListener { btnListener.invoke() }
    }
}
