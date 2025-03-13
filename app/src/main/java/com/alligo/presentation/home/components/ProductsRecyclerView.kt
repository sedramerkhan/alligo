package  com.alligo.presentation.home.components

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alligo.R
import com.alligo.databinding.ItemProductBinding
import com.alligo.model.product.Product
import com.alligo.presentation.utils.ImageService
import com.alligo.presentation.utils.extentions.formatPrice
import com.alligo.presentation.utils.extentions.formatToEnglish


class GridAdapter(
    private val onAddToCartClicked: (Product) -> Unit
) :
    RecyclerView.Adapter<GridAdapter.GridViewHolder>() {

    private val data = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun bindData(items: List<Product>) {
        data.clear()
        data.addAll(items)
        Log.i("products", items.joinToString("\n") { it.toString() })
        notifyDataSetChanged()
    }

    fun addData(items: List<Product>) {
        data.addAll(items)
        notifyItemRangeChanged(data.size - items.size, items.size)
    }

    inner class GridViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {

            //Title
            binding.productItemText.text =
                item.brand?.let { styledTitle(binding.root.context, item.title, it) } ?: item.title

            //Image
            ImageService.setImage(
                binding.productItemImage.image,
                binding.productItemImage.imageShimmer,
                item.thumbnail
            )

            //Price
            binding.productItemPrice.text = item.price.formatPrice()

            //Discount
            if (item.discountPercentage > 0) {
                binding.productItemDiscountCard.visibility = View.VISIBLE
                binding.productItemDiscountText.text =
                    "${item.discountPercentage.formatToEnglish()}%"
            } else {
                binding.productItemDiscountCard.visibility = View.VISIBLE
            }


            //Rating
            if (item.rating > 0) {
                binding.productItemRatingCard.visibility = View.VISIBLE
                binding.productItemRatingText.text = item.rating.formatToEnglish()
            } else {
                binding.productItemRatingCard.visibility = View.VISIBLE
            }


            binding.root.setOnClickListener {

            }

            binding.productItemAddToCart.setOnClickListener {
                onAddToCartClicked(item)
            }
        }

    }


    fun styledTitle(context: Context, titleText: String, brandText: String): SpannableString {

        val fullText = "$brandText, $titleText"

        // Create SpannableString to style text
        val spannableString = SpannableString(fullText)

        // Style the brand part
        val brandSpan = ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary))
        spannableString.setSpan(brandSpan, 0, brandText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        val brandStyle = StyleSpan(R.style.TextBodyMediumSemiBold)
        spannableString.setSpan(brandStyle, 0, brandText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Style the title part
        val titleSpan = ForegroundColorSpan(ContextCompat.getColor(context, R.color.onBackground))
        spannableString.setSpan(
            titleSpan,
            brandText.length + 1,
            fullText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val titleStyle = StyleSpan(R.style.TextBodyMediumRegular)
        spannableString.setSpan(
            titleStyle,
            brandText.length + 1,
            fullText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return spannableString
    }
}