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

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.nahal.developer.family.nahal.databinding.SheetsColorTemplatesItemBinding

internal class ColorAdapter(
    @ColorInt
    private val colors: MutableList<Int>,
    @ColorInt
    private var selectedColor: Int,
    private val callback: ColorListener
) : RecyclerView.Adapter<ColorAdapter.ColorItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorItem =
        ColorItem(
            SheetsColorTemplatesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ColorItem, i: Int) {
        val color = colors[i]
        with(holder.binding) {

            val background = colorView.background as RippleDrawable
            (background.getDrawable(1) as GradientDrawable).setColor(color)

            root.setOnClickListener {
                select(color)
                callback.invoke(color)
            }

            if (color == selectedColor) {
                colorActive.visibility = View.VISIBLE
                colorActive.setColorFilter(getContrastColor(color))
            } else {
                colorActive.visibility = View.GONE
            }
        }
    }

    private fun select(color: Int) {
        notifyItemChanged(colors.indexOf(selectedColor))
        notifyItemChanged(colors.indexOf(color))
        selectedColor = color
    }

    fun removeSelection() {
        notifyItemChanged(colors.indexOf(selectedColor))
        selectedColor = RecyclerView.NO_POSITION
    }

    override fun getItemCount(): Int = colors.size

    inner class ColorItem(val binding: SheetsColorTemplatesItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}