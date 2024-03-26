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

package com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.color

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.nahal.developer.family.nahal.R
import com.nahal.developer.family.nahal.databinding.SheetsColorBinding
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.Sheet
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.layoutmanagers.CustomGridLayoutManager
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.utils.*
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.views.SheetsContent


/** Listener to be invoked when color is selected. */
typealias ColorListener = (Int) -> Unit

/**
 * The [ColorSheet] lets you pick a color.
 */
class ColorSheet : Sheet(), SeekBar.OnSeekBarChangeListener {

    override val dialogTag = "ColorSheet"

    private companion object {
        private const val ARG_MAX_VALUE = 255
    }

    private lateinit var binding: SheetsColorBinding

    private val argbSeekBars = mutableListOf<SeekBar>()
    private val argbLabelTexts = mutableListOf<String>()
    private val argbLabels = mutableListOf<SheetsContent>()
    private val argbValues = mutableListOf<SheetsContent>()

    private var colorAdapter: ColorAdapter? = null

    @ColorInt
    private var colors: MutableList<Int> = mutableListOf()

    private var colorView = ColorView.TEMPLATE
    private var switchColorView = true

    private val touchCustomColorView
        get() = switchColorView || colorView == ColorView.CUSTOM

    @ColorInt
    private var defaultColor: Int? = null

    @ColorInt
    private var selectedColor: Int = Color.BLACK

    private val iconColor by lazy { getIconColor(requireContext()) }
    private val primaryColor by lazy { getPrimaryColor(requireContext()) }
    private val primaryDarkColor by lazy { getPrimaryColor(requireContext()) }
    private val highlightColor by lazy { getHighlightColor(requireContext()) }

    private var listener: ColorListener? = null
    private var disableAlpha: Boolean = false

    private val saveAllowed: Boolean
        get() = selectedColor != defaultColor

    /** Set default [ColorView]. */
    fun defaultView(colorView: ColorView) {
        this.colorView = colorView
    }

    /** Disable to switch between [ColorView]. */
    fun disableSwitchColorView() {
        this.switchColorView = false
    }

    /** Set default color. */
    fun defaultColorRes(@ColorRes color: Int) {
        this.defaultColor = ContextCompat.getColor(windowContext, color)
    }

    /** Set colors. */
    fun colorsRes(@ColorRes vararg color: Int) {
        this.colors = color.map { ContextCompat.getColor(windowContext, it) }.toMutableList()
    }

    /** Set colors. */
    fun colorsRes(@ColorRes colors: List<Int>) {
        this.colors = colors.map { ContextCompat.getColor(windowContext, it) }.toMutableList()
    }

    /** Set default color. */
    fun defaultColorInt(@ColorInt color: Int) {
        this.defaultColor = color
    }

    /** Set colors. */
    fun colorsInt(@ColorInt vararg color: Int) {
        this.colors = color.toMutableList()
    }

    /** Set colors. */
    fun colorsInt(@ColorInt colors: List<Int>) {
        this.colors = colors.toMutableList()
    }

    /** Set default color. */
    fun defaultColorString(color: String) {
        this.defaultColor = Color.parseColor(color)
    }

    /** Set colors. */
    fun colorsString(vararg color: String) {
        this.colors = color.map { Color.parseColor(it) }.toMutableList()
    }

    /** Set colors. */
    fun colorsString(colors: List<String>) {
        this.colors = colors.map { Color.parseColor(it) }.toMutableList()
    }

    /** Disable alpha. */
    fun disableAlpha() {
        this.disableAlpha = true
    }

    /**
     * Set the [ColorListener].
     *
     * @param listener Listener that is invoked with the selected color when the positive button is clicked.
     */
    fun onPositive(listener: ColorListener) {
        this.listener = listener
    }

    /**
     * Set the text of the positive button and set the [ColorListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param listener Listener that is invoked with the selected color when the positive button is clicked.
     */
    fun onPositive(@StringRes positiveRes: Int, listener: ColorListener? = null) {
        this.positiveText = windowContext.getString(positiveRes)
        this.listener = listener
    }

    /**
     *  Set the text of the positive button and set the [ColorListener].
     *
     * @param positiveText The text for the positive button.
     * @param listener Listener that is invoked with the selected color when the positive button is clicked.
     */
    fun onPositive(positiveText: String, listener: ColorListener? = null) {
        this.positiveText = positiveText
        this.listener = listener
    }

    /**
     * Set the text of the positive button and set the [ColorListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param drawableRes The drawable resource for the button icon.
     * @param listener Listener that is invoked with the selected color when the positive button is clicked.
     */
    fun onPositive(
        @StringRes positiveRes: Int,
        @DrawableRes drawableRes: Int,
        listener: ColorListener? = null
    ) {
        this.positiveText = windowContext.getString(positiveRes)
        this.positiveButtonDrawable = ContextCompat.getDrawable(windowContext, drawableRes)
        this.listener = listener
    }

    /**
     *  Set the text of the positive button and set the [ColorListener].
     *
     * @param positiveText The text for the positive button.
     * @param drawableRes The drawable resource for the button icon.
     * @param listener Listener that is invoked with the selected color when the positive button is clicked.
     */
    fun onPositive(
        positiveText: String,
        @DrawableRes drawableRes: Int,
        listener: ColorListener? = null
    ) {
        this.positiveText = positiveText
        this.positiveButtonDrawable = ContextCompat.getDrawable(windowContext, drawableRes)
        this.listener = listener
    }

    /**
     * Set the text of the positive button and set the [ColorListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param drawable The drawable for the button icon.
     * @param listener Listener that is invoked with the selected color when the positive button is clicked.
     */
    fun onPositive(
        @StringRes positiveRes: Int,
        drawable: Drawable,
        listener: ColorListener? = null
    ) {
        this.positiveText = windowContext.getString(positiveRes)
        this.positiveButtonDrawable = drawable
        this.listener = listener
    }

    /**
     *  Set the text of the positive button and set the [ColorListener].
     *
     * @param positiveText The text for the positive button.
     * @param drawable The drawable for the button icon.
     * @param listener Listener that is invoked with the selected color when the positive button is clicked.
     */
    fun onPositive(
        positiveText: String,
        drawable: Drawable,
        listener: ColorListener? = null
    ) {
        this.positiveText = positiveText
        this.positiveButtonDrawable = drawable
        this.listener = listener
    }

    override fun onCreateLayoutView(): View =
        SheetsColorBinding.inflate(LayoutInflater.from(activity)).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonPositiveListener(::save)
        displayToolbarTypeButton(switchColorView)

        if (colors.isEmpty()) {
            this.colors = getDefaultColorPalette().map {
                ContextCompat.getColor(requireContext(), it)
            }.toMutableList()
        }

        when {
            switchColorView -> {
                setupCustomView()
                setupTemplatesView()
            }
            colorView == ColorView.CUSTOM -> {
                setupCustomView()
            }
            colorView == ColorView.TEMPLATE -> {
                setupTemplatesView()
            }
        }

        setColorView()

        if (switchColorView) {
            setToolbarTypeButtonListener {
                colorView = ColorView.TEMPLATE.takeUnless { it == colorView } ?: ColorView.CUSTOM
                setColorView()
            }
        }
    }

    private fun validate(init: Boolean = false) {
        displayButtonPositive(saveAllowed, !init)
    }

    private fun save() {
        listener?.invoke(selectedColor)
        dismiss()
    }

    private fun setupTemplatesView() {
        if (colors.isNotEmpty()) {
            binding.colorTemplatesView.layoutManager =
                CustomGridLayoutManager(requireContext(), 6, true)
            colorAdapter = ColorAdapter(colors, selectedColor) { color ->
                selectedColor = color
                updateColor()
                validate()
            }
            binding.colorTemplatesView.adapter = colorAdapter
        }
    }

    private fun setupCustomView() {

        with(binding.custom) {

            argbSeekBars.apply {
                add(alphaSeekBar)
                add(redSeekBar)
                add(greenSeekBar)
                add(blueSeekBar)
            }

            argbLabels.apply {
                add(alphaLabel)
                add(redLabel)
                add(greenLabel)
                add(blueLabel)
            }

            argbValues.apply {
                add(alphaValue)
                add(redValue)
                add(greenValue)
                add(blueValue)
            }

            argbLabelTexts.apply {
                add(getString(R.string.sheets_color_picker_alpha))
                add(getString(R.string.sheets_color_picker_red))
                add(getString(R.string.sheets_color_picker_green))
                add(getString(R.string.sheets_color_picker_blue))
            }

            argbSeekBars.forEach { seekBar ->
                seekBar.max = ARG_MAX_VALUE
                seekBar.progressBackgroundTintList = ColorStateList.valueOf(iconColor)
                seekBar.progressTintList = ColorStateList.valueOf(primaryColor)
                seekBar.thumbTintList = ColorStateList.valueOf(primaryColor)
                seekBar.setOnSeekBarChangeListener(this@ColorSheet)
            }

            btnCopy.setColorFilter(iconColor)
            btnPaste.setColorFilter(iconColor)

            if (disableAlpha) {
                argbLabels.first().visibility = View.GONE
                argbSeekBars.first().visibility = View.GONE
                argbValues.first().visibility = View.GONE
            }

            argbLabelTexts.forEachIndexed { i, s -> argbLabels[i].text = s }
            hexValue.text = getHex(defaultColor ?: selectedColor)
            updateColor()

            btnCopy.setOnClickListener { onCopy() }
            btnPaste.setOnClickListener { onPaste() }
        }
    }

    private fun onCopy() {
        val hex = getHex(selectedColor)
        copyIntoClipboard(requireContext(), "color", hex)
    }

    private fun onPaste() {
        val pastedText = pasteFromClipboard(requireContext())
        pastedText?.let {
            try {
                selectedColor = Color.parseColor(it)
                binding.custom.hexValue.text = it
                updateColor()
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.sheets_clipboard_paste_invalid_color_code),
                    Toast.LENGTH_LONG
                ).show()
            }
        } ?: kotlin.run {
            // Nothing to paste from clipboard.
            Toast.makeText(
                requireContext(),
                getString(R.string.sheets_clipboard_paste_invalid_empty),
                Toast.LENGTH_LONG
            ).show()
        }
    }


    private fun setColorView() {

        val templateView = colorView == ColorView.TEMPLATE

        with(binding) {
            colorTemplatesView.visibility = if (templateView) View.VISIBLE else View.GONE
            custom.root.visibility = if (templateView) View.INVISIBLE else View.VISIBLE
            if (switchColorView) {
                setToolbarTypeButtonDrawable(if (templateView) R.drawable.sheets_ic_color_picker else R.drawable.sheets_ic_color_palette)
            }
        }
    }

    private fun getHex(color: Int): String =
        String.format("#%08X", (0xFFFFFFFF and color.toLong()))

    private fun updateColor(calculate: Boolean = false) {

        with(binding.custom) {

            val colorParsed = Color.parseColor(getHex(selectedColor))
            val a = if (calculate) argbSeekBars[0].progress else Color.alpha(colorParsed)
            val r = if (calculate) argbSeekBars[1].progress else Color.red(colorParsed)
            val g = if (calculate) argbSeekBars[2].progress else Color.green(colorParsed)
            val b = if (calculate) argbSeekBars[3].progress else Color.blue(colorParsed)

            if (calculate) {
                selectedColor = Color.argb(a, r, g, b)
            } else {
                if (touchCustomColorView) {
                    argbSeekBars[0].progress = a
                    argbSeekBars[1].progress = r
                    argbSeekBars[2].progress = g
                    argbSeekBars[3].progress = b
                }
            }

            if (touchCustomColorView) {
                val background = customColorView.background as RippleDrawable
                (background.getDrawable(1) as GradientDrawable).setColor(selectedColor)

                alphaValue.text = a.toString().padStart(3)
                redValue.text = r.toString().padStart(3)
                greenValue.text = g.toString().padStart(3)
                blueValue.text = b.toString().padStart(3)

                hexValue.text = getHex(selectedColor)
            }
        }

        validate(true)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        updateColor(true)
        if (fromUser) {
            colorAdapter?.removeSelection()
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

    override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit

    /** Build [ColorSheet] and show it later. */
    fun build(ctx: Context, width: Int? = null, func: ColorSheet.() -> Unit): ColorSheet {
        this.windowContext = ctx
        this.width = width
        this.func()
        return this
    }

    /** Build and show [ColorSheet] directly. */
    fun show(ctx: Context, width: Int? = null, func: ColorSheet.() -> Unit): ColorSheet {
        this.windowContext = ctx
        this.width = width
        this.func()
        this.show()
        return this
    }
}

