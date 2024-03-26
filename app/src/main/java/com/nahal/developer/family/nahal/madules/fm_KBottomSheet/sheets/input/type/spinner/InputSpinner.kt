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

package com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.input.type.spinner

import android.os.Bundle
import androidx.annotation.StringRes
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.input.type.Input

/** Listener which returns the new index. */
typealias InputSpinnerListener = (index: Int) -> Unit

/**
 * Input of the type Spinner.
 */
class InputSpinner(key: String? = null, func: InputSpinner.() -> Unit) : Input(key) {

    init {
        func()
    }

    private var changeListener: InputSpinnerListener? = null
    private var resultListener: InputSpinnerListener? = null

    internal var spinnerOptions: List<SpinnerOption>? = null
        private set

    internal var noSelectionText: String? = null
        private set

    internal var textRes: Int? = null
        private set

    var value: Int? = null
        internal set(value) {
            value?.let { changeListener?.invoke(it) }
            field = value
        }

    /** Set the by default selected index. */
    fun selected(selectedIndex: Int) {
        this.value = selectedIndex
    }

    /** Set the options with a list of Strings. */
    @JvmName("optionsStrings")
    fun options(options: List<String>) {
        this.spinnerOptions = options.map { SpinnerOption(it) }
    }

    /** Set the options with a list of StringRes. */
    @JvmName("optionsStringRes")
    fun options(options: List<Int>) {
        this.spinnerOptions = options.map { SpinnerOption(it) }
    }

    /** Set the [SpinnerOption]'s to to be displayed. */
    fun options(options: List<SpinnerOption>) {
        this.spinnerOptions = options
    }

    /** Set the text when no item is selected. */
    fun noSelectionText(@StringRes textRes: Int) {
        this.textRes = textRes
    }

    /** Set the text when no item is selected. */
    fun noSelectionText(text: String) {
        this.noSelectionText = text
    }

    /** Set a listener which returns the new value when it changed. */
    fun changeListener(listener: InputSpinnerListener) {
        this.changeListener = listener
    }

    /** Set a listener which returns the final value when the user clicks the positive button. */
    fun resultListener(listener: InputSpinnerListener) {
        this.resultListener = listener
    }

    override fun invokeResultListener() =
        value?.let { resultListener?.invoke(it) }

    override fun valid(): Boolean = value != -1 && value != null

    override fun putValue(bundle: Bundle, index: Int) {
        value?.let { bundle.putInt(getKeyOrIndex(index), it) }
    }
}