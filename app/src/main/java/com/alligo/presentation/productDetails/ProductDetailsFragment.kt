package  com.alligo.presentation.productDetails

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alligo.R
import com.alligo.data.utils.NetworkResult
import com.alligo.databinding.FragmentProductDetailsBinding
import com.alligo.model.product.Product
import com.alligo.presentation.addToCart.AddToCartDialog
import com.alligo.presentation.productDetails.components.ImagePagerAdapter
import com.alligo.presentation.productDetails.components.ReviewPagerAdapter
import com.alligo.presentation.productDetails.components.TagsAdapter
import com.alligo.presentation.utils.ImageService
import com.alligo.presentation.utils.ToastUtils
import com.alligo.presentation.utils.extentions.formatPrice
import com.alligo.presentation.utils.extentions.formatToEnglish
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {


    private val viewModel: ProductDetailsViewModel by viewModels()

    private var _binding: FragmentProductDetailsBinding? = null

    private val binding get() = _binding!!

    private val args: ProductDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getProduct(args.id)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.productError.errorViewRetry.setOnClickListener {
            viewModel.getProduct(args.id)
        }


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.productsState.collect { state ->
                when (state) {
                    is NetworkResult.Loading -> {

                        binding.productProgress.visibility = View.VISIBLE
                        binding.productView.visibility = View.GONE
                        binding.productErrorView.visibility = View.GONE
                        binding.productBottomSheet.visibility = View.GONE
                    }

                    is NetworkResult.Success -> {
                        binding.productProgress.visibility = View.GONE
                        binding.productView.visibility = View.VISIBLE
                        binding.productBottomSheet.visibility = View.VISIBLE
                        binding.productErrorView.visibility = View.GONE

                        setProductDetails(state.data)

                        binding.productAddToCartBtn.setOnClickListener {
                            val addToCartDialog = AddToCartDialog(state.data){
                                ToastUtils.show(requireActivity(),
                                    getString(R.string.item_is_added_successfully))
                            }
                            addToCartDialog.showNow(childFragmentManager, "AddToCartDialog")
                        }
                    }

                    is NetworkResult.Failure -> {
                        binding.productProgress.visibility = View.GONE
                        binding.productErrorView.visibility = View.VISIBLE

                    }

                    else -> {}
                }
            }
        }
    }


    private fun setProductDetails(product: Product) {
        // Fetch product details from API / ViewModel

        binding.apply {
//            ImageService.setImage(
//                productThumbnail.image,
//                productThumbnail.imageShimmer,
//                product.thumbnail
//            )

            val adapter = ImagePagerAdapter(product.images)
            binding.productViewPager.adapter = adapter
            // Attach TabLayout with ViewPager2
            TabLayoutMediator(binding.productTabLayout, binding.productViewPager) { _, _ -> }.attach()


            if (product.brand.isNullOrEmpty()) {
                productBrand.visibility = View.GONE
            } else {
                productBrand.text = product.brand
            }

            productTitle.text = product.title

            productCategory.text=product.category

            productPrice.text =
                (product.price - product.price * product.discountPercentage / 100).formatPrice()

            if (product.discountPercentage > 0) {

                productOriginalPrice.text = product.price.formatPrice()

                productOriginalPrice.paintFlags =
                    productOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                productDiscountText.text = "${product.discountPercentage.formatToEnglish()}%"


            } else {
                productDiscountCard.visibility = View.GONE
                productOriginalPrice.visibility = View.GONE
            }

            productItemRatingText.text = product.rating.formatToEnglish()

            productStock.text =product.availabilityStatus
            productStock.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    if (product.stock > 0) R.color.green else R.color.red
                )
            )


            val tagsAdapter = TagsAdapter(product.tags)
            binding.productTageRecyclerview.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                this.adapter = tagsAdapter
            }

            productDescription.text = product.description
            productShippingInfo.text = product.shippingInformation
            productWarranty.text = product.warrantyInformation
            productPolicy.text = product.returnPolicy


            val reviewAdapter = ReviewPagerAdapter(product.reviews)
            binding.productReviewRecyclerview.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                this.adapter = reviewAdapter
            }        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

