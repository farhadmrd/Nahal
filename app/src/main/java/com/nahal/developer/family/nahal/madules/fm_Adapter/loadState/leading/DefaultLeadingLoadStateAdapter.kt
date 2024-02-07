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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nahal.developer.family.nahal.R
import com.nahal.developer.family.nahal.madules.fm_Adapter.loadState.LoadState

/**
 * Default leading load state adapter
 *
 * 默认实现的尾部"向上加载更多" Adapter
 */
internal class DefaultLeadingLoadStateAdapter :
    LeadingLoadStateAdapter<DefaultLeadingLoadStateAdapter.LeadingLoadStateVH>() {

    /**
     * Default ViewHolder
     *
     * 默认实现的 ViewHolder
     */
    internal class LeadingLoadStateVH(
        parent: ViewGroup,
        view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.brvah_leading_load_more, parent, false)
    ) : RecyclerView.ViewHolder(view) {
        val loadingProgress: View = itemView.findViewById(R.id.loading_progress)
    }


    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LeadingLoadStateVH {
        return LeadingLoadStateVH(parent)
    }

    override fun onBindViewHolder(holder: LeadingLoadStateVH, loadState: LoadState) {
        if (loadState is LoadState.Loading) {
            holder.loadingProgress.visibility = View.VISIBLE
        } else {
            holder.loadingProgress.visibility = View.GONE
        }
    }

    override fun getStateViewType(loadState: LoadState): Int = R.layout.brvah_leading_load_more
}