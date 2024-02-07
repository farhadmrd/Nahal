package com.nahal.developer.family.nahal.madules.fm_Adapter.layoutmanager
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
import android.content.Context
import android.util.AttributeSet
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nahal.developer.family.nahal.madules.fm_Adapter.BaseQuickAdapter
import com.nahal.developer.family.nahal.madules.fm_Adapter.fullspan.FullSpanAdapterType

/**
 * grid layout manager.
 * Used to achieve full span. Adapter needs to implement [FullSpanAdapterType] interface
 *
 * 网格布局 GridLayoutManager，用于实现满跨度，Adapter 需要实现 [FullSpanAdapterType] 接口
 *
 */
open class QuickGridLayoutManager : GridLayoutManager {

    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    constructor(context: Context, spanCount: Int) : super(context, spanCount)

    constructor(
        context: Context, spanCount: Int,
        @RecyclerView.Orientation orientation: Int, reverseLayout: Boolean
    ) : super(context, spanCount, orientation, reverseLayout)

    private val fullSpanSizeLookup = FullSpanSizeLookup()

    private var adapter: RecyclerView.Adapter<*>? = null

    init {
        fullSpanSizeLookup.originalSpanSizeLookup = spanSizeLookup
        super.setSpanSizeLookup(fullSpanSizeLookup)
    }

    @CallSuper
    override fun onAdapterChanged(
        oldAdapter: RecyclerView.Adapter<*>?, newAdapter: RecyclerView.Adapter<*>?
    ) {
        adapter = newAdapter
    }

    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        adapter = view?.adapter
    }

    override fun onDetachedFromWindow(view: RecyclerView?, recycler: RecyclerView.Recycler?) {
        super.onDetachedFromWindow(view, recycler)
        adapter = null
    }

    override fun setSpanSizeLookup(spanSizeLookup: SpanSizeLookup?) {
        fullSpanSizeLookup.originalSpanSizeLookup = spanSizeLookup
    }

    /**
     * 处理全部跨度item的情况
     */
    private inner class FullSpanSizeLookup : SpanSizeLookup() {

        var originalSpanSizeLookup: SpanSizeLookup? = null

        override fun getSpanSize(position: Int): Int {
            val adapter = adapter ?: return 1

            if (adapter is ConcatAdapter) {
                val pair = adapter.getWrappedAdapterAndPosition(position)

                return when (val wrappedAdapter = pair.first) {
                    is FullSpanAdapterType -> {
                        spanCount
                    }
                    is BaseQuickAdapter<*, *> -> {
                        val type = wrappedAdapter.getItemViewType(pair.second)

                        if (wrappedAdapter.isFullSpanItem(type)) {
                            spanCount
                        } else {
                            originalSpanSizeLookup?.getSpanSize(position) ?: 1
                        }
                    }
                    else -> originalSpanSizeLookup?.getSpanSize(position) ?: 1
                }
            } else {
                return when (adapter) {
                    is FullSpanAdapterType -> {
                        spanCount
                    }
                    is BaseQuickAdapter<*, *> -> {
                        val type = adapter.getItemViewType(position)

                        if (adapter.isFullSpanItem(type)) {
                            spanCount
                        } else {
                            originalSpanSizeLookup?.getSpanSize(position) ?: 1
                        }
                    }
                    else -> originalSpanSizeLookup?.getSpanSize(position) ?: 1
                }
            }
        }
    }
}