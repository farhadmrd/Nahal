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

package com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.input.type.spinner

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.nahal.developer.family.nahal.databinding.SheetsSpinnerItemBinding

internal open class SpinnerAdapter(
    private val ctx: Context,
    private var options: List<SpinnerOption> = listOf(),
) : BaseAdapter() {

    override fun getCount(): Int = options.size

    override fun getItem(position: Int): SpinnerOption = options[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(
        position: Int, convertView: View?,
        parent: ViewGroup,
    ): View = createViewFromResource(position,
        convertView,
        parent)

    private fun createViewFromResource(
        position: Int,
        convertView: View?,
        parent: ViewGroup,
    ): View {

        val binding = convertView?.let { SheetsSpinnerItemBinding.bind(it) }
            ?: SheetsSpinnerItemBinding.inflate(
                LayoutInflater.from(ctx), parent, false)

        val option = options[position]
        val text = option.text ?: option.textRes?.let { ctx.getString(it) }
        val drawable = option.drawable ?: option.drawableRes?.let { ContextCompat.getDrawable(ctx, it) }

        binding.text.text = text
        binding.icon.setImageDrawable(drawable)
        option.drawableTintRes?.let {
            binding.icon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(ctx, it))
        }
        binding.icon.visibility = if (drawable != null) View.VISIBLE else View.GONE

        return binding.root
    }

    override fun getDropDownView(
        position: Int, convertView: View?,
        parent: ViewGroup,
    ): View = createViewFromResource(
        position,
        convertView,
        parent
    )
}