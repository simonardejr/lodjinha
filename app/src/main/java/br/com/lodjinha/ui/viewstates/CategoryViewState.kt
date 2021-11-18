package br.com.lodjinha.ui.viewstates

import br.com.lodjinha.models.GetBannerResponse
import br.com.lodjinha.models.GetCategoriaResponse
import br.com.lodjinha.models.GetMaisVendidosResponse
import br.com.lodjinha.models.GetProdutosPorCategoriaResponse

data class CategoryViewState (
    var loading: Boolean = false,
    var error: Boolean = false,
    var data: CategoryData? = null
)

data class CategoryData(
    val categoryListData: List<GetProdutosPorCategoriaResponse.ProdutoResponse>?,
)