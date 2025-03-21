package com.alligo.presentation.cart.components

import android.content.Context
import android.graphics.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.alligo.R

object SwipeHandler {

    // This to delete cart item when swiping Left
    fun simpleItemTouchCallback(
        context: Context,
        canSwipe: (Int) -> Boolean,  // Function to check if item is deletable
        onDelete: (Int) -> Unit
    ): ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            private val paint = Paint()
            private val icon = ContextCompat.getDrawable(context, R.drawable.ic_delete)
            private val iconMargin = 40
            private val backgroundColor = ContextCompat.getColor(context, R.color.backgroundColor)

            override fun getSwipeDirs(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val position = viewHolder.absoluteAdapterPosition
                return if (position != RecyclerView.NO_POSITION && canSwipe(position)) {
                    super.getSwipeDirs(recyclerView, viewHolder) // Allow swipe
                } else {
                    0 // Disable swipe
                }
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION && canSwipe(position)) {
                    onDelete(position)
                } else {
                    // Reset the swipe if the item cannot be deleted
                    notifyItemChanged(viewHolder)
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val itemHeight = itemView.bottom - itemView.top

                if (dX < 0) {  // Only draw if swiping left
                    paint.color = backgroundColor
                    val background = RectF(
                        itemView.right.toFloat() + dX, itemView.top.toFloat(),
                        itemView.right.toFloat(), itemView.bottom.toFloat()
                    )
                    c.drawRect(background, paint)

                    // Draw delete icon
                    icon?.let {
                        val iconSize = itemHeight / 3
                        val iconLeft = itemView.right - iconMargin - iconSize
                        val iconTop = itemView.top + (itemHeight - iconSize) / 2
                        val iconRight = itemView.right - iconMargin
                        val iconBottom = iconTop + iconSize
                        it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        it.draw(c)
                    }
                }

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

            private fun notifyItemChanged(viewHolder: RecyclerView.ViewHolder) {
                viewHolder.itemView.post {
                    viewHolder.bindingAdapter?.notifyItemChanged(viewHolder.absoluteAdapterPosition)
                }
            }
        }
}
