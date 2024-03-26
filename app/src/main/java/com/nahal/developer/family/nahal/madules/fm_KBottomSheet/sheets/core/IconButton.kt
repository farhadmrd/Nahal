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

package com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

/**
 * Represents an icon acting as a button
 * giving additional possibilities for actions.
 */
class IconButton internal constructor() {

    internal var listener: ClickListener? = null

    @DrawableRes
    internal var drawableRes: Int? = null

    internal var drawable: Drawable? = null

    @ColorRes
    internal var drawableColor: Int? = null

    constructor(@DrawableRes drawableRes: Int) : this() {
        this.drawableRes = drawableRes
    }

    constructor(drawable: Drawable) : this() {
        this.drawable = drawable
    }

    constructor(
        @DrawableRes drawableRes: Int,
        @ColorRes drawableColor: Int
    ) : this() {
        this.drawableRes = drawableRes
        this.drawableColor = drawableColor
    }

    constructor(
        drawable: Drawable,
        @ColorRes drawableColor: Int
    ) : this() {
        this.drawable = drawable
        this.drawableColor = drawableColor
    }

    internal fun listener(listener: ClickListener?) {
        this.listener = listener
    }
}