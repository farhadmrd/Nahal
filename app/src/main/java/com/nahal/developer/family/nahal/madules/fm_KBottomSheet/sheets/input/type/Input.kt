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
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * The base class of any input. Every input can have a label and a drawable.
 * It can be optional or required.
 */
abstract class Input(private val key: String? = null) {

    internal var required: Boolean = false

    internal var visible: Boolean = true
        private set

    internal var label: String? = null
        private set

    internal var labelRes: Int? = null
        private set

    internal var content: String? = null
        private set

    internal var contentRes: Int? = null
        private set

    internal var drawableRes: Int? = null
        private set

    internal var columns: Int? = null
        private set

    /** Set the initial visibility of the input. */
    fun visible(visible: Boolean = true) {
        this.visible = visible
    }

    /** Set the amount of columns this input will span. */
    fun columns(columns: Int) {
        this.columns = columns
    }

    /** Require a value before the user can click the positive button. */
    fun required(required: Boolean = true) {
        this.required = required
    }

    /** Set a drawable. */
    fun drawable(@DrawableRes drawableRes: Int) {
        this.drawableRes = drawableRes
    }

    /** Set the label text. */
    fun label(@StringRes labelRes: Int) {
        this.labelRes = labelRes
    }

    /** Set the label text. */
    fun label(label: String) {
        this.label = label
    }

    /** Set the content text. */
    fun content(@StringRes contentRes: Int) {
        this.contentRes = contentRes
    }

    /** Set the content text. */
    fun content(content: String) {
        this.content = content
    }

    /** Check if the input value is valid. */
    abstract fun valid(): Boolean

    /** Invoke the result listener which returns the input value. */
    abstract fun invokeResultListener(): Unit?

    /** Save the input value into the bundle. Takes the index as an key, if there's no unique input key available. */
    abstract fun putValue(bundle: Bundle, index: Int)

    internal fun getKeyOrIndex(index: Int): String =
        if (key.isNullOrEmpty()) index.toString() else key
}