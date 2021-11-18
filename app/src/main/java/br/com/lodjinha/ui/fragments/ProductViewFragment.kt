package br.com.lodjinha.ui.fragments

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.lodjinha.R
import br.com.lodjinha.databinding.FragmentProductBinding
import br.com.lodjinha.ui.NavigationDelegate
import br.com.lodjinha.utils.DialogUtils
import coil.load

class ProductViewFragment: Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding: FragmentProductBinding get() = _binding!!

    private val args: ProductViewFragmentArgs by lazy {
        ProductViewFragmentArgs.fromBundle(requireArguments())
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
        _binding = FragmentProductBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener?.setToolbarTitle(args.title)

        // nome do produto
        _binding?.productName?.text = args.tvProdcutName

        // preço atual
        _binding?.productPrice?.text = "Por: ${args.tvProductPrice2}"

        // preço antigo
        _binding?.productOldPrice?.text = "  De: ${args.tvProductPriceFrom2}  "
        _binding?.productOldPrice?.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        // descrição
        _binding?.productDescription?.text = args.tvProductDescription

        _binding?.toolbarImage?.apply {
            load(args.urlImagem) {
                crossfade(true)
                placeholder(R.drawable.ic_info)
                error(R.drawable.ic_info)

                listener(onError = {request, throwable ->
                    DialogUtils.showDialog(context, getString(R.string.erro_loading_image)) {}
                })
            }
        }

        _binding?.onReserve?.setOnClickListener {
            DialogUtils.showDialog(context, getString(R.string.reseverd_product)) {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}