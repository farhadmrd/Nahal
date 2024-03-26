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

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nahal.developer.family.nahal.R
import com.nahal.developer.family.nahal.databinding.SheetsOptionBinding
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.Sheet
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.layoutmanagers.CustomGridLayoutManager
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.layoutmanagers.CustomLinearLayoutManager
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.utils.getPrimaryColor

/** Listener which returns the selected index and the respective option.
 * If multiple choices is enabled, this listener will return nothing. Use the [OptionsListener]. */
typealias OptionListener = (index: Int, option: Option) -> Unit

/** Listener which returns the selected option's index and text. */
typealias OptionsListener = OptionSheet.(selectedIndices: MutableList<Int>, selectedOptions: MutableList<Option>) -> Unit

/**
 * The [OptionSheet] lets you display a grid or list of options.
 */
class OptionSheet : Sheet() {

    override val dialogTag = "OptionSheet"

    companion object {
        private const val SMALL_GRID_ITEMS_MIN = 2
        private const val SMALL_GRID_ITEMS_MAX = 8
        private const val GRID_COLUMNS_MAX_DEFAULT = 4
        private const val DEFAULT_COLUMN_SPAN = 1
    }

    private lateinit var binding: SheetsOptionBinding
    private lateinit var optionsAdapter: OptionAdapter

    private var colorActive: Int = 0

    private var listener: OptionListener? = null
    private var listenerMultiple: OptionsListener? = null
    private var options = mutableListOf<Option>()
    private var optionsSelected = mutableListOf<Int>()

    private var mode = DisplayMode.GRID_HORIZONTAL
    private var info: Info? = null
    private var multipleChoices = false
    private var displayMultipleChoicesInfo = false
    private var minChoices: Int? = null
    private var maxChoices: Int? = null
    private var maxChoicesStrict = true
    private var displayButtons = false
    private var gridColumns: Int? = null
    private var preventIconTint: Boolean? = null

    private val saveAllowed: Boolean
        get() {
            val validMultipleChoice =
                multipleChoices && optionsSelected.size >= minChoices ?: 0
                        && optionsSelected.size <= maxChoices ?: options.size
            val validSingleChoice = !multipleChoices && optionsSelected.isNotEmpty()
            return validMultipleChoice || validSingleChoice
        }

    /** Set an info section above the options. */
    fun withInfo(info: Info) {
        this.info = info
    }

    /** Set the nax amount of columns in [DisplayMode.GRID_VERTICAL] and [DisplayMode.GRID_HORIZONTAL]. */
    fun columns(@IntRange(from = 2, to = 8) columns: Int) {
        this.gridColumns = columns
    }

    /** Show buttons and require a positive button click. */
    fun multipleChoices(multipleChoices: Boolean = true) {
        this.multipleChoices = multipleChoices
    }

    /** Display the hints for allowed minimum and maximum amount of choices and the amount of selected options. */
    fun displayMultipleChoicesInfo(displayMultipleChoicesInfo: Boolean = true) {
        this.displayMultipleChoicesInfo = displayMultipleChoicesInfo
    }

    /** A strict limit for the maximum amount of choices will prevent the user to select more than allowed.  */
    fun maxChoicesStrictLimit(maxChoicesStrict: Boolean = true) {
        this.maxChoicesStrict = maxChoicesStrict
    }

    /** Set the minimum amount of (enabled) choices. */
    fun minChoices(@IntRange(from = 1) minChoices: Int) {
        maxChoices?.let { max ->
            if (minChoices > max)
                throw IllegalStateException("The minimum amount of selected options needs to be less or equal to the maximum amount.")
        }
        this.minChoices = minChoices
    }

    /** Set the maximum amount of (enabled) choices. */
    fun maxChoices(maxChoices: Int) {
        minChoices?.let { min ->
            if (maxChoices < min)
                throw IllegalStateException("The maximum amount of selected options needs to be more or equal to the minimum amount.")
        }
        this.maxChoices = maxChoices
    }

    /** Display buttons and require a positive button click. */
    fun displayButtons(displayButtons: Boolean = true) {
        this.displayButtons = displayButtons
    }

    /** Set display mode. */
    fun displayMode(displayMode: DisplayMode) {
        this.mode = displayMode
    }

    /**
     * Sheets applies by default a one-colored tint on the various option drawables.
     * You can prevent this behavior in order to keep the original colors of the drawables.
     * You can override this global settings on per-option basis through the [Option] class.
     */
    fun preventIconTint(preventIconTint: Boolean) {
        this.preventIconTint = preventIconTint
    }

    /**
     * Set the [OptionListener].
     *
     * @param listener Listener that is invoked with the selected options when the positive button is clicked.
     */
    fun onPositive(listener: OptionListener) {
        this.listener = listener
    }

    /**
     * Set the text of the positive button and set the [OptionListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param listener Listener that is invoked with the selected options when the positive button is clicked.
     */
    fun onPositive(@StringRes positiveRes: Int, listener: OptionListener? = null) {
        this.positiveText = windowContext.getString(positiveRes)
        this.listener = listener
    }

    /**
     *  Set the text of the positive button and set the [OptionListener].
     *
     * @param positiveText The text for the positive button.
     * @param listener Listener that is invoked with the selected options when the positive button is clicked.
     */
    fun onPositive(positiveText: String, listener: OptionListener? = null) {
        this.positiveText = positiveText
        this.listener = listener
    }

    /**
     * Set the text and icon of the positive button and set the [OptionListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param drawableRes The drawable resource for the button icon.
     * @param listener Listener that is invoked with the selected options when the positive button is clicked.
     */
    fun onPositive(
        @StringRes positiveRes: Int,
        @DrawableRes drawableRes: Int,
        listener: OptionListener? = null
    ) {
        this.positiveText = windowContext.getString(positiveRes)
        this.positiveButtonDrawable = ContextCompat.getDrawable(windowContext, drawableRes)
        this.listener = listener
    }

    /**
     *  Set the text and icon of the positive button and set the [OptionListener].
     *
     * @param positiveText The text for the positive button.
     * @param drawableRes The drawable resource for the button icon.
     * @param listener Listener that is invoked with the selected options when the positive button is clicked.
     */
    fun onPositive(
        positiveText: String,
        @DrawableRes drawableRes: Int,
        listener: OptionListener? = null
    ) {
        this.positiveText = positiveText
        this.positiveButtonDrawable = ContextCompat.getDrawable(windowContext, drawableRes)
        this.listener = listener
    }


    /**
     * Set the text and icon of the positive button and set the [OptionListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param drawable The drawable for the button icon.
     * @param listener Listener that is invoked with the selected options when the positive button is clicked.
     */
    fun onPositive(
        @StringRes positiveRes: Int,
        drawable: Drawable,
        listener: OptionListener? = null
    ) {
        this.positiveText = windowContext.getString(positiveRes)
        this.positiveButtonDrawable = drawable
        this.listener = listener
    }

    /**
     *  Set the text and icon of the positive button and set the [OptionListener].
     *
     * @param positiveText The text for the positive button.
     * @param drawable The drawable for the button icon.
     * @param listener Listener that is invoked with the selected options when the positive button is clicked.
     */
    fun onPositive(
        positiveText: String,
        drawable: Drawable,
        listener: OptionListener? = null
    ) {
        this.positiveText = positiveText
        this.positiveButtonDrawable = drawable
        this.listener = listener
    }


    /**
     * Set the [OptionListener].
     *
     * @param listener Listener that is invoked with the selected options when the positive button is clicked.
     */
    fun onPositiveMultiple(listener: OptionsListener) {
        this.listenerMultiple = listener
    }

    /**
     * Set the text of the positive button and set the [OptionListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param listener Listener that is invoked with the selected options when the positive button is clicked.
     */
    fun onPositiveMultiple(@StringRes positiveRes: Int, listener: OptionsListener? = null) {
        this.positiveText = windowContext.getString(positiveRes)
        this.listenerMultiple = listener
    }

    /**
     *  Set the text of the positive button and set the [OptionListener].
     *
     * @param positiveText The text for the positive button.
     * @param listener Listener that is invoked with the selected options when the positive button is clicked.
     */
    fun onPositiveMultiple(positiveText: String, listener: OptionsListener? = null) {
        this.positiveText = positiveText
        this.listenerMultiple = listener
    }

    /**
     * Set the text and icon of the positive button and set the [OptionListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param drawableRes The drawable resource for the button icon.
     * @param listener Listener that is invoked with the selected options when the positive button is clicked.
     */
    fun onPositiveMultiple(
        @StringRes positiveRes: Int,
        @DrawableRes drawableRes: Int,
        listener: OptionsListener? = null
    ) {
        this.positiveText = windowContext.getString(positiveRes)
        this.positiveButtonDrawable = ContextCompat.getDrawable(windowContext, drawableRes)
        this.listenerMultiple = listener
    }

    /**
     *  Set the text and icon of the positive button and set the [OptionListener].
     *
     * @param positiveText The text for the positive button.
     * @param drawableRes The drawable resource for the button icon.
     * @param listener Listener that is invoked with the selected options when the positive button is clicked.
     */
    fun onPositiveMultiple(
        positiveText: String,
        @DrawableRes drawableRes: Int,
        listener: OptionsListener? = null
    ) {
        this.positiveText = positiveText
        this.positiveButtonDrawable = ContextCompat.getDrawable(windowContext, drawableRes)
        this.listenerMultiple = listener
    }

    /**
     * Set the text and icon of the positive button and set the [OptionListener].
     *
     * @param positiveRes The String resource id for the positive button.
     * @param drawable The drawable for the button icon.
     * @param listener Listener that is invoked with the selected options when the positive button is clicked.
     */
    fun onPositiveMultiple(
        @StringRes positiveRes: Int,
        drawable: Drawable,
        listener: OptionsListener? = null
    ) {
        this.positiveText = windowContext.getString(positiveRes)
        this.positiveButtonDrawable = drawable
        this.listenerMultiple = listener
    }

    /**
     *  Set the text and icon of the positive button and set the [OptionListener].
     *
     * @param positiveText The text for the positive button.
     * @param drawable The drawable for the button icon.
     * @param listener Listener that is invoked with the selected options when the positive button is clicked.
     */
    fun onPositiveMultiple(
        positiveText: String,
        drawable: Drawable,
        listener: OptionsListener? = null
    ) {
        this.positiveText = positiveText
        this.positiveButtonDrawable = drawable
        this.listenerMultiple = listener
    }

    /**
     * Add multiple options.
     *
     * @param options The [Option] arguments to be added.
     */
    fun with(vararg options: Option) {
        this.options.addAll(options.toMutableList())
    }

    /**
     * Add multiple options as a MutableList
     *
     * @param options The [Option] arguments to be added.
     */
    fun with(options: MutableList<Option>) {
        this.options.addAll(options)
    }

    /**
     * Add an option.
     *
     * @param option Instance of [Option].
     */
    fun with(option: Option) {
        this.options.add(option)
    }

    /**
     * Add MenuItems from a Menu as options.
     *
     * @param menu Instance of [Menu].
     */
    fun with(menu: Menu) {
        val menuItemOptions = mutableListOf<Option>()
        var i = 0
        while (i < menu.size()) {
            val menuItem = menu.getItem(i)
            menuItem.title?.toString()?.let { title ->
                val option = menuItem.icon?.let { Option(it, title) } ?: Option(title)
                menuItemOptions.add(option)
            }

            i++
        }
        this.options.addAll(menuItemOptions)
    }

    private val adapterListener = object : OptionSelectionListener {

        override fun select(index: Int) {
            if (displayButtons) {
                optionsSelected.clear()
                optionsSelected.add(index)
                validate()
            } else {
                optionsSelected.add(index)
                Handler(Looper.getMainLooper()).postDelayed({
                    listener?.invoke(index, options[index])
                    dismiss()
                }, 300)
            }
        }

        override fun isSelected(index: Int): Boolean =
            optionsSelected.contains(index)

        override fun selectMultipleChoice(index: Int) {
            if (!optionsSelected.contains(index)) {
                optionsSelected.add(index)
                validate()
            }
        }

        override fun deselectMultipleChoice(index: Int) {
            optionsSelected.remove(index)
            validate()
        }

        override fun isMultipleChoiceSelectionAllowed(index: Int): Boolean =
            (multipleChoices && optionsSelected.contains(index) ||
                    (maxChoicesStrict && optionsSelected.size < maxChoices ?: options.size)
                    || (!maxChoicesStrict && optionsSelected.size < options.size))
    }

    override fun onCreateLayoutView(): View =
        SheetsOptionBinding.inflate(LayoutInflater.from(activity)).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkSetup()
        displayButtonsView(multipleChoices || displayButtons)
        setButtonPositiveListener(::save)
        colorActive = getPrimaryColor(requireContext())

        with(binding.optionsRecyclerView) {

            val columnsLimits = gridColumns ?: GRID_COLUMNS_MAX_DEFAULT
            val columns = if (options.size < columnsLimits) options.size else columnsLimits
            val collapsedItems = when (mode) {
                DisplayMode.GRID_HORIZONTAL -> options.size <= SMALL_GRID_ITEMS_MAX
                DisplayMode.GRID_VERTICAL -> true
                DisplayMode.LIST -> false
            }

            optionsAdapter =
                OptionAdapter(
                    requireContext(),
                    info,
                    options,
                    preventIconTint,
                    mode,
                    multipleChoices,
                    collapsedItems,
                    adapterListener
                )
            adapter = optionsAdapter

            setItemViewCacheSize(options.size)

            val spanLookUp = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == 0 && info != null) columns else DEFAULT_COLUMN_SPAN
                }
            }
            layoutManager = when (mode) {
                DisplayMode.GRID_HORIZONTAL -> if (collapsedItems) CustomGridLayoutManager(
                    requireContext(),
                    columns,
                    false
                ).apply { this.spanSizeLookup = spanLookUp }
                else CustomLinearLayoutManager(requireContext(), true, RecyclerView.HORIZONTAL)
                DisplayMode.GRID_VERTICAL -> CustomGridLayoutManager(
                    requireContext(),
                    columns,
                    true
                ).apply { this.spanSizeLookup = spanLookUp }
                DisplayMode.LIST -> CustomLinearLayoutManager(requireContext(), true)
            }
        }

        validate(true)
    }

    /** Validate if the current selections fulfils the requirements. */
    private fun validate(init: Boolean = false) {

        displayButtonPositive(saveAllowed, !init)

        if (multipleChoices) {
            updateMultipleChoicesInfo()
        }
    }

    /** Return the options and dismiss dialog. */
    private fun save() {

        if (multipleChoices) {
            val selectedOptions =
                options.filterIndexed { index, _ -> optionsSelected.contains(index) }
                    .toMutableList()
            listenerMultiple?.invoke(this, optionsSelected, selectedOptions)
        } else {
            optionsSelected.firstOrNull()?.let { index ->
                listener?.invoke(index, options[index])
            }
        }
        dismiss()
    }

    private fun checkSetup() {

        if (options.isEmpty())
            throw IllegalStateException("No options added.")

        checkChoicesSetup()
    }

    private fun checkChoicesSetup() {

        if (!multipleChoices) {

            if (options.any { it.selected && it.disabled }) {
                throw IllegalStateException("An option is already selected and can't be changed because it's disabled. ")
            }
        }

        if (minChoices != null && maxChoices != null) {
            binding.status.minimumLabel.visibility = View.GONE
            binding.status.selectionLabel.visibility = View.VISIBLE
        }

        if (options.all { it.disabled })
            throw IllegalStateException("All options are disabled.")

        minChoices?.let { min ->

            if (min >= options.size)
                throw IllegalStateException("Minimum amount of choices exceed the amount of options.")

            if (min >= options.filterNot { it.disabled && !it.selected }.size)
                throw IllegalStateException("Minimum amount of choices exceed the amount of enabled options.")
        }

        maxChoices?.let { max ->

            if (max > options.size)
                throw IllegalStateException("Maximum amount of choices exceed the amount of options.")

            if (max > options.filterNot { it.disabled && !it.selected }.size)
                throw IllegalStateException("Maximum amount of choices exceed the amount of enabled options.")
        }
    }

    private fun updateMultipleChoicesInfo() {

        if (!displayMultipleChoicesInfo) {
            binding.status.root.visibility = View.GONE
            return
        }

        binding.status.root.visibility = View.VISIBLE

        val selected = optionsSelected.size

        val isMinLabelShown = minChoices?.let { min ->
            val lessThanSelected = selected < min
            if (lessThanSelected) {
                binding.status.minimumLabel.text = getString(R.string.sheets_select_at_least, min)
                binding.status.minimumLabel.visibility = View.VISIBLE
            } else {
                binding.status.minimumLabel.visibility = View.GONE
            }
            lessThanSelected
        } ?: false

        maxChoices?.let { max ->
            if (!maxChoicesStrict && selected > max) {
                binding.status.minimumLabel.text = getString(R.string.sheets_select_at_most, max)
                binding.status.minimumLabel.visibility = View.VISIBLE
            } else if (!isMinLabelShown) {
                binding.status.minimumLabel.visibility = View.GONE
            }
        }

        val actualMaximum = maxChoices ?: options.filterNot { it.disabled && !it.selected }.size
        binding.status.selectionLabel.setTextColor(colorActive)
        val textSizeSmall = resources.getDimensionPixelSize(R.dimen.sheetsTextSizeBody)
        val textSpan =
            SpannableString(
                getString(
                    R.string.sheets_current_of_total,
                    selected,
                    actualMaximum
                )
            ).apply {
                setSpan(
                    AbsoluteSizeSpan(textSizeSmall), selected.toString().length, this.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

        binding.status.selectionLabel.text = textSpan
    }

    /** Build [OptionSheet] and show it later. */
    fun build(ctx: Context, width: Int? = null, func: OptionSheet.() -> Unit): OptionSheet {
        this.windowContext = ctx
        this.width = width
        this.func()
        return this
    }

    /** Build and show [OptionSheet] directly. */
    fun show(ctx: Context, width: Int? = null, func: OptionSheet.() -> Unit): OptionSheet {
        this.windowContext = ctx
        this.width = width
        this.func()
        this.show()
        return this
    }
}