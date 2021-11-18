package br.com.lodjinha.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lodjinha.models.GetBannerResponse
import br.com.lodjinha.models.GetCategoriaResponse
import br.com.lodjinha.models.GetMaisVendidosResponse
import br.com.lodjinha.models.GetProdutosPorCategoriaResponse
import br.com.lodjinha.repositories.LodjinhaRepository
import br.com.lodjinha.ui.viewstates.CategoryData
import br.com.lodjinha.ui.viewstates.CategoryViewState
import br.com.lodjinha.ui.viewstates.HomeData
import br.com.lodjinha.ui.viewstates.HomeViewState
import br.com.lodjinha.utils.ResponseWrapper
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.system.measureTimeMillis

class CategoryViewModel(
    private val repository: LodjinhaRepository
) : ViewModel() {

    private val _homeDataLiveData = MutableLiveData<CategoryViewState>()
    val homeDataLiveData: LiveData<CategoryViewState> get() = _homeDataLiveData

    fun getMainHomeData(id: Int) = viewModelScope.launch {
        _homeDataLiveData.postValue(CategoryViewState(
            loading = true
        ))

        val time1 = measureTimeMillis {
            val categoryListResponse = async {repository.getProdutoPorCategoria(id)}

            val homeState = handleCategoryResponse(
                categoryListResponse.await(),
            )

            _homeDataLiveData.postValue(homeState)
        }

        println("Tempo do request é? $time1 ms")
    }

    private fun handleCategoryResponse(
        categoryListResponse: ResponseWrapper<GetProdutosPorCategoriaResponse>
    ): CategoryViewState {

        var categoryListData: List<GetProdutosPorCategoriaResponse.ProdutoResponse>? = null

        when (categoryListResponse) {
            is ResponseWrapper.Success -> {
                categoryListData = categoryListResponse.result.data
            }
        }

        if (categoryListData != null) {

            return CategoryViewState(
                loading = false,
                error = false,
                data = CategoryData(
                    categoryListData = categoryListData,
                )
            )
        }

        return CategoryViewState(
            loading = false,
            error = true,
            data = null
        )
    }
}