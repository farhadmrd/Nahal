package com.nahal.developer.family.nahal.madules.fm_Adapter.loadState.trailing
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
 * Default trailing load state adapter
 *
 * 默认实现的尾部"加载更多" Adapter
 */
internal class DefaultTrailingLoadStateAdapter(isLoadEndDisplay: Boolean = true): TrailingLoadStateAdapter<DefaultTrailingLoadStateAdapter.TrailingLoadStateVH>(isLoadEndDisplay) {

    /**
     * Default implementation of "load more" ViewHolder
     *
     * 默认实现的"加载更多" ViewHolder
     */
    internal class TrailingLoadStateVH(
        parent: ViewGroup,
        view: View = LayoutInflater.from(parent.context).inflate(R.layout.brvah_trailing_load_more, parent, false)
    ) : RecyclerView.ViewHolder(view) {
        val loadCompleteView: View = itemView.findViewById(R.id.load_more_load_complete_view)
        val loadingView: View = itemView.findViewById(R.id.load_more_loading_view)
        val loadFailView: View = itemView.findViewById(R.id.load_more_load_fail_view)
        val loadEndView: View = itemView.findViewById(R.id.load_more_load_end_view)
    }

    override fun onCreateViewHolder( parent: ViewGroup, loadState: LoadState): TrailingLoadStateVH {
        return TrailingLoadStateVH(parent).apply {
            loadFailView.setOnClickListener {
                // 失败重试点击事件
                // retry when loaded failed.
                invokeFailRetry()
            }
            loadCompleteView.setOnClickListener {
                // 加载更多，手动点击事件
                // manual click to load more.
                invokeLoadMore()
            }
        }
    }

    /**
     * bind LoadState
     *
     * 绑定加载状态
     */
    override fun onBindViewHolder(holder: TrailingLoadStateVH, loadState: LoadState) {
        when (loadState) {
            is LoadState.NotLoading -> {
                if (loadState.endOfPaginationReached) {
                    holder.loadCompleteView.visibility = View.GONE
                    holder.loadingView.visibility = View.GONE
                    holder.loadFailView.visibility = View.GONE
                    holder.loadEndView.visibility = View.VISIBLE
                } else {
                    holder.loadCompleteView.visibility = View.VISIBLE
                    holder.loadingView.visibility = View.GONE
                    holder.loadFailView.visibility = View.GONE
                    holder.loadEndView.visibility = View.GONE
                }
            }
            is LoadState.Loading -> {
                holder.loadCompleteView.visibility = View.GONE
                holder.loadingView.visibility = View.VISIBLE
                holder.loadFailView.visibility = View.GONE
                holder.loadEndView.visibility = View.GONE
            }
            is LoadState.Error -> {
                holder.loadCompleteView.visibility = View.GONE
                holder.loadingView.visibility = View.GONE
                holder.loadFailView.visibility = View.VISIBLE
                holder.loadEndView.visibility = View.GONE
            }
            is LoadState.None -> {
                holder.loadCompleteView.visibility = View.GONE
                holder.loadingView.visibility = View.GONE
                holder.loadFailView.visibility = View.GONE
                holder.loadEndView.visibility = View.GONE
            }
        }
    }

    override fun getStateViewType(loadState: LoadState): Int = R.layout.brvah_trailing_load_more
}