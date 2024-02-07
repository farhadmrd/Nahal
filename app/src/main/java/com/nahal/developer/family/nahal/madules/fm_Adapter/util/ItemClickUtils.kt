package com.nahal.developer.family.nahal.madules.fm_Adapter.util
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
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.nahal.developer.family.nahal.madules.fm_Adapter.BaseQuickAdapter


private abstract class DebouncedClickListener<T : Any>(private val interval: Long) :
    BaseQuickAdapter.OnItemClickListener<T>, BaseQuickAdapter.OnItemChildClickListener<T> {
    private var mLastClickTime: Long = 0

    override fun onClick(adapter: BaseQuickAdapter<T, *>, view: View, position: Int) {
        val nowTime = System.currentTimeMillis()
        val diffTime = nowTime - mLastClickTime
        // 当用户修改系统时间时，可能会导致diffTime为负数
        if (diffTime >= interval || diffTime < 0) {
            mLastClickTime = nowTime
            onSingleClick(adapter, view, position)
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<T, *>, view: View, position: Int) {
        val nowTime = System.currentTimeMillis()
        val diffTime = nowTime - mLastClickTime
        // 当用户修改系统时间时，可能会导致diffTime为负数
        if (diffTime >= interval || diffTime < 0) {
            mLastClickTime = nowTime
            onSingleClick(adapter, view, position)
        }
    }

    protected abstract fun onSingleClick(adapter: BaseQuickAdapter<T, *>, view: View, position: Int)
}


/**
 * 去除点击抖动的点击方法
 *
 * @param time 间隔时间，单位：毫秒
 * @param block
 * @receiver
 */
fun <T : Any, VH : RecyclerView.ViewHolder> BaseQuickAdapter<T, VH>.setOnDebouncedItemClick(
    time: Long = 500,
    block: BaseQuickAdapter.OnItemClickListener<T>
) = this.setOnItemClickListener(object : DebouncedClickListener<T>(time) {
    override fun onSingleClick(adapter: BaseQuickAdapter<T, *>, view: View, position: Int) {
        block.onClick(adapter, view, position)
    }
})

/**
 * 去除 Child View 点击抖动的点击方法
 *
 * @param time 间隔时间，单位：毫秒
 * @param block
 * @receiver
 */
fun <T : Any, VH : RecyclerView.ViewHolder> BaseQuickAdapter<T, VH>.addOnDebouncedChildClick(
    @IdRes id: Int,
    time: Long = 500,
    block: BaseQuickAdapter.OnItemChildClickListener<T>
) = this.addOnItemChildClickListener(id, object : DebouncedClickListener<T>(time) {
    override fun onSingleClick(adapter: BaseQuickAdapter<T, *>, view: View, position: Int) {
        block.onItemClick(adapter, view, position)
    }
})
