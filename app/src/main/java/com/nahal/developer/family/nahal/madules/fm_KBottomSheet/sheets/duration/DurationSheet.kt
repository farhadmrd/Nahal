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

package com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.duration

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.nahal.developer.family.nahal.databinding.SheetsDurationBinding
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.Sheet

/** Listener which returns the selected duration time in milliseconds. */
typealias DurationTimeListener = (timeInSec: Long) -> Unit

/**
 * The [DurationSheet] lets you pick a duration time in a specific format.
 */
class DurationSheet : Sheet() {

    override val dialogTag = "DurationSheet"

    private lateinit var binding: SheetsDurationBinding
    private lateinit var selector: TimeSelector

    private var listener: DurationTimeListener? = null
    private var format: DurationTimeFormat = DurationTimeFormat.MM_SS
    private var minTime: Long = Long.MIN_VALUE
    private var maxTime: Long = Long.MAX_VALUE
    private var currentTime: Long? = null
    private var saveAllowed = false

    /** Set the time format. */
    fun format(format: DurationTimeFormat) {
        this.format = format
    }

    /** Set current time in seconds. */
    fun currentTime(currentTimeInSec: Long) {
        this.currentTime = currentTimeInSec
    }

    /** Set min time in seconds. */
    fun minTime(minTimeInSec: Long) {
        this.minTime = minTimeInSec
    }

    /** Set max time in seconds. */
    fun maxTime(maxTimeInSec: Long) {
        this.maxTime = maxTimeInSec
    }

    /**
     * Set the [DurationTimeListener].
     *
     * @param listener Listener that is invoked with the duration time when the positive button is clicked.
     */
    fun onPositive(listener: DurationTimeListener) {
        this.listener = listener
    }

    /**
     * Set the text of the positive button and set the [DurationTimeListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param listener Listener that is invoked with the duration time when the positive button is clicked.
     */
    fun onPositive(@StringRes positiveRes: Int, listener: DurationTimeListener? = null) {
        this.positiveText = windowContext.getString(positiveRes)
        this.listener = listener
    }

    /**
     *  Set the text of the positive button and set the [DurationTimeListener].
     *
     * @param positiveText The text for the positive button.
     * @param listener Listener that is invoked with the duration time when the positive button is clicked.
     */
    fun onPositive(positiveText: String, listener: DurationTimeListener? = null) {
        this.positiveText = positiveText
        this.listener = listener
    }

    /**
     * Set the text and icon of the positive button and set the [DurationTimeListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param drawableRes The drawable resource for the button icon.
     * @param listener Listener that is invoked with the duration time when the positive button is clicked.
     */
    fun onPositive(
        @StringRes positiveRes: Int,
        @DrawableRes drawableRes: Int,
        listener: DurationTimeListener? = null
    ) {
        this.positiveText = windowContext.getString(positiveRes)
        this.positiveButtonDrawable = ContextCompat.getDrawable(windowContext, drawableRes)
        this.listener = listener
    }

    /**
     *  Set the text and icon of the positive button and set the [DurationTimeListener].
     *
     * @param positiveText The text for the positive button.
     * @param drawableRes The drawable resource for the button icon.
     * @param listener Listener that is invoked with the duration time when the positive button is clicked.
     */
    fun onPositive(
        positiveText: String,
        @DrawableRes drawableRes: Int,
        listener: DurationTimeListener? = null
    ) {
        this.positiveText = positiveText
        this.positiveButtonDrawable = ContextCompat.getDrawable(windowContext, drawableRes)
        this.listener = listener
    }


    /**
     * Set the text and icon of the positive button and set the [DurationTimeListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param drawable The drawable for the button icon.
     * @param listener Listener that is invoked with the duration time when the positive button is clicked.
     */
    fun onPositive(
        @StringRes positiveRes: Int,
        drawable: Drawable,
        listener: DurationTimeListener? = null
    ) {
        this.positiveText = windowContext.getString(positiveRes)
        this.positiveButtonDrawable = drawable
        this.listener = listener
    }

    /**
     *  Set the text and icon of the positive button and set the [DurationTimeListener].
     *
     * @param positiveText The text for the positive button.
     * @param drawable The drawable for the button icon.
     * @param listener Listener that is invoked with the duration time when the positive button is clicked.
     */
    fun onPositive(
        positiveText: String,
        drawable: Drawable,
        listener: DurationTimeListener? = null
    ) {
        this.positiveText = positiveText
        this.positiveButtonDrawable = drawable
        this.listener = listener
    }

    private val validationListener: TimeValidationListener = { valid ->
        saveAllowed = valid
        displayButtonPositive(saveAllowed)
    }

    private fun save() {
        listener?.invoke(selector.getTimeInSeconds())
        dismiss()
    }

    override fun onCreateLayoutView(): View =
        SheetsDurationBinding.inflate(LayoutInflater.from(activity)).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonPositiveListener(::save)
        selector = TimeSelector(
            requireContext(),
            binding = binding,
            format = format,
            minTime = minTime,
            maxTime = maxTime,
            validationListener = validationListener
        )
        currentTime?.let { selector.setTime(it) }
    }

    /** Build [DurationSheet] and show it later. */
    fun build(ctx: Context, width: Int? = null, func: DurationSheet.() -> Unit): DurationSheet {
        this.windowContext = ctx
        this.width = width
        this.func()
        return this
    }

    /** Build and show [DurationSheet] directly. */
    fun show(ctx: Context, width: Int? = null, func: DurationSheet.() -> Unit): DurationSheet {
        this.windowContext = ctx
        this.width = width
        this.func()
        this.show()
        return this
    }
}