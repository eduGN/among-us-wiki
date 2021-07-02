package com.example.amonguswiki.navigator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemDecorationColumns(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        val manager = parent.layoutManager as? GridLayoutManager
        val spanCount = manager?.spanCount ?: 0

        if (position < spanCount) {
            outRect.top = space
        }

        if (position % 2 != 0) {
            outRect.right = space
        }
        outRect.left = space
        outRect.bottom = space
    }

}