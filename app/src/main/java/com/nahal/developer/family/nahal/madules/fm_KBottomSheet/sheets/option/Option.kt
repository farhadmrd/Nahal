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
 * Listener that is invoked when an option view is long clicked.
 * This could be used to initiate another action e. g. editing the option in another sheet.
 */
typealias OptionLongClickListener = () -> Unit

/**
 * An option is represented with at least a text.
 * A drawable is optional but makes it easier to understand to the user.
 */
class Option internal constructor() {

    internal var drawable: Drawable? = null
        private set

    @DrawableRes
    internal var drawableRes: Int? = null
        private set

    internal var titleText: String? = null
        private set

    @StringRes
    internal var titleTextRes: Int? = null
        private set

    internal var subtitleText: String? = null
        private set

    @StringRes
    internal var subtitleTextRes: Int? = null
        private set

    @ColorRes
    internal var defaultIconColorRes: Int? = null
        private set

    @ColorInt
    internal var defaultIconColor: Int? = null
        private set

    @ColorRes
    internal var defaultTitleColorRes: Int? = null
        private set

    @ColorInt
    internal var defaultTitleColor: Int? = null
        private set

    @ColorRes
    internal var defaultSubtitleColorRes: Int? = null
        private set

    @ColorInt
    internal var defaultSubtitleColor: Int? = null
        private set

    internal var selected: Boolean = false
        private set

    internal var disabled: Boolean = false
        private set

    internal var longClickListener: OptionLongClickListener? = null
        private set

    internal var preventIconTint: Boolean? = null
        private set

    constructor(@StringRes textRes: Int) : this() {
        this.titleTextRes = textRes
    }

    constructor(text: String) : this() {
        this.titleText = text
    }

    constructor(@DrawableRes drawableRes: Int, titleText: String) : this() {
        this.drawableRes = drawableRes
        this.titleText = titleText
    }

    constructor(drawable: Drawable, @StringRes titleTextRes: Int) : this() {
        this.drawable = drawable
        this.titleTextRes = titleTextRes
    }

    constructor(drawable: Drawable, titleText: String) : this() {
        this.drawable = drawable
        this.titleText = titleText
    }

    constructor(@DrawableRes drawableRes: Int, @StringRes titleTextRes: Int) : this() {
        this.drawableRes = drawableRes
        this.titleTextRes = titleTextRes
    }

    constructor(
        @DrawableRes drawableRes: Int,
        titleText: String,
        subtitleText: String? = null,
        longClickListener: OptionLongClickListener? = null
    ) : this() {
        this.drawableRes = drawableRes
        this.titleText = titleText
        this.subtitleText = subtitleText
        this.longClickListener = longClickListener
    }

    constructor(
        drawable: Drawable,
        @StringRes titleTextRes: Int,
        subtitleText: String? = null,
        longClickListener: OptionLongClickListener? = null
    ) : this() {
        this.drawable = drawable
        this.titleTextRes = titleTextRes
        this.subtitleText = subtitleText
        this.longClickListener = longClickListener
    }

    constructor(
        drawable: Drawable,
        titleText: String,
        subtitleText: String? = null,
        longClickListener: OptionLongClickListener? = null
    ) : this() {
        this.drawable = drawable
        this.titleText = titleText
        this.subtitleText = subtitleText
        this.longClickListener = longClickListener
    }

    constructor(
        @DrawableRes drawableRes: Int,
        @StringRes titleTextRes: Int,
        subtitleText: String? = null,
        longClickListener: OptionLongClickListener? = null
    ) : this() {
        this.drawableRes = drawableRes
        this.titleTextRes = titleTextRes
        this.subtitleText = subtitleText
        this.longClickListener = longClickListener
    }

    constructor(
        @DrawableRes drawableRes: Int,
        titleText: String,
        @StringRes subtitleTextRes: Int? = null,
        longClickListener: OptionLongClickListener? = null
    ) : this() {
        this.drawableRes = drawableRes
        this.titleText = titleText
        this.subtitleTextRes = subtitleTextRes
        this.longClickListener = longClickListener
    }

    constructor(
        drawable: Drawable,
        @StringRes titleTextRes: Int,
        @StringRes subtitleTextRes: Int? = null,
        longClickListener: OptionLongClickListener? = null
    ) : this() {
        this.drawable = drawable
        this.titleTextRes = titleTextRes
        this.subtitleTextRes = subtitleTextRes
        this.longClickListener = longClickListener
    }

    constructor(
        drawable: Drawable,
        titleText: String,
        @StringRes subtitleTextRes: Int? = null,
        longClickListener: OptionLongClickListener? = null
    ) : this() {
        this.drawable = drawable
        this.titleText = titleText
        this.subtitleTextRes = subtitleTextRes
        this.longClickListener = longClickListener
    }

    constructor(
        @DrawableRes drawableRes: Int,
        @StringRes titleTextRes: Int,
        @StringRes subtitleTextRes: Int? = null,
        longClickListener: OptionLongClickListener? = null
    ) : this() {
        this.drawableRes = drawableRes
        this.titleTextRes = titleTextRes
        this.subtitleTextRes = subtitleTextRes
        this.longClickListener = longClickListener
    }

    constructor(
        @StringRes titleTextRes: Int,
        @StringRes subtitleTextRes: Int? = null,
        longClickListener: OptionLongClickListener? = null
    ) : this() {
        this.titleTextRes = titleTextRes
        this.subtitleTextRes = subtitleTextRes
        this.longClickListener = longClickListener
    }

    constructor(
        titleText: String,
        subtitleText: String,
        longClickListener: OptionLongClickListener? = null
    ) : this() {
        this.titleText = titleText
        this.subtitleText = subtitleText
        this.longClickListener = longClickListener
    }


    /** Declare the option as already selected. */
    fun select(): Option {
        this.selected = true
        return this
    }

    /** Declare the option as already selected. */
    fun selected(selected: Boolean): Option {
        this.selected = selected
        return this
    }

    /** Declare the option as disabled. Disabled options are not selectable anymore. */
    fun disable(): Option {
        this.disabled = true
        return this
    }

    /** Declare the option as disabled. Disabled options are not selectable anymore. */
    fun disabled(disabled: Boolean): Option {
        this.disabled = disabled
        return this
    }

    /**
     * Sheets applies by default a one-colored tint on the drawable representing an option.
     * You can prevent this behavior in order to keep the original colors of the drawable.
     */
    fun preventIconTint(preventIconTint: Boolean): Option {
        this.preventIconTint = preventIconTint
        return this
    }

    /** Set default icon color. */
    fun defaultIconColor(@ColorInt color: Int): Option {
        this.defaultIconColor = color
        return this
    }

    /** Set default icon color. */
    fun defaultIconColorRes(@ColorRes colorRes: Int): Option {
        this.defaultIconColorRes = colorRes
        return this
    }

    /** Set default title color. */
    fun defaultTitleColor(@ColorInt color: Int): Option {
        this.defaultTitleColor = color
        return this
    }

    /** Set default title color. */
    fun defaultTitleColorRes(@ColorRes colorRes: Int): Option {
        this.defaultTitleColorRes = colorRes
        return this
    }

    /** Set default subtitle color. */
    fun defaultSubtitleColor(@ColorInt color: Int): Option {
        this.defaultSubtitleColor = color
        return this
    }

    /** Set default subtitle color. */
    fun defaultSubtitleColorRes(@ColorRes colorRes: Int): Option {
        this.defaultSubtitleColorRes = colorRes
        return this
    }

}