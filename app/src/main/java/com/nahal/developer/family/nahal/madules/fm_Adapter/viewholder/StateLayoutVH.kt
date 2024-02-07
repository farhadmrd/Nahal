package com.nahal.developer.family.nahal.madules.fm_Adapter.viewholder
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
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.nahal.developer.family.nahal.madules.fm_Adapter.fullspan.FullSpanAdapterType

/**
 * An emptyState viewHolder. (For internal use only)
 * 内部使用的空状态ViewHolder
 *
 * @property stateLayout
 * @constructor Create empty Empty layout v h
 */
internal class StateLayoutVH(
    parent: ViewGroup,
    stateView: View?,
    private val stateLayout: FrameLayout = FrameLayout(parent.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        setStateView(this, stateView)
    }
) : RecyclerView.ViewHolder(stateLayout), FullSpanAdapterType {


    fun changeStateView(stateView: View?) {
        setStateView(stateLayout, stateView)
    }

    companion object {
        private fun setStateView(rootView: ViewGroup, stateView: View?) {
            if (stateView == null) {
                rootView.removeAllViews()
                return
            }

            if (rootView.childCount == 1) {
                val old = rootView.getChildAt(0)
                if (old == stateView) {
                    // 如果是同一个view，不进行操作
                    return
                }
            }

            stateView.parent.run {
                if (this is ViewGroup) {
                    this.removeView(stateView)
                }
            }

            if (stateView.layoutParams == null) {
                stateView.layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.CENTER
                }
            }

            rootView.removeAllViews()
            rootView.addView(stateView)
        }
    }
}
