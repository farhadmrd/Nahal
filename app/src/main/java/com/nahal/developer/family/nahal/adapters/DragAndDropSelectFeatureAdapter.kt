package com.nahal.developer.family.nahal.adapters

import android.annotation.SuppressLint
import android.content.ClipData
import android.os.Build
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nahal.developer.family.nahal.R
import com.nahal.developer.family.nahal.madules.fm_DrogableRecyclerView.DragListener
import com.nahal.developer.family.nahal.madules.fm_DrogableRecyclerView.Listener


class DragAndDropSelectFeatureAdapter(
    var list: MutableList<String>,
    private val listener: Listener?
) : RecyclerView.Adapter<DragAndDropSelectFeatureAdapter.ViewHolder>(), OnTouchListener {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_main, parent, false)

        return ViewHolder(view)

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            // TODO
            tvGrid.text = list[position]
            cvGrid.tag = position
            cvGrid.setOnTouchListener(this@DragAndDropSelectFeatureAdapter)
            cvGrid.setOnDragListener(DragListener(listener))
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val data = ClipData.newPlainText("", "")
            val shadowBuilder = DragShadowBuilder(view)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                view.startDragAndDrop(data, shadowBuilder, view, 0)
            } else {
                view.startDrag(data, shadowBuilder, view, 0)
            }
            return true
        }
        return false
    }

    fun updateList(list: MutableList<String>) {
        this.list = list
    }

    val dragInstance: DragListener?
        get() = if (listener != null) {
            DragListener(listener)
        } else {
            null
        }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvGrid: TextView
        var cvGrid: CardView

        init {
            // Define click listener for the ViewHolder's View
            tvGrid = view.findViewById(R.id.tvGrid)
            cvGrid = view.findViewById(R.id.cvGrid)
        }
    }

}