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
import android.text.InputFilter
import android.text.InputType
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.input.ValidationResult

/** Listener that returns the new value. */
typealias EditTextInputListener = (value: CharSequence?) -> Unit

/** Listener that is invoked when the value changes and to which a custom validation logic can be executed. */
typealias EditTextInputValidationListener = (value: CharSequence /* Non-nullable, text entered can not be null. */) -> ValidationResult

/** Listener that is invoked after a custom validation. */
internal typealias EditTextInputValidationResultListener = (validationResult: ValidationResult) -> Unit

/**
 * Input of the type EditText.
 */
class InputEditText(key: String? = null, func: InputEditText.() -> Unit) : Input(key) {

    init {
        func()
    }

    private var validationResultListener: EditTextInputValidationResultListener? = null
    private var validationListener: EditTextInputValidationListener? = null
    private var changeListener: EditTextInputListener? = null
    private var resultListener: EditTextInputListener? = null

    internal var hint: String? = null
        private set

    internal var hintRes: Int? = null
        private set

    internal var inputType: Int? = null
        private set

    internal var inputFilter: InputFilter? = null
        private set

    internal var maxLines: Int? = null
        private set

    internal var endIconMode: Int? = null
        private set

    @DrawableRes
    internal var startIconDrawableRes: Int? = null

    @DrawableRes
    internal var errorIconDrawableRes: Int? = null

    internal var isPasswordVisible: Boolean? = null
        private set

    internal var isEndIconActivated: Boolean? = null
        private set

    internal var suffix: String? = null
        private set

    @StringRes
    internal var suffixRes: Int? = null
        private set

    var value: CharSequence? = null
        internal set(value) {
            invokeListeners(value)
            field = value
        }

    private fun invokeListeners(value: CharSequence?) {
        changeListener?.invoke(value)
        value?.let { textValue ->
            validationListener?.invoke(textValue)?.let { result ->
                validationResultListener?.invoke(result)
            }
        }
    }

    /** Set the default value. */
    fun defaultValue(@StringRes defaultTextRes: Int) {
        this.hintRes = defaultTextRes
    }

    /** Set the default value. */
    fun defaultValue(defaultText: CharSequence) {
        this.value = defaultText
    }

    /** Set the hint text. */
    fun hint(@StringRes hintRes: Int) {
        this.hintRes = hintRes
    }

    /** Set the hint text. */
    fun hint(hint: String) {
        this.hint = hint
    }

    /** Set the [InputType]. */
    fun inputType(inputType: Int) {
        this.inputType = inputType
    }

    /** Set the [InputFilter]. */
    fun inputFilter(inputFilter: InputFilter) {
        this.inputFilter = inputFilter
    }

    /** Set the max lines. */
    fun maxLines(maxLines: Int) {
        this.maxLines = maxLines
    }

    /** Set a start drawable. */
    fun startIconDrawable(@DrawableRes drawableRes: Int) {
        this.startIconDrawableRes = drawableRes
    }

    /** Set a error drawable. */
    fun errorIconDrawable(@DrawableRes drawableRes: Int) {
        this.errorIconDrawableRes = drawableRes
    }

    /** Set the end icon mode. */
    fun endIconMode(@TextInputLayout.EndIconMode endIconMode: Int) {
        this.endIconMode = endIconMode
    }

    /** Activate the end icon. */
    fun endIconActivated(isEndIconActivated: Boolean) {
        this.isEndIconActivated = isEndIconActivated
    }

    /** Set the initial visibility of the password. This overrides the transformationMethod for the EditText. */
    fun passwordVisible(isPasswordVisible: Boolean) {
        this.isPasswordVisible = isPasswordVisible
    }

    /** Set suffix of edittext. */
    fun suffix(suffix: String) {
        this.suffix = suffix
    }

    /** Set suffix res of edittext. */
    fun suffixRes(suffixRes: Int) {
        this.suffixRes = suffixRes
    }

    /** Set a listener that is invoked when the value needs to be validated. */
    fun validationListener(listener: EditTextInputValidationListener) {
        this.validationListener = listener
    }

    internal fun validationResultListener(listener: EditTextInputValidationResultListener) {
        this.validationResultListener = listener
    }

    /** Set a listener which returns the new value when it changed. */
    fun changeListener(listener: EditTextInputListener) {
        this.changeListener = listener
    }

    /** Set a listener which returns the final value when the user clicks the positive button. */
    fun resultListener(listener: EditTextInputListener) {
        this.resultListener = listener
    }

    override fun valid(): Boolean {
        val customValidationOk = value?.let { validationListener?.invoke(it)?.valid } ?: true
        val requiredValid =
            (required && !value.isNullOrEmpty() || !required)
        return customValidationOk && requiredValid
    }

    override fun invokeResultListener() =
        resultListener?.invoke(value)

    override fun putValue(bundle: Bundle, index: Int) {
        bundle.putCharSequence(getKeyOrIndex(index), value)
    }
}