package  com.alligo.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alligo.data.utils.NetworkResult
import com.alligo.databinding.FragmentHomeBinding
import com.alligo.model.product.Product
import com.alligo.presentation.addToCart.AddToCartViewModel
import com.alligo.presentation.cart.CartViewModel
import com.alligo.presentation.addToCart.AddToCartDialog
import com.alligo.presentation.home.components.GridAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private val cartViewModel: CartViewModel by activityViewModels()
    private val addToCartViewModel: AddToCartViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: GridAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        gridInitializer()

        binding.homeRefresher.setOnRefreshListener {
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            viewModel.refresh()
        }
        //  binding.buttonHome.setOnClickListener {
//            val navController = requireActivity().findNavController(R.id.main_activity_container)
//
//            val action =
//                MainContainerFragmentDirections.actionMainContainerFragmentToProductDetailsFragment()
//            navController.navigate(action)
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.productsState.collect { state ->
                when (state) {
                    is NetworkResult.Loading -> {

                        //  binding.textHome.text = "Loading"
                    }

                    is NetworkResult.Success -> {
                        setData(state.data.products)

                    }

                    is NetworkResult.Failure -> {
                        //   binding.textHome.text = state.message

                    }
                    else->{}
                }
            }
        }

    }


    private fun gridInitializer() {
        val recyclerView: RecyclerView = binding.homeProductsGrid
        recyclerView.layoutManager = GridLayoutManager(context, 2) // 2 items per row

        adapter = GridAdapter { product ->

            val addToCartDialog = AddToCartDialog(product,)
            addToCartDialog.showNow(childFragmentManager, "AddToCartDialog")
        }

        recyclerView.adapter = adapter

        ///Control calling next page
        binding.homeProductsGrid.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                recyclerView.layoutManager?.apply {
                    // Check if we've scrolled to the last item
                    val totalItemCount = itemCount
                    val visibleItemCount = childCount
                    val firstVisibleItemPosition =
                        (this as GridLayoutManager).findFirstVisibleItemPosition()

                    viewModel.productListScrollPosition =
                        firstVisibleItemPosition + 1 //index start from 0
                    // If the last item is visible, load more
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                        viewModel.nextPage()
                    }
                }

            }
        })
    }

    private fun setData(data: List<Product>) {

        binding.homeRefresher.isRefreshing = false
        if (viewModel.page == 1)
            adapter.bindData(data)
        else
            adapter.addData(data)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}