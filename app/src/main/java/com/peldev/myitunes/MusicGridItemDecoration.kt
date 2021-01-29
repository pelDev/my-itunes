package com.peldev.myitunes

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MusicGridItemDecoration(private val largePadding: Int, private val smallPadding: Int)
    : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = smallPadding
        outRect.right = largePadding
    }
}