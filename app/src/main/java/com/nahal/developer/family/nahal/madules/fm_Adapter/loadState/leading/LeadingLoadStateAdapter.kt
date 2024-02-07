package com.nahal.developer.family.nahal.madules.fm_Adapter.loadState.leading
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
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import com.nahal.developer.family.nahal.madules.fm_Adapter.loadState.LoadState
import com.nahal.developer.family.nahal.madules.fm_Adapter.loadState.LoadStateAdapter

/**
 * Leading load state adapter
 * 首部的加载状态适配器
 */
abstract class LeadingLoadStateAdapter<VH: RecyclerView.ViewHolder> : LoadStateAdapter<VH>() {

    /**
     * A listener for loading more.
     * 加载更多的监听事件
     */
    var onLeadingListener: OnLeadingListener? = null
        private set

    /**
     * Whether enable loading.
     * 是否开启加载功能
     */
    var isLoadEnable = true

    /**
     * Preload, the number of distances from the first item.
     *
     * 预加载，距离首 item 的个数
     */
    var preloadSize = 0

    private var mDelayNextLoadFlag: Boolean = false

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return loadState is LoadState.Loading
    }

    @CallSuper
    override fun onViewAttachedToWindow(holder: VH) {
        loadAction()
    }

    /**
     * Action of loading more.
     * 加载更多执行的操作
     */
    private fun loadAction() {
        if (!isLoadEnable || onLeadingListener?.isAllowLoading() == false) return

        if (mDelayNextLoadFlag) return

        if (loadState is LoadState.NotLoading && !loadState.endOfPaginationReached) {
            val recyclerView = recyclerView ?: return

            if (recyclerView.isComputingLayout) {
                // 如果 RecyclerView 当前正在计算布局，则延迟执行，避免崩溃
                // To avoid crash. Delay to load more if the recyclerview is computingLayout.
                mDelayNextLoadFlag = true
                recyclerView.post {
                    mDelayNextLoadFlag = false
                    invokeLoad()
                }
                return
            }
            invokeLoad()
        }
    }

    internal fun checkPreload(currentPosition: Int) {
        if (currentPosition < 0) return

        if (currentPosition <= preloadSize) {
            loadAction()
        }
    }

    fun invokeLoad() {
        loadState = LoadState.Loading
        onLeadingListener?.onLoad()
    }


    fun setOnLeadingListener(listener: OnLeadingListener?) = apply {
        this.onLeadingListener = listener
    }

    override fun toString(): String {
        return """
            LeadingLoadStateAdapter ->
            [isLoadEnable: $isLoadEnable],
            [preloadSize: $preloadSize],
            [loadState: $loadState]
        """.trimIndent()
    }

    interface OnLeadingListener {

        /**
         * Executing loading.
         * "加载更多"执行逻辑
         */
        fun onLoad()

        /**
         * Whether to allow loading.
         * 是否允许进行加载
         */
        fun isAllowLoading(): Boolean = true
    }
}