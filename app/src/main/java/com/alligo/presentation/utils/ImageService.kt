package  com.alligo.presentation.utils

import android.view.View
import android.widget.ImageView
import coil.load
import com.alligo.R
import com.facebook.shimmer.ShimmerFrameLayout

object ImageService {

    fun setImage(imageView: ImageView,shimmerLayout: ShimmerFrameLayout ,
                 url: String){

        shimmerLayout.startShimmer()


        imageView.load(url) {
            crossfade(true)
//            placeholder(R.drawable.placeholder)
//            error(R.drawable.error)
            listener(
                onSuccess = { _, _ ->
                    // Stop shimmer when image is loaded
                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
                },
                onError = { _, _ ->
                    // Stop shimmer on error
                    shimmerLayout.stopShimmer()
                }
            )
        }
    }
}