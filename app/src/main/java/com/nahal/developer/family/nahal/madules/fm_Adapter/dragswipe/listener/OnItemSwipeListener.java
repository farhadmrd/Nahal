package com.nahal.developer.family.nahal.madules.fm_Adapter.dragswipe.listener;
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
import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public interface OnItemSwipeListener {
    /**
     * Called when the swipe action start.
     */
    void onItemSwipeStart(@Nullable RecyclerView.ViewHolder viewHolder, int bindingAdapterPosition);

    /**
     * Called when the swipe action is over.
     * If you change the view on the start, you should reset is here, no matter the item has swiped or not.
     *
     * @param bindingAdapterPosition If the view is swiped, pos will be negative.
     */
    void onItemSwipeEnd(@NonNull RecyclerView.ViewHolder viewHolder, int bindingAdapterPosition);

    /**
     * Called when item is swiped, the view is going to be removed from the adapter.
     */
    void onItemSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction, int bindingAdapterPosition);

    /**
     * Draw on the empty edge when swipe moving
     *
     * @param canvas            the empty edge's canvas
     * @param viewHolder        The ViewHolder which is being interacted by the User or it was
     *                          interacted and simply animating to its original position
     * @param dX                The amount of horizontal displacement caused by user's action
     * @param dY                The amount of vertical displacement caused by user's action
     * @param isCurrentlyActive True if this view is currently being controlled by the user or
     *                          false it is simply animating back to its original state.
     */
    void onItemSwipeMoving(@NonNull Canvas canvas, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive);
}
