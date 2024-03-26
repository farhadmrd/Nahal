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

package com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.duration

import android.content.Context
import android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.Spannable.SPAN_INCLUSIVE_INCLUSIVE
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView
import com.nahal.developer.family.nahal.R
import com.nahal.developer.family.nahal.databinding.SheetsDurationBinding
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.utils.getPrimaryColor
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.utils.getTextColor
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.utils.splitTime
import java.util.concurrent.TimeUnit

/** Internal listener which informs about the success of the time validation. */
internal typealias TimeValidationListener = (valid: Boolean) -> Unit

internal class TimeSelector(
    private val ctx: Context,
    private val binding: SheetsDurationBinding,
    private val format: DurationTimeFormat = DurationTimeFormat.M_SS,
    private val minTime: Long = Long.MIN_VALUE,
    private val maxTime: Long = Long.MAX_VALUE,
    private val validationListener: TimeValidationListener? = null
) {

    private val textColor = getTextColor(ctx)
    private val primaryColor = getPrimaryColor(ctx)

    private val time = StringBuffer("0")

    init {

        with(binding) {

            numericalInput.rightImageListener { onBackspace() }
            numericalInput.setRightImageDrawable(R.drawable.sheets_ic_backspace)

            numericalInput.leftImageListener { onClear() }
            numericalInput.setLeftImageDrawable(R.drawable.sheets_ic_clear)

            numericalInput.digitListener { onDigit(it) }

            timeValue.text = getFormattedTime()
        }

        validationListener?.invoke(false)
    }

    private fun onDigit(value: Int) {

        if (time.length >= format.length) time.deleteCharAt(0)
        time.append(value)
        binding.timeValue.text = getFormattedTime()
        validate()
    }

    private fun validate() {

        if (time.isNotEmpty()) {

            val timeInSeconds = getTimeInSeconds()

            when {
                timeInSeconds < minTime -> {
                    binding.hintLabel.visibility = View.VISIBLE
                    binding.hintLabel.text =
                        ctx.getString(
                            R.string.sheets_at_least_placeholder,
                            getFormattedHintTime(minTime * 1000)
                        )
                    validationListener?.invoke(false)
                }
                timeInSeconds > maxTime -> {
                    binding.hintLabel.visibility = View.VISIBLE
                    binding.hintLabel.text = ctx.getString(
                        R.string.sheets_at_most_placeholder,
                        getFormattedHintTime(maxTime * 1000)
                    )
                    validationListener?.invoke(false)
                }
                else -> {
                    binding.hintLabel.visibility = View.GONE
                    validationListener?.invoke(true)
                }
            }
        } else {
            binding.hintLabel.visibility = View.GONE
            validationListener?.invoke(false)
        }
    }

    /** Calculates the current time in seconds based on the input. */
    fun getTimeInSeconds(): Long {

        var timeInSeconds = 0L
        val reversedTime = time.reversed()
        val timeIntoFormat = StringBuilder(reversedTime)
        for (i in 2..timeIntoFormat.length step 3) {
            timeIntoFormat.insert(i, '_')
        }
        val timeReversedArray = timeIntoFormat.split("_")
        val formatReversedArray = format.name.reversed().split("_")
        formatReversedArray.forEachIndexed { i, formatTimeUnit ->
            if (i >= timeReversedArray.size) return@forEachIndexed
            val time = timeReversedArray[i]
            if (time.isEmpty()) return@forEachIndexed
            when {
                formatTimeUnit.contains("H", ignoreCase = true) -> {
                    timeInSeconds += TimeUnit.HOURS.toSeconds(time.reversed().toLong())
                }
                formatTimeUnit.contains("M", ignoreCase = true) -> {
                    timeInSeconds += TimeUnit.MINUTES.toSeconds(time.reversed().toLong())
                }
                formatTimeUnit.contains("S", ignoreCase = true) -> {
                    timeInSeconds += time.reversed().toInt()
                }
            }
        }

        return timeInSeconds
    }

    private fun onClear() {
        time.setLength(0)
        binding.timeValue.setText(getFormattedTime(), TextView.BufferType.SPANNABLE)
        validate()
    }

    private fun onBackspace() {

        if (time.isNotEmpty()) time.deleteCharAt(time.lastIndex)

        binding.timeValue.setText(getFormattedTime(), TextView.BufferType.SPANNABLE)
        validate()
    }

    private fun getFormattedTime(): SpannableStringBuilder {

        val smallTextSize = ctx.resources.getDimensionPixelSize(R.dimen.sheetsTextSizeSubheading)

        val formattedTime = SpannableStringBuilder(SpannableString(time))
        repeat(format.length.minus(time.length)) { formattedTime.insert(0, "0") }

        val formatArray = format.name.split("_")
        var insertIndex = 0
        formatArray.forEachIndexed { i, formatTimeUnit ->
            insertIndex += formatTimeUnit.length
            val space = if (formatArray.lastIndex != i) " " else ""
            val span = when {
                formatTimeUnit.contains("H", ignoreCase = true) ->
                    SpannableString(ctx.getString(R.string.sheets_hour_code).plus(space))
                formatTimeUnit.contains("M", ignoreCase = true) ->
                    SpannableString(ctx.getString(R.string.sheets_minute_code).plus(space))
                formatTimeUnit.contains("S", ignoreCase = true) ->
                    SpannableString(ctx.getString(R.string.sheets_second_code).plus(space))
                else -> SpannableString("")
            }
            span.setSpan(
                AbsoluteSizeSpan(smallTextSize),
                0, span.lastIndex.plus(1),
                SPAN_INCLUSIVE_INCLUSIVE
            )
            formattedTime.insert(insertIndex, span)
            formattedTime.setSpan(
                ForegroundColorSpan(primaryColor),
                insertIndex.minus(formatTimeUnit.length),
                insertIndex,
                SPAN_INCLUSIVE_INCLUSIVE
            )
            insertIndex += span.length
        }

        var index = formattedTime.indexOfFirst { it.isDigit() && it != '0' }
        if (index == -1) index = formattedTime.lastIndex.minus(1)

        formattedTime.setSpan(ForegroundColorSpan(textColor), 0, index, SPAN_EXCLUSIVE_EXCLUSIVE)

        formattedTime.setSpan(
            UnderlineSpan(),
            formattedTime.lastIndex.minus(1),
            formattedTime.lastIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return formattedTime
    }

    private fun getFormattedHintTime(timeInMillis: Long): SpannableString {

        val formattedTime = SpannableStringBuilder()

        if (timeInMillis > 0) {

            var millis = timeInMillis
            val days = TimeUnit.MILLISECONDS.toDays(millis).toInt()
            millis -= TimeUnit.DAYS.toMillis(days.toLong())
            val hours = TimeUnit.MILLISECONDS.toHours(millis).toInt()
            millis -= TimeUnit.HOURS.toMillis(hours.toLong())
            var minutes = TimeUnit.MILLISECONDS.toMinutes(millis).toInt()
            millis -= TimeUnit.MINUTES.toMillis(minutes.toLong())
            val seconds = TimeUnit.MILLISECONDS.toSeconds(millis).toInt()

            val small16dp =
                ctx.resources.getDimensionPixelSize(R.dimen.sheetsTextSizeSubheading)
            val big20dp = ctx.resources.getDimensionPixelSize(R.dimen.sheetsTextSizeTitle)

            if (days > 0) {
                formattedTime.append(days.toString(), AbsoluteSizeSpan(big20dp))
                formattedTime.append(
                    ctx.getString(R.string.sheets_day_code),
                    AbsoluteSizeSpan(small16dp)
                )
                formattedTime.append("  ")
            }

            if (hours > 0) {
                formattedTime.append(hours.toString(), AbsoluteSizeSpan(big20dp))
                formattedTime.append(ctx.getString(R.string.sheets_hour_code), AbsoluteSizeSpan(small16dp))
                formattedTime.append(" ")
            }

            if (minutes >= 0 || (hours == 0 || days == 0)) {
                if (seconds > 0) minutes = minutes.plus(1)
                formattedTime.append(minutes.toString(), AbsoluteSizeSpan(big20dp))
                formattedTime.append(
                    ctx.getString(R.string.sheets_minute_code),
                    AbsoluteSizeSpan(small16dp)
                )
                formattedTime.append(" ")
            }
        }

        return SpannableString(formattedTime)
    }

    private fun SpannableStringBuilder.append(text: CharSequence, span: Any) {
        val textLength = text.length
        append(text)
        setSpan(span, this.length - textLength, length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    }

    /** Set current time based on the seconds passed. */
    fun setTime(timeInSeconds: Long) {

        val (days, hours, minutes, seconds) = splitTime(timeInSeconds)

        // No support for days yet
        val filledTimeString = StringBuilder().apply {
            when(format) {
                DurationTimeFormat.HH_MM_SS -> {
                    append(hours.toString().padStart(2, '0'))
                    append(minutes.toString().padStart(2, '0'))
                    append(seconds.toString().padStart(2, '0'))
                }
                DurationTimeFormat.HH_MM -> {
                    append(hours.toString().padStart(2, '0'))
                    append(minutes.toString().padStart(2, '0'))
                }
                DurationTimeFormat.MM_SS -> {
                    append(minutes.toString().padStart(2, '0'))
                    append(seconds.toString().padStart(2, '0'))
                }
                DurationTimeFormat.M_SS -> {
                    append(minutes.toString().substring(0, minutes.toString().length.coerceAtMost(1)))
                    append(seconds.toString().padStart(2, '0'))
                }
                DurationTimeFormat.HH -> {
                    append(hours.toString().padStart(2, '0'))
                }
                DurationTimeFormat.MM -> {
                    append(minutes.toString().padStart(2, '0'))
                }
                DurationTimeFormat.SS -> {
                    append(seconds.toString().padStart(2, '0'))
                }
            }
        }

        time.setLength(0)
        filledTimeString.reversed().forEach {
            time.insert(0, it)
        }

        binding.timeValue.text = getFormattedTime()
        validate()
    }

}