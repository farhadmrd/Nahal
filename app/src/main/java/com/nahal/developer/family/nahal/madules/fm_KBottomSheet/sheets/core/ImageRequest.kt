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

@file:Suppress("unused", "EXPERIMENTAL_API_USAGE")

package com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import coil.ImageLoader
import coil.drawable.CrossfadeDrawable
import coil.size.PixelSize
import coil.size.Scale
import coil.size.Size
import coil.size.SizeResolver
import coil.transition.CrossfadeTransition
import coil.transition.Transition

/**
 * Wrapper class of the ImageRequest class of coil to offer basic functionality to the image loading.
 */
class ImageRequest internal constructor() {

    internal var scale: Scale? = null
    internal var size: SizeResolver? = null

    internal var placeholderResId: Int? = null
    internal var placeholderDrawable: Drawable? = null
    internal var errorResId: Int? = null
    internal var errorDrawable: Drawable? = null
    internal var fallbackResId: Int? = null
    internal var fallbackDrawable: Drawable? = null
    internal var transition: Transition? = null
    internal var bitmapConfig: Bitmap.Config? = null

    /** Set the placeholder drawable to use when the request starts. */
    fun placeholder(@DrawableRes drawableResId: Int) = apply {
        this.placeholderResId = drawableResId
        this.placeholderDrawable = null
    }

    /** Set the placeholder drawable to use when the request starts. */
    fun placeholder(drawable: Drawable?) = apply {
        this.placeholderDrawable = drawable
        this.placeholderResId = 0
    }

    /** Set the error drawable to use if the request fails. */
    fun error(@DrawableRes drawableResId: Int) = apply {
        this.errorResId = drawableResId
        this.errorDrawable = null
    }

    /** Set the error drawable to use if the request fails. */
    fun error(drawable: Drawable?) = apply {
        this.errorDrawable = drawable
        this.errorResId = 0
    }

    /** Set the fallback drawable to use if image is null. */
    fun fallback(@DrawableRes drawableResId: Int) = apply {
        this.fallbackResId = drawableResId
        this.fallbackDrawable = null
    }

    /** Set the fallback drawable to use if image is null. */
    fun fallback(drawable: Drawable?) = apply {
        this.fallbackDrawable = drawable
        this.fallbackResId = 0
    }

    /**
     * @see ImageLoader.Builder.crossfade
     */
    fun crossfade(enable: Boolean) =
        crossfade(if (enable) CrossfadeDrawable.DEFAULT_DURATION else 0)

    /**
     * @see ImageLoader.Builder.crossfade
     */
    fun crossfade(durationMillis: Int) =
        transition(if (durationMillis > 0) CrossfadeTransition(durationMillis) else Transition.NONE)


    /**
     * Set the requested width/height.
     */
    fun size(@Px size: Int) = size(size, size)

    /**
     * Set the requested width/height.
     */
    fun size(@Px width: Int, @Px height: Int) = size(PixelSize(width, height))

    /**
     * Set the requested width/height.
     */
    private fun size(size: Size) {
        this.size = SizeResolver(size)
    }

    /**
     * Set the scaling algorithm that will be used to fit/fill the image into the size provided by [sizeResolver].
     *
     * NOTE: If [scale] is not set, it is automatically computed for [ImageView] targets.
     */
    private fun scale(scale: Scale) = apply {
        this.scale = scale
    }

    /**
     * @see ImageLoader.Builder.bitmapConfig
     */
    fun bitmapConfig(config: Bitmap.Config) = apply {
        this.bitmapConfig = config
    }

    private fun transition(transition: Transition) = apply {
        this.transition = transition
    }
}