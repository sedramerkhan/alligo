package com.alligo.presentation.utils

import android.os.Handler
import android.os.Looper
import android.view.View
import com.alligo.R
import com.google.android.material.snackbar.Snackbar

object SnackbarService {
     fun showWithAction(
        view: View,
        message: String,
        onUndoClicked: () -> Unit,

        onCompleteAction: () -> Unit
    ) {
        var isUndoPressed = false
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)

        // Add an undo action button
        snackbar.setAction(view.context.getString(R.string.undo)) {
            isUndoPressed = true
            onUndoClicked.invoke()
        }

        // Customize Snackbar appearance 
        snackbar.setActionTextColor(view.context.getColor(R.color.colorSecondaryVariant))
        snackbar.setBackgroundTint(view.context.getColor(R.color.surfaceColor))
        snackbar.setTextColor(view.context.getColor(R.color.onSurface))
      
        snackbar.show()

        // Wait for the duration of the Snackbar (Snackbar.LENGTH_LONG duration is 3500 ms)
        Handler(Looper.getMainLooper()).postDelayed({
            // Check if Undo wasn't pressed to confirm action 
            if (!isUndoPressed) {
                onCompleteAction.invoke()  
            }
        }, 3500)
    }
}