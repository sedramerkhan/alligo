package  com.alligo.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alligo.R
import com.alligo.data.utils.NetworkResult
import com.alligo.databinding.FragmentHomeBinding
import com.alligo.model.product.Product
import com.alligo.presentation.MainContainerFragmentDirections
import com.alligo.presentation.addToCart.AddToCartDialog
import com.alligo.presentation.home.components.GridAdapter
import com.alligo.presentation.utils.KeyboardUtils
import com.alligo.presentation.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: GridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

    }

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

        binding.homeError.errorViewRetry.setOnClickListener {
            viewModel.getProducts()
        }


        ///Search
        binding.homeSearchLayout.setEndIconOnClickListener {
            viewModel.onClearSearch()
            activity?.let { KeyboardUtils.hide(it) }

            binding.homeView.clearFocus()
        }

        binding.homeSearch.setOnEditorActionListener { _, actionId, _ ->

            Log.i("product", "$actionId")
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.isSearchEnabled = true
                viewModel.refresh()
                activity?.let { KeyboardUtils.hide(it) }

                binding.homeView.clearFocus()
                true
            } else {
                false
            }
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up data binding
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.productsState.collect { state ->
                binding.homeRefresher.isRefreshing = false

                when (state) {
                    is NetworkResult.Loading -> {
                        binding.homeErrorView.visibility = View.GONE

                        if (adapter.itemCount == 0) {
                            binding.homeProgress.visibility = View.VISIBLE
                            binding.homeView.visibility = View.GONE
                        }
                    }

                    is NetworkResult.Success -> {
                        binding.homeProgress.visibility = View.GONE
                        binding.homeView.visibility = View.VISIBLE
                        binding.homeErrorView.visibility = View.GONE

                        setData(state.data.products)


                    }

                    is NetworkResult.Failure -> {
                        if (viewModel.products.isNotEmpty()) {
                            binding.homeProgress.visibility = View.GONE
                            binding.homeErrorView.visibility = View.VISIBLE

                        } else {
                            activity?.let {
                                ToastUtils.show(
                                    it,
                                    getString(R.string.failed_to_get_data)
                                )
                            }
                        }

                    }

                    else -> {}
                }
            }
        }

    }


    private fun gridInitializer() {
        val recyclerView: RecyclerView = binding.homeProductsGrid
        recyclerView.layoutManager = GridLayoutManager(context, 2) // 2 items per row

        adapter = GridAdapter(onClicked = {

            val navController = requireActivity().findNavController(R.id.main_activity_container)

            val action =
                MainContainerFragmentDirections.actionMainContainerFragmentToProductDetailsFragment(
                    it.id
                )
            navController.navigate(action)
//
        }, onAddToCartClicked = { product ->

            val addToCartDialog = AddToCartDialog(product)
            addToCartDialog.showNow(childFragmentManager, "AddToCartDialog")
        })

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