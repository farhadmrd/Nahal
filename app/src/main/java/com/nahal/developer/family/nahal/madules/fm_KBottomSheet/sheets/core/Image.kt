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
import android.net.Uri
import androidx.annotation.DrawableRes
import okhttp3.HttpUrl
import java.io.File

/** Convenience alias for this class. */
private typealias ImageBuilder = Image.() -> Unit

/** Convenience alias for coil's image request builder. */
private typealias CoiImageRequestBuilder = coil.request.ImageRequest.Builder.() -> Unit

/** Used to apply image request settings. */
typealias ImageRequestBuilder = ImageRequest.() -> Unit

/**
 * A class that holds the image data and some preferences for the image view and image loading.
 */
class Image private constructor() : ImageSource() {

    internal lateinit var any: Any
        private set

    private var imageRequestBuilder: ImageRequestBuilder = {
        // Use by default always crossfade
        crossfade(true)
    }

    internal val coilRequestBuilder: CoiImageRequestBuilder
        get() = {
            val request = ImageRequest().apply { imageRequestBuilder.invoke(this) }
            request.scale?.let { scale(it) }
            request.size?.let { size(it) }
            request.placeholderResId?.let { placeholder(it) }
            request.placeholderDrawable?.let { placeholder(it) }
            request.errorResId?.let { error(it) }
            request.errorDrawable?.let { error(it) }
            request.fallbackResId?.let { fallback(it) }
            request.fallbackDrawable?.let { fallback(it) }
            request.transition?.let { transition(it) }
            request.bitmapConfig?.let { bitmapConfig(it) }
        }

    constructor(uri: String, builder: ImageBuilder? = null) : this() {
        this.any = uri
        builder?.invoke(this)
    }

    constructor(url: HttpUrl, builder: ImageBuilder? = null) : this() {
        this.any = url
        builder?.invoke(this)
    }

    constructor(uri: Uri, builder: ImageBuilder? = null) : this() {
        this.any = uri
        builder?.invoke(this)
    }

    constructor(file: File, builder: ImageBuilder? = null) : this() {
        this.any = file
        builder?.invoke(this)
    }

    constructor(@DrawableRes drawableRes: Int, builder: ImageBuilder? = null) : this() {
        this.any = drawableRes
        builder?.invoke(this)
    }

    constructor(drawable: Drawable, builder: ImageBuilder? = null) : this() {
        this.any = drawable
        builder?.invoke(this)
    }

    constructor(bitmap: Bitmap, builder: ImageBuilder? = null) : this() {
        this.any = bitmap
        builder?.invoke(this)
    }

    /** Setup the image loading request. */
    fun setupRequest(builder: ImageRequestBuilder) {
        this.imageRequestBuilder = builder
    }

}