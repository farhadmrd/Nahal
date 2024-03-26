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

package com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.input.type

import android.os.Bundle
import androidx.annotation.StringRes

/** Listener which returns the new value. */
typealias CheckBoxInputListener = (value: Boolean) -> Unit

/**
 * Input of the type Checkbox.
 */
class InputCheckBox(key: String? = null, func: InputCheckBox.() -> Unit) : Input(key) {

    init {
        func()
    }

    private var changeListener: CheckBoxInputListener? = null
    private var resultListener: CheckBoxInputListener? = null

    internal var text: String? = null
        private set

    internal var textRes: Int? = null
        private set

    var value: Boolean = false
        internal set(value) {
            changeListener?.invoke(value)
            field = value
        }

    /** Set the default value. */
    fun defaultValue(defaultValue: Boolean) {
        this.value = defaultValue
    }

    /** Set the text of the CheckBox. */
    fun text(@StringRes textRes: Int) {
        this.textRes = textRes
    }

    /** Set the text of the CheckBox. */
    fun text(text: String) {
        this.text = text
    }

    /** Set a listener which returns the new value when it changed. */
    fun changeListener(listener: CheckBoxInputListener) {
        this.changeListener = listener
    }

    /** Set a listener which returns the final value when the user clicks the positive button. */
    fun resultListener(listener: CheckBoxInputListener) {
        this.resultListener = listener
    }

    override fun invokeResultListener() =
        resultListener?.invoke(value ?: false)

    override fun valid(): Boolean = value ?: false


    override fun putValue(bundle: Bundle, index: Int) {
        bundle.putBoolean(getKeyOrIndex(index), value ?: false)
    }

}