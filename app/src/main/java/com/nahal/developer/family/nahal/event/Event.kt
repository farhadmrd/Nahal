package com.nahal.developer.family.nahal.event
/*
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
import android.content.Context
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import com.android.volley.VolleyError
import com.nahal.developer.family.nahal.R
import com.nahal.developer.family.nahal.core.UBase
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.color.ColorSheet
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.SheetStyle
import com.nahal.developer.family.nahal.madules.fm_Toaster.FMToaster

open class Event : UBase() {
    var app: Event? = null
    override fun onCreate() {
        super.onCreate()
        app = this
    }

    companion object {
        private val ShowErrorForTesterIsOn: Boolean = false

        open fun ShowErrorForTester(err: Exception, className: String) {
            try {
                if (ShowErrorForTesterIsOn) {
                    FMToaster.Builder(getContext()!!)
                        .setDescription(err.message)
                        .setDuration(FMToaster.LENGTH_LONG)
                        .setTitle("خطا")
                        .setStatus(FMToaster.Status.SUCCESS).show()
                }
            } catch (e: Exception) {
            }
        }

        open fun ShowErrorForTester(err: VolleyError, className: String) {
            try {
                if (ShowErrorForTesterIsOn) {
                    FMToaster.Builder(getContext()!!)
                        .setDescription(err.message)
                        .setDuration(FMToaster.LENGTH_LONG)
                        .setTitle("خطا")
                        .setStatus(FMToaster.Status.SUCCESS).show()
                }
            } catch (e: Exception) {
            }
        }

        open fun getCurrentUserId(): Long {
            return try {
                0
            } catch (e: java.lang.Exception) {
                ShowErrorForTester(e, "Event")
                0
            }
        }

        open fun ConnectivityManager?.isCurrentlyConnected() = when (this) {
            null -> false
            else ->
                activeNetwork
                    ?.let(::getNetworkCapabilities)
                    ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    ?: false
        }

       open fun showColorSheet(context : Context, view: View?) {
            ColorSheet().show(context) { // Build and show
                style(SheetStyle.BOTTOM_SHEET)

                title("رنگ پس زمینه")
                defaultView(com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.color.ColorView.TEMPLATE) // Set the default view when the sheet is visible
                // disableSwitchColorView() Disable switching between template and custom color view
                onPositive("انتخاب") { color ->
                    // Use Color
                    view?.setBackgroundColor(color)
                }
                onNegative("لغو") {

                }
            }
        }
    }

}

