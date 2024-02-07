package com.nahal.developer.family.nahal.madules.fm_Adapter.viewholder;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A viewHolder quick to use when the project enabled ViewDataBinding.
 * ViewDataBinding 快速使用的 ViewHolder
 * @param <DB> ViewDataBinding
 */
public class DataBindingHolder<DB extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private final DB binding;

    public DataBindingHolder(DB binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public DataBindingHolder(@NonNull View itemView) {
        super(itemView);
        this.binding = DataBindingUtil.bind(itemView);

        if (this.binding == null)
            throw new NullPointerException("DataBinding is Null. Please check Layout resource or ItemView");
    }

    public DataBindingHolder(@LayoutRes int resId, @NonNull ViewGroup parent) {
        this(LayoutInflater.from(parent.getContext()).inflate(resId, parent, false));
    }

    @NonNull
    public DB getBinding() {
        return binding;
    }
}
