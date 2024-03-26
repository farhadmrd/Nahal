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

package com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.input

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.nahal.developer.family.nahal.databinding.SheetsInputBinding
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.Sheet
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.layoutmanagers.CustomGridLayoutManager
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.input.type.Input
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.input.type.InputRadioButtons

/** Listener which returns the inputs with the new data. */
typealias InputListener = (result: Bundle) -> Unit

/**
 * The [InputSheet] lets you display a form consisting of various inputs.
 */
class InputSheet : Sheet() {

    override val dialogTag = "InputSheet"

    companion object {
        const val COLUMNS_MAX_DEFAULT = 1
    }

    private lateinit var binding: SheetsInputBinding
    private lateinit var inputAdapter: InputAdapter
    private var listener: InputListener? = null
    private var inputs = mutableListOf<Input>()
    private var columnsMax: Int = COLUMNS_MAX_DEFAULT
    private var displayButtons = true

    private val saveAllowed: Boolean
        get() = inputs.filter { it.required }.all { it.valid() }

    /** Set the max amount of columns inputs can span. */
    fun columnsMax(@IntRange(from = 1) columns: Int) {
        this.columnsMax = columns
    }

    /** Display buttons and require a positive button click. */
    fun displayButtons(displayButtons: Boolean = true) {
        this.displayButtons = displayButtons
    }

    /**
     * Set the [InputListener].
     *
     * @param listener Listener that is invoked with the new input data when the positive button is clicked.
     */
    fun onPositive(listener: InputListener) {
        this.listener = listener
    }

    /**
     * Set the text of the positive button and set the [InputListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param listener Listener that is invoked with the new input data when the positive button is clicked.
     */
    fun onPositive(@StringRes positiveRes: Int, listener: InputListener? = null) {
        this.positiveText = windowContext.getString(positiveRes)
        this.listener = listener
    }

    /**
     *  Set the text of the positive button and set the [InputListener].
     *
     * @param positiveText The text for the positive button.
     * @param listener Listener that is invoked with the new input data when the positive button is clicked.
     */
    fun onPositive(positiveText: String, listener: InputListener? = null) {
        this.positiveText = positiveText
        this.listener = listener
    }

    /**
     * Set the text and icon of the positive button and set the [InputListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param drawableRes The drawable resource for the button icon.
     * @param listener Listener that is invoked with the new input data when the positive button is clicked.
     */
    fun onPositive(
        @StringRes positiveRes: Int,
        @DrawableRes drawableRes: Int,
        listener: InputListener? = null
    ) {
        this.positiveText = windowContext.getString(positiveRes)
        this.positiveButtonDrawable = ContextCompat.getDrawable(windowContext, drawableRes)
        this.listener = listener
    }

    /**
     *  Set the text and icon of the positive button and set the [InputListener].
     *
     * @param positiveText The text for the positive button.
     * @param drawableRes The drawable resource for the button icon.
     * @param listener Listener that is invoked with the new input data when the positive button is clicked.
     */
    fun onPositive(
        positiveText: String,
        @DrawableRes drawableRes: Int,
        listener: InputListener? = null
    ) {
        this.positiveText = positiveText
        this.positiveButtonDrawable = ContextCompat.getDrawable(windowContext, drawableRes)
        this.listener = listener
    }


    /**
     * Set the text and icon of the positive button and set the [InputListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param drawable The drawable for the button icon.
     * @param listener Listener that is invoked with the new input data when the positive button is clicked.
     */
    fun onPositive(
        @StringRes positiveRes: Int,
        drawable: Drawable,
        listener: InputListener? = null
    ) {
        this.positiveText = windowContext.getString(positiveRes)
        this.positiveButtonDrawable = drawable
        this.listener = listener
    }

    /**
     *  Set the text and icon of the positive button and set the [InputListener].
     *
     * @param positiveText The text for the positive button.
     * @param drawable The drawable for the button icon.
     * @param listener Listener that is invoked with the new input data when the positive button is clicked.
     */
    fun onPositive(
        positiveText: String,
        drawable: Drawable,
        listener: InputListener? = null
    ) {
        this.positiveText = positiveText
        this.positiveButtonDrawable = drawable
        this.listener = listener
    }

    /**
     * Add multiple inputs.
     *
     * @param input The [InputRadioButtons] arguments to be added.
     */
    fun with(vararg input: Input) {
        this.inputs.addAll(input.toMutableList())
    }

    /**
     * Add an input.
     *
     * @param input Instance of [InputRadioButtons].
     */
    fun with(input: Input) {
        this.inputs.add(input)
    }

    override fun onCreateLayoutView(): View =
        SheetsInputBinding.inflate(LayoutInflater.from(activity)).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonPositiveListener(::save)
        displayButtonsView(displayButtons)
        validate(true)
        inputAdapter = InputAdapter(requireContext(), inputs, ::validate)
        with(binding.inputRecyclerView) {
            layoutManager = CustomGridLayoutManager(requireContext(), columnsMax, true).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int =
                        inputs[position].columns?.takeUnless { it > columnsMax } ?: columnsMax
                }
            }
            adapter = inputAdapter
            setHasFixedSize(false)
        }
    }

    private fun validate(init: Boolean = false) {
        displayButtonPositive(saveAllowed, !init)
        if (!init && !displayButtons && saveAllowed) save()
    }

    private fun save() {
        val result = getResult()
        listener?.invoke(result)
        dismiss()
    }

    private fun getResult(): Bundle {
        val bundle = Bundle()
        inputs.forEachIndexed { i, input ->
            input.invokeResultListener()
            input.putValue(bundle, i)
        }
        return bundle
    }

    /** Display input. */
    fun displayInput(key: String, visible: Boolean) {
        if (this::inputAdapter.isInitialized) inputAdapter.displayInput(key, visible)
        else {
            inputs.forEachIndexed { i, comp ->
                if (comp.getKeyOrIndex(i) == key)
                    inputs[i].visible(visible)
            }
        }
    }

    /** Build [InputSheet] and show it later. */
    fun build(ctx: Context, width: Int? = null, func: InputSheet.() -> Unit): InputSheet {
        this.windowContext = ctx
        this.width = width
        this.func()
        return this
    }

    /** Build and show [InputSheet] directly. */
    fun show(ctx: Context, width: Int? = null, func: InputSheet.() -> Unit): InputSheet {
        this.windowContext = ctx
        this.width = width
        this.func()
        this.show()
        return this
    }
}