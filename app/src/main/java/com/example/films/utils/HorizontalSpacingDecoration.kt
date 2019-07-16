package com.example.films.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalSpacingDecoration(private val paddingPixels: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val size = parent.adapter!!.itemCount
        outRect.left = if (position == 0) paddingPixels else paddingPixels / 2
        outRect.right = if (position + 1 == size) paddingPixels else paddingPixels / 2
    }
}
