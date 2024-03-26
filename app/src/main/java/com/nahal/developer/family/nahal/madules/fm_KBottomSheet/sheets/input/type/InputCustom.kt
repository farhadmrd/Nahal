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
import android.view.View
import androidx.annotation.LayoutRes
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.input.InputBehavior

/** Listener that is invoked when the view is ready. */
typealias InputCustomViewListener = (view: View) -> Unit

/** Listener that is invoked when the data of the input changed. */
typealias InputCustomDataChangeListener = () -> Unit

/**
 * Input of a unknown type displayed in a custom view.
 */
class InputCustom(key: String? = null, func: InputCustom.() -> Unit) : Input(key) {

    init {
        func()
    }

    internal var viewListener: InputCustomViewListener? = null
    internal var dataChangeListener: InputCustomDataChangeListener? = null
    internal var viewRes: Int? = null
    internal var view: View? = null
    private var behavior: InputBehavior? = null
    internal var fullView: Boolean = false

    /** Set the behavior. */
    fun behavior(behavior: InputBehavior) {
        this.behavior = behavior
    }

    /** Set the behavior. */
    inline fun behavior(
        crossinline onResult: () -> Unit = { },
        crossinline onValidate: () -> Boolean = { true },
        crossinline onSave: (bundle: Bundle, index: Int) -> Unit = { _, _ -> },
    ) = behavior(object : InputBehavior {
        override fun onResult() = onResult.invoke()
        override fun onValidate(): Boolean = onValidate.invoke()
        override fun onSave(bundle: Bundle, index: Int) = onSave.invoke(bundle, index)
    })

    /** Set the view. */
    fun view(@LayoutRes layout: Int, listener: InputCustomViewListener? = null) {
        this.viewRes = layout
        this.viewListener = listener
        this.view = null
    }

    /** Set the view. */
    fun view(view: View, listener: InputCustomViewListener? = null) {
        this.view = view
        this.viewListener = listener
        this.viewRes = null
    }

    /** The custom view will be take up the full space of the input and hide the label, content and icon. */
    fun fullView(fullView: Boolean) {
        this.fullView = fullView
    }

    /** Notify the validation process about the changed input data. */
    fun notifyInputDataChange() {
        this.dataChangeListener?.invoke()
    }

    override fun invokeResultListener() {
        behavior?.onResult()
    }

    override fun valid(): Boolean =
        behavior?.onValidate() ?: true

    override fun putValue(bundle: Bundle, index: Int) {
        behavior?.onSave(bundle, index)
    }
}