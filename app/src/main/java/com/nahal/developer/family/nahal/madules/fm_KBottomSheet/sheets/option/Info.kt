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

package com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.option

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * A info section that is displayed above all options.
 */
class Info internal constructor() {

    @ColorInt
    internal var drawableColor: Int? = null
        private set

    @ColorRes
    internal var drawableColorRes: Int? = null
        private set

    internal var drawable: Drawable? = null
        private set

    @DrawableRes
    internal var drawableRes: Int? = null
        private set

    internal var contentText: CharSequence? = null
        private set

    @StringRes
    internal var contentTextRes: Int? = null
        private set

    constructor(
        @StringRes contentRes: Int? = null,
        drawable: Drawable? = null,
        @ColorInt drawableColor: Int? = null,
    ) : this() {
        this.contentTextRes = contentRes
        this.drawable = drawable
        this.drawableColor = drawableColor
    }

    constructor(
        @StringRes contentRes: Int? = null,
        @DrawableRes drawableRes: Int? = null,
        @ColorInt drawableColor: Int? = null,
    ) : this() {
        this.contentTextRes = contentRes
        this.drawableRes = drawableRes
        this.drawableColor = drawableColor
    }

    constructor(
        @StringRes contentRes: Int? = null,
    ) : this() {
        this.contentTextRes = contentRes
    }

    constructor(
        content: CharSequence? = null,
    ) : this() {
        this.contentText = content
    }

    constructor(
        content: CharSequence? = null,
        @DrawableRes drawableRes: Int? = null,
        @ColorRes drawableColorRes: Int? = null,
    ) : this() {
        this.contentText = content
        this.drawableRes = drawableRes
        this.drawableColorRes = drawableColorRes
    }

    constructor(
        content: CharSequence? = null,
        drawable: Drawable? = null,
        @ColorRes drawableColorRes: Int? = null,
    ) : this() {
        this.contentText = content
        this.drawable = drawable
        this.drawableColorRes = drawableColorRes
    }

    constructor(
        content: CharSequence? = null,
        @StringRes contentRes: Int? = null,
        drawable: Drawable? = null,
        @DrawableRes drawableRes: Int? = null,
        @ColorInt drawableColor: Int? = null,
        @ColorRes drawableColorRes: Int? = null,
    ) : this() {
        this.drawableColor = drawableColor
        this.drawableColorRes = drawableColorRes
        this.drawable = drawable
        this.drawableRes = drawableRes
        this.contentText = content
        this.contentTextRes = contentRes
    }
}