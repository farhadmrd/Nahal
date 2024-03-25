package com.nahal.developer.family.nahal.helper
/*
 * Copyright (c) 2020 farhad moradi
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
import com.nahal.developer.family.nahal.madules.fm_PersianDate.PersianDate
import com.nahal.developer.family.nahal.madules.fm_PersianDate.PersianDateFormat
import java.nio.charset.StandardCharsets
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Calendar
import java.util.Random

class Convert {
    private fun getRandomNumber(min: Int, max: Int): Int {
        return Random().nextInt(max - min + 1) + min
    }

    companion object {
        private val formater: PersianDateFormat = PersianDateFormat("l j F Y")
        fun GetPersianToday(): String? {
            val date = PersianDate()
            return getCharacterFromUnicode(formater.format(date))
        }

        fun getCharacterFromUnicode(s: String): String? {
            var returnString: String? = null
            try {
                s.toByteArray(StandardCharsets.UTF_8)
                returnString = String()
            } catch (ignored: Exception) {
            }
            return returnString
        }

        fun EnToFa(num: String): String {
            return num
                .replace("0", "۰")
                .replace("1", "۱")
                .replace("2", "۲")
                .replace("3", "۳")
                .replace("4", "۴")
                .replace("5", "۵")
                .replace("6", "۶")
                .replace("7", "۷")
                .replace("8", "۸")
                .replace("9", "۹")
        }

        fun FaToEn(num: String): String {
            return num
                .replace("۰", "0")
                .replace("۱", "1")
                .replace("۲", "2")
                .replace("۳", "3")
                .replace("۴", "4")
                .replace("۵", "5")
                .replace("۶", "6")
                .replace("۷", "7")
                .replace("۸", "8")
                .replace("۹", "9")
        }

        fun convertDateToNumber(date: String): Int {
            val numbers =
                date.split("/".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
            val years = numbers[0].toInt()
            val month = numbers[1].toInt()
            val day = numbers[2].toInt()
            return years * 10000 + month * 100 + day
        }

        fun convertSplitMoney(money: Int): String {
            val formatter: NumberFormat = DecimalFormat("#,###,###,###,###")
            return formatter.format(money.toLong()) + ""
        }

        fun convertSplitMoney(money: Long): String {
            val formatter: NumberFormat = DecimalFormat("#,###,###,###,###")
            return formatter.format(money) + ""
        }

        fun convertSplitMoney(money: Float): String {
            val formatter: NumberFormat = DecimalFormat("#,###,###,###,###")
            return formatter.format(money.toDouble()) + ""
        }

        fun convertSplitMoney(money: String): String {
            return String.format(money, "#,###,###,###,###")
        }

        val startOfDayInMillis: Long
            get() {
                val calendar = Calendar.getInstance()
                calendar[Calendar.HOUR_OF_DAY] = 0
                calendar[Calendar.MINUTE] = 0
                calendar[Calendar.SECOND] = 0
                calendar[Calendar.MILLISECOND] = 0
                return calendar.timeInMillis
            }
        val endOfDayInMillis: Long
            get() =// Add one day's time to the beginning of the day.
                // 24 hours * 60 minutes * 60 seconds * 1000 milliseconds = 1 day
                startOfDayInMillis + 24 * 60 * 60 * 1000
    }
}