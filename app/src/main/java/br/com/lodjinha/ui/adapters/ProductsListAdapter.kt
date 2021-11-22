package br.com.lodjinha.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.lodjinha.R
import br.com.lodjinha.databinding.FragmentProductsListBinding
import coil.load
import br.com.lodjinha.databinding.MaisvendidosLayoutRvBinding
import br.com.lodjinha.models.GetMaisVendidosResponse
import br.com.lodjinha.models.GetProdutosPorCategoriaResponse

class ProductsListAdapter :
    ListAdapter<GetProdutosPorCategoriaResponse.ProdutoResponse, ProductsListAdapter.ProdutoListViewHolder>(differCallback) {

    inner class ProdutoListViewHolder(
        private val binding: MaisvendidosLayoutRvBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(model: GetProdutosPorCategoriaResponse.ProdutoResponse) {
            binding.apply {
                productIv.load(model.urlImagem) {
                    crossfade(true)
                    placeholder(R.drawable.ic_info)
                    error(R.drawable.ic_info)

                    listener(
                        onSuccess = { request, data ->
                            println("Successo lojinha")
                        },
                        onError = { request, throwable ->
                            println("Error lojinha")
                            println(throwable.localizedMessage)
                            println(request)
                        }
                    )

                }

                descricaoProduto.text = model.descricao

                dePriceProduct.text = "R$ ${model.precoDe}"
                porPriceProduct.text = "R$ ${model.precoPor}"

                root.setOnClickListener {
                    onItemClickListener?.invoke(model)
                }

            }

        }

    }

    companion object {
        private val differCallback: DiffUtil.ItemCallback<GetProdutosPorCategoriaResponse.ProdutoResponse> =
            object : DiffUtil.ItemCallback<GetProdutosPorCategoriaResponse.ProdutoResponse>(){
                override fun areItemsTheSame(
                    oldItem: GetProdutosPorCategoriaResponse.ProdutoResponse,
                    newItem: GetProdutosPorCategoriaResponse.ProdutoResponse
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: GetProdutosPorCategoriaResponse.ProdutoResponse,
                    newItem: GetProdutosPorCategoriaResponse.ProdutoResponse
                ): Boolean {
                    return oldItem.id == newItem.id
                }

            }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoListViewHolder {
        val binding = MaisvendidosLayoutRvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProdutoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProdutoListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ( (GetProdutosPorCategoriaResponse.ProdutoResponse) -> Unit )? = null

    fun setOnItemClickListener(clickListener: (GetProdutosPorCategoriaResponse.ProdutoResponse) -> Unit ) {
        onItemClickListener = clickListener
    }

}