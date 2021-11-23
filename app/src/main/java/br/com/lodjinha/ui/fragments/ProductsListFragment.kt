package br.com.lodjinha.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.lodjinha.api.RetrofitInstance
import br.com.lodjinha.databinding.FragmentProductsListBinding
import br.com.lodjinha.repositories.LodjinhaRepository
import br.com.lodjinha.ui.NavigationDelegate
import br.com.lodjinha.ui.adapters.CategoriasAdapter
import br.com.lodjinha.ui.adapters.ProductsListAdapter
import br.com.lodjinha.ui.viewmodels.CategoryViewModel
import br.com.lodjinha.ui.viewmodels.CategoryViewModelProviderFactory
import br.com.lodjinha.ui.viewmodels.MainViewModel
import br.com.lodjinha.ui.viewmodels.MainViewModelProviderFactory
import br.com.lodjinha.utils.toggleVisibilty

class ProductsListFragment : Fragment() {

    private var _binding: FragmentProductsListBinding? = null
    private val binding: FragmentProductsListBinding get() = _binding!!

    private val args: ProductsListFragmentArgs by lazy {
        ProductsListFragmentArgs.fromBundle(requireArguments())
    }

    private lateinit var productListAdapter: ProductsListAdapter

    private val viewModel: CategoryViewModel by lazy {
        val repository = LodjinhaRepository(RetrofitInstance.apiService)
        val viewModelProviderFactory = CategoryViewModelProviderFactory(repository)
        ViewModelProvider(this, viewModelProviderFactory).get(CategoryViewModel::class.java)
    }

    private var listener: NavigationDelegate? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigationDelegate) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener?.setToolbarTitle(args.title)
        setupProductList()
        setupObservers()
        //chamar aqui o get list
        viewModel.getMainHomeData(args.categoryId)
    }

    private fun setupProductList() {
        productListAdapter = ProductsListAdapter()
        binding.produtosRv.adapter = productListAdapter
        productListAdapter.setOnItemClickListener { produtoResponse ->
            // println(produtoResponse)

            findNavController().navigate(
                HomeFragmentDirections.actionMainFragmentToProductViewFragment(
                    title = produtoResponse.nome,
                    productId = produtoResponse.id,
                    tvProductCategory = produtoResponse.categoria.descricao,
                    tvProdcutName = produtoResponse.nome,
                    tvProductPrice2 = produtoResponse.precoPor.toString(),
                    tvProductPriceFrom2 = produtoResponse.precoDe.toString(),
                    tvProductDescription = produtoResponse.descricao,
                    urlImagem = produtoResponse.urlImagem
                )
            )
        }
    }

    private fun setupObservers() {
        viewModel.homeDataLiveData.observe(viewLifecycleOwner) { viewState ->
            println(viewState)
            when {
                viewState.loading -> {
                    binding.productListLayout.toggleVisibilty(false)
                    binding.progress.toggleVisibilty(true)
                }
                viewState.error -> {
                    // TODO Criar placeholder caso de erro
                    findNavController().navigate(
                        HomeFragmentDirections.actionMainFragmentToErrorFragment()
                    )
                }
                viewState.data != null -> {
                    binding.progress.toggleVisibilty(false)
                    binding.productListLayout.toggleVisibilty(true)
                    if (viewState.data?.categoryListData.isNullOrEmpty().not()) {
                        productListAdapter.differ.submitList(viewState.data?.categoryListData)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}