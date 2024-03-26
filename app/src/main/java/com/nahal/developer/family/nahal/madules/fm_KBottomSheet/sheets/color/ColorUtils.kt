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

package com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.color

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.RestrictTo
import androidx.core.graphics.ColorUtils
import com.nahal.developer.family.nahal.R


//method to determine colors luminance
@ColorInt
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
internal fun getContrastColor(color: Int): Int =
    if (ColorUtils.calculateLuminance(color) < 0.35) Color.WHITE else Color.DKGRAY

@ColorRes
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
internal fun getDefaultColorPalette(): IntArray = intArrayOf(

    R.color.sheets_md_teal_50,
    R.color.sheets_md_teal_100,
    R.color.sheets_md_teal_200,
    R.color.sheets_md_teal_300,
    R.color.sheets_md_teal_400,
    R.color.sheets_md_teal_500,
    R.color.sheets_md_teal_600,
    R.color.sheets_md_teal_700,
    R.color.sheets_md_teal_800,
    R.color.sheets_md_teal_900,

    R.color.sheets_md_light_green_50,
    R.color.sheets_md_light_green_100,
    R.color.sheets_md_light_green_200,
    R.color.sheets_md_light_green_300,
    R.color.sheets_md_light_green_400,
    R.color.sheets_md_light_green_500,
    R.color.sheets_md_light_green_600,
    R.color.sheets_md_light_green_700,
    R.color.sheets_md_light_green_800,
    R.color.sheets_md_light_green_900,

    R.color.sheets_md_green_50,
    R.color.sheets_md_green_100,
    R.color.sheets_md_green_200,
    R.color.sheets_md_green_300,
    R.color.sheets_md_green_400,
    R.color.sheets_md_green_500,
    R.color.sheets_md_green_600,
    R.color.sheets_md_green_700,
    R.color.sheets_md_green_800,
    R.color.sheets_md_green_900,

    R.color.sheets_md_cyan_50,
    R.color.sheets_md_cyan_100,
    R.color.sheets_md_cyan_200,
    R.color.sheets_md_cyan_300,
    R.color.sheets_md_cyan_400,
    R.color.sheets_md_cyan_500,
    R.color.sheets_md_cyan_600,
    R.color.sheets_md_cyan_700,
    R.color.sheets_md_cyan_800,
    R.color.sheets_md_cyan_900,

    R.color.sheets_md_light_blue_50,
    R.color.sheets_md_light_blue_100,
    R.color.sheets_md_light_blue_200,
    R.color.sheets_md_light_blue_300,
    R.color.sheets_md_light_blue_400,
    R.color.sheets_md_light_blue_500,
    R.color.sheets_md_light_blue_600,
    R.color.sheets_md_light_blue_700,
    R.color.sheets_md_light_blue_800,
    R.color.sheets_md_light_blue_900,

    R.color.sheets_md_blue_50,
    R.color.sheets_md_blue_100,
    R.color.sheets_md_blue_200,
    R.color.sheets_md_blue_300,
    R.color.sheets_md_blue_400,
    R.color.sheets_md_blue_500,
    R.color.sheets_md_blue_600,
    R.color.sheets_md_blue_700,
    R.color.sheets_md_blue_800,
    R.color.sheets_md_blue_900,

    R.color.sheets_md_indigo_50,
    R.color.sheets_md_indigo_100,
    R.color.sheets_md_indigo_200,
    R.color.sheets_md_indigo_300,
    R.color.sheets_md_indigo_400,
    R.color.sheets_md_indigo_500,
    R.color.sheets_md_indigo_600,
    R.color.sheets_md_indigo_700,
    R.color.sheets_md_indigo_800,
    R.color.sheets_md_indigo_900,

    R.color.sheets_md_deep_purple_50,
    R.color.sheets_md_deep_purple_100,
    R.color.sheets_md_deep_purple_200,
    R.color.sheets_md_deep_purple_300,
    R.color.sheets_md_deep_purple_400,
    R.color.sheets_md_deep_purple_500,
    R.color.sheets_md_deep_purple_600,
    R.color.sheets_md_deep_purple_700,
    R.color.sheets_md_deep_purple_800,
    R.color.sheets_md_deep_purple_900,

    R.color.sheets_md_purple_50,
    R.color.sheets_md_purple_100,
    R.color.sheets_md_purple_200,
    R.color.sheets_md_purple_300,
    R.color.sheets_md_purple_400,
    R.color.sheets_md_purple_500,
    R.color.sheets_md_purple_600,
    R.color.sheets_md_purple_700,
    R.color.sheets_md_purple_800,
    R.color.sheets_md_purple_900,


    R.color.sheets_md_pink_50,
    R.color.sheets_md_pink_100,
    R.color.sheets_md_pink_200,
    R.color.sheets_md_pink_300,
    R.color.sheets_md_pink_400,
    R.color.sheets_md_pink_500,
    R.color.sheets_md_pink_600,
    R.color.sheets_md_pink_700,
    R.color.sheets_md_pink_800,
    R.color.sheets_md_pink_900,

    R.color.sheets_md_red_50,
    R.color.sheets_md_red_100,
    R.color.sheets_md_red_200,
    R.color.sheets_md_red_300,
    R.color.sheets_md_red_400,
    R.color.sheets_md_red_500,
    R.color.sheets_md_red_600,
    R.color.sheets_md_red_700,
    R.color.sheets_md_red_800,
    R.color.sheets_md_red_900,

    R.color.sheets_md_orange_50,
    R.color.sheets_md_orange_100,
    R.color.sheets_md_orange_200,
    R.color.sheets_md_orange_300,
    R.color.sheets_md_orange_400,
    R.color.sheets_md_orange_500,
    R.color.sheets_md_orange_600,
    R.color.sheets_md_orange_700,
    R.color.sheets_md_orange_800,
    R.color.sheets_md_orange_900,

    R.color.sheets_md_amber_50,
    R.color.sheets_md_amber_100,
    R.color.sheets_md_amber_200,
    R.color.sheets_md_amber_300,
    R.color.sheets_md_amber_400,
    R.color.sheets_md_amber_500,
    R.color.sheets_md_amber_600,
    R.color.sheets_md_amber_700,
    R.color.sheets_md_amber_800,
    R.color.sheets_md_amber_900,

    R.color.sheets_md_yellow_50,
    R.color.sheets_md_yellow_100,
    R.color.sheets_md_yellow_200,
    R.color.sheets_md_yellow_300,
    R.color.sheets_md_yellow_400,
    R.color.sheets_md_yellow_500,
    R.color.sheets_md_yellow_600,
    R.color.sheets_md_yellow_700,
    R.color.sheets_md_yellow_800,
    R.color.sheets_md_yellow_900
)