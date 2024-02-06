package com.nahal.developer.family.nahal.madules.fm_BottomBar
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
import android.graphics.Rect
import android.os.Bundle
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.customview.widget.ExploreByTouchHelper

class AccessibleExploreByTouchHelper(
    private val host : SmoothBottomBar,
    private val bottomBarItems : List<BottomBarItem>,
    private val onClickAction : (id : Int) -> Unit
) : ExploreByTouchHelper(host) {

    override fun getVisibleVirtualViews(virtualViewIds: MutableList<Int>) {
        // defining simple ids for each item of the bottom bar
        for (i in bottomBarItems.indices) {
            virtualViewIds.add(i)
        }
    }

    override fun getVirtualViewAt(x: Float, y: Float): Int {
        val itemWidth = host.width / bottomBarItems.size
        return (x / itemWidth).toInt()
    }

    /**
     *  setBoundsInParent is required by [ExploreByTouchHelper]
     */
    @Suppress("DEPRECATION")
    override fun onPopulateNodeForVirtualView(
        virtualViewId: Int,
        node: AccessibilityNodeInfoCompat
    ) {
        node.className = BottomBarItem::class.simpleName
        node.contentDescription = bottomBarItems[virtualViewId].contentDescription
        node.isClickable = true
        node.isFocusable = true
        node.isScreenReaderFocusable = true

        node.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK)

        node.isSelected = host.itemActiveIndex == virtualViewId

        val bottomItemBoundRect = updateBoundsForBottomItem(virtualViewId)
        node.setBoundsInParent(bottomItemBoundRect)
    }

    override fun onPerformActionForVirtualView(
        virtualViewId: Int,
        action: Int,
        arguments: Bundle?
    ): Boolean {
        if (action == AccessibilityNodeInfoCompat.ACTION_CLICK) {
            onClickAction.invoke(virtualViewId)
            return true
        }
        return false
    }

    private fun updateBoundsForBottomItem(index: Int): Rect {
        val itemBounds = Rect()
        val itemWidth = host.width / bottomBarItems.size
        val left = index * itemWidth
        itemBounds.left = left
        itemBounds.top = 0
        itemBounds.right = (left + itemWidth)
        itemBounds.bottom = host.height
        return itemBounds
    }
}
