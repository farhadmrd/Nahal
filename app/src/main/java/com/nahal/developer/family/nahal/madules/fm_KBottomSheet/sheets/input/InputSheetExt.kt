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

package com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.input

import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.RestrictTo
import androidx.appcompat.widget.AppCompatEditText

/** Add a [TextWatcher] to the [AppCompatEditText]. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun AppCompatEditText.addTextWatcher(textChanged: ((String) -> Unit)? = null): TextWatcher {
    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun afterTextChanged(editable: Editable?) {
            textChanged?.invoke(editable.toString())
        }
    }
    addTextChangedListener(textWatcher)
    return textWatcher
}