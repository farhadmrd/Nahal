package com.nahal.developer.family.nahal.madules.fm_Adapter.dragswipe
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
import android.graphics.Canvas
import androidx.recyclerview.widget.RecyclerView
import com.nahal.developer.family.nahal.madules.fm_Adapter.dragswipe.listener.OnItemDragListener
import com.nahal.developer.family.nahal.madules.fm_Adapter.dragswipe.listener.OnItemSwipeListener

/**
 * 使用拖拽方式的拓展函数
 */
inline fun QuickDragAndSwipe.setItemDragListener(
    crossinline onItemDragStart: ((viewHolder: RecyclerView.ViewHolder?, pos: Int) -> Unit) = { _, _ -> },
    crossinline onItemDragMoving: ((
        source: RecyclerView.ViewHolder,
        from: Int,
        target: RecyclerView.ViewHolder,
        to: Int
    ) -> Unit) = { _, _, _, _ -> },
    crossinline onItemDragEnd: ((viewHolder: RecyclerView.ViewHolder, pos: Int) -> Unit) = { _, _ -> },

    ) = apply {
    val listener = object :
        OnItemDragListener {
        override fun onItemDragStart(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
            onItemDragStart.invoke(viewHolder, pos)
        }

        override fun onItemDragMoving(
            source: RecyclerView.ViewHolder,
            from: Int,
            target: RecyclerView.ViewHolder,
            to: Int
        ) {
            onItemDragMoving.invoke(source, from, target, to)
        }

        override fun onItemDragEnd(viewHolder: RecyclerView.ViewHolder, pos: Int) {
            onItemDragEnd.invoke(viewHolder, pos)
        }
    }
    this.setItemDragListener(listener)
}

/**
 * 滑动删除的拓展函数
 */
inline fun QuickDragAndSwipe.setItemSwipeListener(
    crossinline onItemSwipeStart: ((viewHolder: RecyclerView.ViewHolder?, bindingAdapterPosition: Int) -> Unit) = { _, _ -> },
    crossinline onItemSwipeMoving: ((
        canvas: Canvas,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        isCurrentlyActive: Boolean
    ) -> Unit) = { _, _, _, _, _ -> },
    crossinline onItemSwiped: ((viewHolder: RecyclerView.ViewHolder, direction: Int, bindingAdapterPosition: Int) -> Unit) = { _, _, _ -> },
    crossinline onItemSwipeEnd: ((viewHolder: RecyclerView.ViewHolder, bindingAdapterPosition: Int) -> Unit) = { _, _ -> }
) = apply {
    val listener = object :
        OnItemSwipeListener {
        override fun onItemSwipeStart(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
            onItemSwipeStart.invoke(viewHolder, pos)
        }

        override fun onItemSwipeMoving(
            canvas: Canvas,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            isCurrentlyActive: Boolean
        ) {
            onItemSwipeMoving.invoke(canvas, viewHolder, dX, dY, isCurrentlyActive)
        }

        override fun onItemSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, bindingAdapterPosition: Int) {
            onItemSwiped.invoke(viewHolder, direction, bindingAdapterPosition)
        }

        override fun onItemSwipeEnd(viewHolder: RecyclerView.ViewHolder, pos: Int) {
            onItemSwipeEnd.invoke(viewHolder, pos)
        }
    }
    this.setItemSwipeListener(listener)
}
