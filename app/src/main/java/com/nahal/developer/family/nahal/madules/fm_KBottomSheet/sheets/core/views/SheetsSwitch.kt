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

package com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import androidx.core.view.ViewCompat
import com.google.android.material.color.MaterialColors
import com.google.android.material.elevation.ElevationOverlayProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.nahal.developer.family.nahal.R
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.utils.getPrimaryColor

/** Custom switch button used for the InputSheet. */
class SheetsSwitch
@JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    styleAttrs: Int = com.google.android.material.R.attr.switchStyle,
) : SwitchMaterial(ctx, attrs, styleAttrs) {

    val primaryColor = getPrimaryColor(ctx)

    companion object {

        /** Taken from [SwitchMaterial]. */
        private val ENABLED_CHECKED_STATES =
            arrayOf(intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_enabled, android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_enabled, -android.R.attr.state_checked))
    }

    init {
        thumbTintList = getCustomColorsThumbTintList()
        trackTintList = getCustomColorsTrackTintList()
    }

    /** Taken from [SwitchMaterial]. */
    private fun getCustomColorsThumbTintList(): ColorStateList {
        val elevationOverlayProvider = ElevationOverlayProvider(context)
        val colorSurface = MaterialColors.getColor(this, com.google.android.material.R.attr.colorSurface)
        var thumbElevation: Float = resources.getDimension(com.google.android.material.R.dimen.mtrl_switch_thumb_elevation)
        if (elevationOverlayProvider.isThemeElevationOverlayEnabled) {
            thumbElevation += getParentAbsoluteElevation(this)
        }
        val colorThumbOff: Int =
            elevationOverlayProvider.compositeOverlayIfNeeded(colorSurface, thumbElevation)
        val thumbs = IntArray(ENABLED_CHECKED_STATES.size)
        thumbs[0] =
            MaterialColors.layer(colorSurface, primaryColor, MaterialColors.ALPHA_FULL)
        thumbs[1] = colorThumbOff
        thumbs[2] =
            MaterialColors.layer(colorSurface, primaryColor, MaterialColors.ALPHA_DISABLED)
        thumbs[3] = colorThumbOff
        return ColorStateList(ENABLED_CHECKED_STATES, thumbs)
    }

    /** Taken from [SwitchMaterial]. */
    private fun getCustomColorsTrackTintList(): ColorStateList {
        val tracks = IntArray(ENABLED_CHECKED_STATES.size)
        val colorSurface = MaterialColors.getColor(this, com.google.android.material.R.attr.colorSurface)
        val colorOnSurface = MaterialColors.getColor(this, com.google.android.material.R.attr.colorOnSurface)
        tracks[0] =
            MaterialColors.layer(colorSurface, primaryColor, MaterialColors.ALPHA_MEDIUM)
        tracks[1] = MaterialColors.layer(colorSurface, colorOnSurface, MaterialColors.ALPHA_LOW)
        tracks[2] = MaterialColors.layer(colorSurface, primaryColor,
            MaterialColors.ALPHA_DISABLED_LOW)
        tracks[3] =
            MaterialColors.layer(colorSurface, colorOnSurface, MaterialColors.ALPHA_DISABLED_LOW)
        return ColorStateList(ENABLED_CHECKED_STATES, tracks)
    }

    /** Taken from [SwitchMaterial]. */
    private fun getParentAbsoluteElevation(view: View): Float {
        var absoluteElevation = 0f
        var viewParent = view.parent
        while (viewParent is View) {
            absoluteElevation += ViewCompat.getElevation((viewParent as View))
            viewParent = viewParent.getParent()
        }
        return absoluteElevation
    }
}
