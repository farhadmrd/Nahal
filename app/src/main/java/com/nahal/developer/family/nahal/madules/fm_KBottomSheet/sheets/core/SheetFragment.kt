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

package com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.utils.*


/**
 * This class is the base of all types of sheets.
 * You can implement this class in your own and build your
 * own custom sheet with the already existing features which the base class offers.
 */
abstract class SheetFragment : DialogFragment() {

    open val dialogTag = "SheetFragment"

    companion object {
        const val DEFAULT_CORNER_RADIUS = 16f
        const val DEFAULT_CORNER_FAMILY = CornerFamily.ROUNDED
    }

    open lateinit var windowContext: Context

    protected var sheetStyle: SheetStyle = SheetStyle.BOTTOM_SHEET
    private var sheetTheme = Theme.BOTTOM_SHEET_DAY
    private var draggable = true
    private var behavior = BottomSheetBehavior.STATE_EXPANDED
    private var peekHeight = 0
    protected var width: Int? = null
    protected var cornerFamily: Int? = null
    protected var cornerRadiusDp: Float? = null
    private var borderStrokeWidthDp: Float? = null
    private var borderStrokeColor: Int? = null
    private var navigationBarColor: Int? = null

    /** Set sheet style. */
    fun style(style: SheetStyle) {
        this.sheetStyle = style
    }

    /** Override style to switch between sheet & dialog style. */
    override fun setStyle(style: Int, theme: Int) {
        sheetTheme = Theme.inferTheme(requireContext(), sheetStyle)
        super.setStyle(style, sheetTheme.styleRes)
    }

    /** Set if sheet is cancelable outside. */
    fun cancelableOutside(cancelable: Boolean) {
        this.isCancelable = cancelable
    }

    /** Set the color of the navigation bar. */
    fun navigationBarColor(@ColorInt color: Int) {
        this.navigationBarColor = color
    }

    /** Set the color of the navigation bar. */
    fun navigationBarColorRes(@ColorRes colorRes: Int) {
        this.navigationBarColor = ContextCompat.getColor(windowContext, colorRes)
    }
    
    /** Set if bottom-sheet is draggable. Only works with [SheetStyle.BOTTOM_SHEET]. */
    fun draggable(draggable: Boolean) {
        this.draggable = draggable
    }

    /** Set the [BottomSheetBehavior] state. */
    fun behavior(@BottomSheetBehavior.State behavior: Int) {
        this.behavior = behavior
    }

    /** Set the peek height. */
    fun peekHeight(peekHeight: Int) {
        this.peekHeight = peekHeight
    }

    /**
     * Set the [CornerFamily].
     * Overrides the style default.
     */
    fun cornerFamily(@CornerFamily cornerFamily: Int) {
        this.cornerFamily = cornerFamily
    }

    /**
     * Set the corner radius in dp.
     * Overrides the style default.
     */
    fun cornerRadius(cornerRadiusInDp: Float) {
        this.cornerRadiusDp = cornerRadiusInDp
    }

    /**
     * Set the corner radius in dp.
     * Overrides the style default.
     */
    fun cornerRadius(@DimenRes cornerRadiusInDpRes: Int) {
        this.cornerRadiusDp = windowContext.resources.getDimension(cornerRadiusInDpRes)
    }

    /** Set the border stroke width */
    fun borderWidth(strokeWidthDp: Float) {
        this.borderStrokeWidthDp = strokeWidthDp
    }

    /** Set the border stroke width */
    fun borderWidth(@DimenRes strokeWidthDpRes: Int) {
        this.borderStrokeWidthDp = windowContext.resources.getDimension(strokeWidthDpRes)
    }

    /**
     * Set the border stroke color around the sheet.
     * If no color is set, the primary color is used.
     */
    fun borderColor(strokeColor: Int) {
        this.borderStrokeColor = strokeColor
    }

    /** Override theme to allow auto switch between day & night design. */
    override fun getTheme(): Int {
        sheetTheme = Theme.inferTheme(requireContext(), sheetStyle)
        return sheetTheme.styleRes
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSheetBehavior(view)
        setupWindow(view)
        setupSheetBackground(view)
    }

    private fun setupSheetBehavior(view: View) {

        if (sheetStyle == SheetStyle.DIALOG) {
            // We don't need a behavior for the dialog
            return
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val dialog = dialog as? BottomSheetDialog? ?: return
                val dialogBehavior = dialog.behavior
                dialogBehavior.state = behavior
                dialogBehavior.isDraggable = draggable
                dialogBehavior.peekHeight = peekHeight
                dialogBehavior.addBottomSheetCallback(object :
                    BottomSheetBehavior.BottomSheetCallback() {
                    override fun onSlide(bottomSheet: View, dY: Float) {
//                         to do Make button layout stick to the bottom through translationY property.
                    }

                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                            dismiss()
                        }
                    }
                })
            }
        })
    }

    private fun setupWindow(view: View) {
        val navBarColor = navigationBarColor ?: getSheetNavigationBarColor(windowContext)
        navBarColor?.let { dialog?.window?.navigationBarColor = it }
    }

    @CornerFamily
    fun getActualCornerFamily(): Int =
        this.cornerFamily
            ?: getCornerFamily(requireContext())
            ?: DEFAULT_CORNER_FAMILY

    fun getActualCornerRadius(): Float =
        this.cornerRadiusDp?.toDp()
            ?: getCornerRadius(requireContext())
            ?: DEFAULT_CORNER_RADIUS.toDp()

    private fun setupSheetBackground(view: View) {

        // Remove dialog background
        if (sheetStyle == SheetStyle.DIALOG) {
            dialog?.window?.decorView?.setBackgroundColor(Color.TRANSPARENT)
        }

        val cornerFamily = getActualCornerFamily()
        val cornerRadius = getActualCornerRadius()

        val model = ShapeAppearanceModel().toBuilder().apply {
            when (sheetStyle) {
                SheetStyle.BOTTOM_SHEET -> {
                    setTopRightCorner(cornerFamily, cornerRadius)
                    setTopLeftCorner(cornerFamily, cornerRadius)
                }
                else -> setAllCorners(cornerFamily, cornerRadius)
            }
        }.build()

        val shape = MaterialShapeDrawable(model).apply {

            borderStrokeWidthDp?.let { width ->
                setStroke(width.toDp(), borderStrokeColor ?: getPrimaryColor(requireContext()))
                setPadding(width.getDp(), width.getDp(), width.getDp(), width.getDp())
            }

            val backgroundColor = getBottomSheetBackgroundColor(requireContext(),
                sheetTheme.styleRes)
            fillColor = ColorStateList.valueOf(backgroundColor)
        }

        view.background = shape
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return when (sheetStyle) {
            SheetStyle.BOTTOM_SHEET -> BottomSheetDialog(requireContext(), theme)
            else -> Dialog(requireContext(), theme)
        }
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        when (sheetStyle) {

            SheetStyle.BOTTOM_SHEET -> {

                // If the dialog is an AppCompatDialog, we'll handle it
                val acd = dialog as AppCompatDialog
                when (style) {
                    STYLE_NO_INPUT -> {
                        dialog.getWindow()?.addFlags(
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        )
                        acd.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
                    }
                    STYLE_NO_FRAME, STYLE_NO_TITLE -> acd.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
                }
            }

            else -> {
                when (style) {
                    STYLE_NO_INPUT -> {
                        val window = dialog.window
                        window?.addFlags(
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                    or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        )
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    }
                    STYLE_NO_FRAME, STYLE_NO_TITLE -> dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        fixDecoderViewPadding()
    }

    /*
        On Android 11 the horizontal padding is zero, therefore we add a default 16dp horizontal padding.
        https://github.com/maxkeppeler/sheets/issues/77
     */
    private fun fixDecoderViewPadding() {
        if (sheetStyle == SheetStyle.DIALOG) {
            dialog?.window?.decorView?.apply {
                val padding = paddingStart
                if (padding == 0) setPadding(16.toDp(), 0, 16.toDp(), 0)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (sheetStyle == SheetStyle.DIALOG) {
            dialog?.window?.setLayout(
                width ?: ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        } else {
            width?.let { width ->
                dialog?.window?.setLayout(width,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            }
        }
    }

    /** Show the sheet. */
    fun show() {
        windowContext.let { ctx ->
            when (ctx) {
                is FragmentActivity -> show(ctx.supportFragmentManager, dialogTag)
                is AppCompatActivity -> show(ctx.supportFragmentManager, dialogTag)
                is Fragment -> show(ctx.childFragmentManager, dialogTag)
                is PreferenceFragmentCompat -> show(ctx.childFragmentManager, dialogTag)
                else -> throw IllegalStateException("Context ($windowContext) has no window attached.")
            }
        }
    }
}