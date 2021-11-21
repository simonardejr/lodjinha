package br.com.lodjinha.repositories

import br.com.lodjinha.api.LodjinhaService
import br.com.lodjinha.api.SafeApiCall
import br.com.lodjinha.models.GetBannerResponse
import br.com.lodjinha.models.GetCategoriaResponse
import br.com.lodjinha.models.GetMaisVendidosResponse
import br.com.lodjinha.models.GetProdutosPorCategoriaResponse
import br.com.lodjinha.utils.ResponseWrapper
import retrofit2.Response

class LodjinhaRepository(
    private val service: LodjinhaService
) {

    suspend fun getBanner(): Response<GetBannerResponse> {
        return SafeApiCall.safeNetworkRequest {
            service.getBanner()
        } ?: Response.success(null)
    }

    suspend fun getCategoria(): Response<GetCategoriaResponse> {
        return SafeApiCall.safeNetworkRequest {
            service.getCategoria()
        } ?: Response.success(null)
    }

    suspend fun getMaisVendidos(): Response<GetMaisVendidosResponse> {
        return SafeApiCall.safeNetworkRequest {
            service.getMaisVendidos()
        } ?: Response.success(null)
    }

    suspend fun getProdutoPorCategoria(id: Int): Response<GetProdutosPorCategoriaResponse> {
        return SafeApiCall.safeNetworkRequest {
            service.getProdutoPorCategoria(id)
        } ?: Response.success(null)
    }
}