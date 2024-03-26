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

package com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.option

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nahal.developer.family.nahal.R
import com.nahal.developer.family.nahal.databinding.SheetsOptionGridItemBinding
import com.nahal.developer.family.nahal.databinding.SheetsOptionInfoItemBinding
import com.nahal.developer.family.nahal.databinding.SheetsOptionListItemBinding
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.utils.*
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.views.SheetsContent

internal class OptionAdapter(
    private val ctx: Context,
    private val info: Info?,
    private val options: MutableList<Option>,
    private val globalPreventIconTint: Boolean?,
    private val type: DisplayMode,
    private val multipleChoice: Boolean,
    private val collapsedItems: Boolean,
    private val listener: OptionSelectionListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TAG_DISABLED_SELECTED = "tag_disabled_selected"
        private const val SELECTOR_STATE_DISABLED_INDEX = 0
        private const val SELECTOR_STATE_SELECTED_INDEX = 1
        private const val VIEW_TYPE_HEADER_INFO = 0
        private const val VIEW_TYPE_AUTO = 1
    }

    private val selectedOptions =
        mutableMapOf<Int, Triple<ImageView, SheetsContent, SheetsContent>>()

    private val iconsColor = getIconColor(ctx)
    private val textColor = getTextColor(ctx)
    private val highlightColor = getHighlightColor(ctx)

    private val selectedTextColor =
        colorOfAttr(ctx, R.attr.sheetsOptionSelectedTextColor).takeUnlessNotResolved()
            ?: getPrimaryColor(ctx)

    private val selectedIconsColor =
        colorOfAttr(ctx, R.attr.sheetsOptionSelectedImageColor).takeUnlessNotResolved()
            ?: getPrimaryColor(ctx)

    private val disabledTextColor =
        colorOfAttr(ctx, R.attr.sheetsOptionDisabledTextColor).takeUnlessNotResolved()
            ?: getTextColor(ctx)

    private val disabledIconsColor =
        colorOfAttr(ctx, R.attr.sheetsOptionDisabledImageColor).takeUnlessNotResolved()
            ?: getIconColor(ctx)

    private val disabledBackgroundColor =
        colorOfAttr(ctx, R.attr.sheetsOptionDisabledBackgroundColor).takeUnlessNotResolved()
            ?: colorOf(ctx, R.color.sheetsOptionDisabledColor)

    override fun getItemViewType(position: Int): Int =
        if (position == 0 && info != null) VIEW_TYPE_HEADER_INFO else VIEW_TYPE_AUTO

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == VIEW_TYPE_HEADER_INFO) {
            InfoItem(
                SheetsOptionInfoItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else when (type) {
            DisplayMode.GRID_HORIZONTAL,
            DisplayMode.GRID_VERTICAL -> {
                GridItem(
                    SheetsOptionGridItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            DisplayMode.LIST -> {
                ListItem(
                    SheetsOptionListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, i: Int) {
        val actualIndex = i.takeUnless { info != null } ?: i - 1
        when (holder) {
            is InfoItem -> holder.binding.buildHeaderItem()
            is GridItem -> holder.binding.buildGridItem(actualIndex)
            is ListItem -> holder.binding.buildListItem(actualIndex)
        }
    }

    private fun SheetsOptionInfoItemBinding.buildHeaderItem() {
        info?.contentText?.let { content.text = it }
        if (info?.drawableRes != null || info?.drawable != null) {
            val infoIconDrawable = info.drawable ?: info.drawableRes?.let {
                ContextCompat.getDrawable(ctx, it)
            }
            icon.setImageDrawable(infoIconDrawable)
            icon.setColorFilter(info.drawableColor ?: getIconColor(ctx))
            icon.visibility = View.VISIBLE
        }
    }

    private fun SheetsOptionListItemBinding.buildListItem(i: Int) {

        val option = options[i]

        title.text = option.titleTextRes?.let { ctx.getString(it) } ?: option.titleText ?: ""

        val subtitleText = option.subtitleTextRes?.let { ctx.getString(it) } ?: option.subtitleText
        subtitle.visibility = if (subtitleText != null) View.VISIBLE else View.GONE
        subtitle.text = subtitleText

        option.drawable?.let {
            icon.setImageDrawable(it)
            icon.visibility = View.VISIBLE
        }

        option.drawableRes?.let { res ->
            icon.setImageDrawable(ContextCompat.getDrawable(ctx, res))
            icon.visibility = View.VISIBLE
        }

        optionContainer.setOnLongClickListener { option.longClickListener?.invoke(); true }
        optionContainer.changeRippleAndStateColor()

        val selected = option.selected || listener.isSelected(i)

        if (option.disabled && !selected) {
            showDisabled(title, subtitle, icon, optionContainer)
        } else {

            if (option.disabled && selected) optionContainer.tag = TAG_DISABLED_SELECTED
            else optionContainer.setOnClickListener {
                selectOption(i, title, subtitle, icon, optionContainer)
            }

            if (selected) selectOption(i, title, subtitle, icon, optionContainer)
            else showDeselected(i, title, subtitle, icon, optionContainer)
        }
    }

    private fun SheetsOptionGridItemBinding.buildGridItem(i: Int) {

        val option = options[i]

        if (collapsedItems) {
            root.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
        }

        title.text = option.titleTextRes?.let { ctx.getString(it) } ?: option.titleText ?: ""

        val subtitleText = option.subtitleTextRes?.let { ctx.getString(it) } ?: option.subtitleText
        subtitle.visibility = if (subtitleText != null) View.VISIBLE else View.GONE
        subtitle.text = subtitleText

        option.drawable?.let {
            icon.setImageDrawable(it)
            icon.visibility = View.VISIBLE
        }

        option.drawableRes?.let { res ->
            icon.setImageDrawable(ContextCompat.getDrawable(ctx, res))
            icon.visibility = View.VISIBLE
        }

        optionContainer.setOnLongClickListener { option.longClickListener?.invoke(); true }
        optionContainer.changeRippleAndStateColor()

        val selected = option.selected || listener.isSelected(i)

        if (option.disabled && !selected) {
            showDisabled(title, subtitle, icon, optionContainer)
        } else {

            if (option.disabled && selected) optionContainer.tag = TAG_DISABLED_SELECTED
            else optionContainer.setOnClickListener {
                selectOption(i, title, subtitle, icon, optionContainer)
            }

            if (selected) selectOption(i, title, subtitle, icon, optionContainer)
            else showDeselected(i, title, subtitle, icon, optionContainer)
        }
    }

    private fun showDisabled(
        title: SheetsContent,
        subtitle: SheetsContent,
        icon: ImageView,
        root: View,
    ) {
        title.setTextColor(disabledTextColor)
        subtitle.setTextColor(disabledTextColor)
        icon.setColorFilter(disabledIconsColor)
        root.changeRippleAndStateColor(
            Color.TRANSPARENT,
            SELECTOR_STATE_DISABLED_INDEX,
            disabledBackgroundColor
        )
        root.isActivated = true
    }

    private fun showSelected(
        title: SheetsContent,
        subtitle: SheetsContent,
        icon: ImageView,
        root: View,
    ) {

        title.setTextColor(selectedTextColor)
        subtitle.setTextColor(selectedTextColor)
        icon.setColorFilter(selectedIconsColor)

        if (root.tag == TAG_DISABLED_SELECTED) {
            root.changeRippleAndStateColor(Color.TRANSPARENT)
        }

        if (multipleChoice) {
            root.isSelected = true
        }
    }

    private fun showDeselected(
        index: Int,
        title: SheetsContent,
        subtitle: SheetsContent,
        icon: ImageView,
        root: View,
    ) {
        val option = options[index]
        with(option) {

            val titleColor =
                defaultTitleColor ?: defaultTitleColorRes?.let { ContextCompat.getColor(ctx, it) }
                ?: textColor

            val subtitleColor = defaultSubtitleColor ?: defaultSubtitleColorRes?.let {
                ContextCompat.getColor(
                    ctx,
                    it
                )
            } ?: textColor

            val preventIconTint = option.preventIconTint ?: globalPreventIconTint
            val iconsColor = defaultIconColor
                ?: defaultIconColorRes?.let { ContextCompat.getColor(ctx, it) }
                ?: takeUnless { preventIconTint == true }?.let { iconsColor }

            title.setTextColor(titleColor)
            subtitle.setTextColor(subtitleColor)
            iconsColor?.let { color ->
                icon.setColorFilter(color)
            } ?: run {
                icon.clearColorFilter()
            }
        }
        if (multipleChoice) {
            root.isSelected = false
        }
    }

    private fun View.changeRippleAndStateColor(
        rippleColor: Int = highlightColor,
        stateIndex: Int = SELECTOR_STATE_SELECTED_INDEX,
        stateBackgroundColor: Int = highlightColor,
    ) {
        // Ripple drawable
        (background as RippleDrawable).apply {
            setColor(ColorStateList.valueOf(rippleColor))
            // Selector drawable
            (getDrawable(1) as StateListDrawable).apply {
                // Selected state drawable
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    (getStateDrawable(stateIndex) as GradientDrawable).apply {
                        color = ColorStateList.valueOf(stateBackgroundColor)
                    }
                }
            }
        }
    }

    private fun selectOption(
        index: Int,
        title: SheetsContent,
        subtitle: SheetsContent,
        icon: ImageView,
        root: View,
    ) {
        if (multipleChoice) {
            if (!listener.isMultipleChoiceSelectionAllowed(index)) return
            if (selectedOptions.contains(index)) {
                listener.deselectMultipleChoice(index)
                selectedOptions[index]?.let {
                    showDeselected(
                        index,
                        it.second,
                        it.third,
                        it.first,
                        root
                    )
                }
                selectedOptions.remove(index)
            } else {
                listener.selectMultipleChoice(index)
                selectedOptions[index] = Triple(icon, title, subtitle)
                showSelected(title, subtitle, icon, root)
            }
        } else {
            selectedOptions.forEach { option ->
                showDeselected(
                    option.key,
                    option.value.second,
                    option.value.third,
                    option.value.first,
                    root
                )
            }
            selectedOptions.clear()
            selectedOptions[index] = Triple(icon, title, subtitle)
            showSelected(title, subtitle, icon, root)
            listener.select(index)
        }
    }

    override fun getItemCount(): Int = options.size.plus(if (info != null) 1 else 0)

    inner class InfoItem(val binding: SheetsOptionInfoItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class GridItem(val binding: SheetsOptionGridItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ListItem(val binding: SheetsOptionListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}