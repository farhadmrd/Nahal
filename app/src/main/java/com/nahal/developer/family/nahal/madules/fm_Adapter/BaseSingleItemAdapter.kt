package com.nahal.developer.family.nahal.madules.fm_Adapter
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
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for single item
 * 只有单个/一个 item 情况下的 Adapter
 *
 * @param T 数据类型 type of data
 * @param VH viewHolder类型 type of the viewHolder
 * @property mItem 数据 data
 */
abstract class BaseSingleItemAdapter<T : Any, VH : RecyclerView.ViewHolder>(private var mItem: T? = null) :
    BaseQuickAdapter<Any, VH>() {

    protected abstract fun onBindViewHolder(holder: VH, item: T?)

    open fun onBindViewHolder(holder: VH, item: T?, payloads: List<Any>) {
        onBindViewHolder(holder, item)
    }

    final override fun onBindViewHolder(holder: VH, position: Int, item: Any?) {
        onBindViewHolder(holder, mItem)
    }

    final override fun onBindViewHolder(holder: VH, position: Int, item: Any?, payloads: List<Any>) {
        onBindViewHolder(holder, mItem, payloads)
    }

    final override fun getItemCount(items: List<Any>): Int {
        return 1
    }

    /**
     * 设置 item 数据（payload 方式）
     *
     * @param t
     * @param payload
     */
    fun setItem(t: T?, payload: Any?) {
        mItem = t
        notifyItemChanged(0, payload)
    }

    /**
     * 获取/设置 item 数据
     */
    var item: T?
        get() = mItem
        set(value) {
            mItem = value
            notifyItemChanged(0)
        }

    override fun submitList(list: List<Any>?) {
        throw RuntimeException("Please use setItem()")
    }

    override fun add(data: Any) {
        throw RuntimeException("Please use setItem()")
    }

    override fun add(position: Int, data: Any) {
        throw RuntimeException("Please use setItem()")
    }

    override fun addAll(collection: Collection<Any>) {
        throw RuntimeException("Please use setItem()")
    }

    override fun addAll(position: Int, collection: Collection<Any>) {
        throw RuntimeException("Please use setItem()")
    }

    override fun remove(data: Any) {
        throw RuntimeException("Please use setItem()")
    }

    override fun removeAtRange(range: IntRange) {
        throw RuntimeException("Please use setItem()")
    }

    override fun removeAt(position: Int) {
        throw RuntimeException("Please use setItem()")
    }

    override fun set(position: Int, data: Any) {
        throw RuntimeException("Please use setItem()")
    }
}