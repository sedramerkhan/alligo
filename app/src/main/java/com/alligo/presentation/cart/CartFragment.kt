package  com.alligo.presentation.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.alligo.R
import com.alligo.databinding.FragmentCartBinding
import com.alligo.model.CartItem
import com.alligo.presentation.cart.components.CartAdapter
import com.alligo.presentation.cart.components.CheckoutSuccessDialog
import com.alligo.presentation.cart.components.SwipeHandler
import com.alligo.presentation.utils.SnackbarService
import com.alligo.presentation.utils.extentions.formatPrice
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private val viewModel: CartViewModel by activityViewModels()

    private lateinit var cartAdapter: CartAdapter


    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerViewInitializer()


        binding.cartCheckoutBtn.setOnClickListener {
            val checkoutSuccessDialog = CheckoutSuccessDialog {
                viewModel.deleteAll()
            }
            checkoutSuccessDialog.showNow(childFragmentManager, "AddToCartDialog")
        }

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cartItemsState.collect { items ->
                setData(items)
            }
        }

    }


    private fun setData(items: List<CartItem>) {
        if (items.isNotEmpty()) {
            cartAdapter.bindData(items)

            val originalPrice = viewModel.originalPrice
            binding.cartOriginalPriceValue.text = originalPrice.formatPrice()


            val discount = viewModel.discount
            binding.cartDiscountValue.text = discount.formatPrice()

            binding.cartTotalValue.text = (originalPrice - discount).formatPrice()
        } else {
            binding.cartBottomSheet.visibility = View.GONE
            binding.cartEmpty.visibility = View.VISIBLE
        }
    }

    private fun recyclerViewInitializer() {

        cartAdapter = CartAdapter(
            onItemDecreased = {
                viewModel.decreaseItemQuantity(it)
            },
            onItemIncreased = {
                viewModel.increaseItemQuantity(it)
            }
        )
        binding.cartRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }

        val itemTouchHelper = ItemTouchHelper(
            SwipeHandler.simpleItemTouchCallback(
                context = requireContext(),
                canSwipe = {
                    viewModel.isDeleteEnabled
                },
                onDelete = { index ->
                    viewModel.deleteItem(index)
                    cartAdapter.removeItem(index)
                    view?.let {
                        SnackbarService.showWithAction(it, getString(R.string.item_is_deleted), onUndoClicked = {

                            cartAdapter.addItem(
                                viewModel.recentlyDeletedItemPosition!!,
                                viewModel.recentlyDeletedItem!!
                            )
                            viewModel.clearDeleteData()

                        }, onCompleteAction = {
                            viewModel.confirmDeleteItem()

                        })
                    }
                },

                )
        )

        itemTouchHelper.attachToRecyclerView(binding.cartRecyclerview)
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}