package com.nahal.developer.family.nahal.madules.fm_DrogableRecyclerView

import android.view.DragEvent
import android.view.View
import android.view.View.OnDragListener
import androidx.recyclerview.widget.RecyclerView
import com.nahal.developer.family.nahal.adapters.DragAndDropSelectFeatureAdapter
import com.nahal.developer.family.nahal.R

class DragListener(private val listener: Listener?) : OnDragListener {
    private var isDropped = false
    override fun onDrag(view: View, event: DragEvent): Boolean {
        if (event.action == DragEvent.ACTION_DROP) {
            isDropped = true
            var positionTarget = -1
            val viewSource = event.localState as View
            val viewId = view.id
            val cvItem = R.id.cvGrid
            val tvEmptyListTop = R.id.tvEmptyListTop
            val tvEmptyListBottom = R.id.tvEmptyListBottom
            val rvTop = R.id.rvTop
            val rvBottom = R.id.rvBottom
            when (viewId) {
                cvItem, tvEmptyListTop, tvEmptyListBottom, rvTop, rvBottom -> {
                    val target: RecyclerView
                    when (viewId) {
                        tvEmptyListTop, rvTop -> target =
                            view.rootView.findViewById<View>(rvTop) as RecyclerView

                        tvEmptyListBottom, rvBottom -> target =
                            view.rootView.findViewById<View>(rvBottom) as RecyclerView

                        else -> {
                            target = view.parent as RecyclerView
                            positionTarget = view.tag as Int
                        }
                    }
                    if (viewSource != null) {
                        val source = viewSource.parent as RecyclerView
                        val adapterSource = source.adapter as DragAndDropSelectFeatureAdapter?
                        val positionSource = viewSource.tag as Int
                        val sourceId = source.id
                        val list = adapterSource!!.list[positionSource]
                        val listSource = adapterSource.list
                        listSource.removeAt(positionSource)
                        adapterSource.updateList(listSource)
                        adapterSource.notifyDataSetChanged()
                        val adapterTarget = target.adapter as DragAndDropSelectFeatureAdapter?
                        val customListTarget = adapterTarget!!.list
                        if (positionTarget >= 0) {
                            customListTarget.add(positionTarget, list)
                        } else {
                            customListTarget.add(list)
                        }
                        adapterTarget.updateList(customListTarget)
                        adapterTarget.notifyDataSetChanged()
                        if (sourceId == rvBottom && adapterSource.itemCount < 1) {
                            listener?.setEmptyListBottom(true)
                        }
                        if (viewId == tvEmptyListBottom) {
                            listener?.setEmptyListBottom(false)
                        }
                        if (sourceId == rvTop && adapterSource.itemCount < 1) {
                            listener?.setEmptyListTop(true)
                        }
                        if (viewId == tvEmptyListTop) {
                            listener?.setEmptyListTop(false)
                        }
                    }
                }
            }
        }
        if (!isDropped && event.localState != null) {
            (event.localState as View).visibility = View.VISIBLE
        }
        return true
    }
}