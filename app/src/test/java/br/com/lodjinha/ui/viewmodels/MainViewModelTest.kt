package br.com.lodjinha.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.lodjinha.MainCoroutineRule
import br.com.lodjinha.getOrAwaitValueTest
import br.com.lodjinha.repositories.LodjinhaRepository
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import kotlinx.coroutines.test.runBlockingTest
import retrofit2.Response

class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    lateinit var viewModel: MainViewModel
    val repository: LodjinhaRepository = mockk()

    @Before
    fun setUp() {
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `validar que o mainHomeState é loading ao iniciar o getMainHomeData`() {

        mainCoroutineRule.runBlockingTest {
            val result = viewModel.homeDataLiveData.getOrAwaitValueTest(
                positionOfValueToBeCatch = 1
            ) {
                coEvery { repository.getBanner() } returns Response.success(null)
                coEvery { repository.getCategoria() } returns Response.success(null)
                coEvery { repository.getMaisVendidos() } returns Response.success(null)

                viewModel.getMainHomeData()
            }

            assertThat(result[0].loading).isTrue()
            assertThat(result[1].loading).isFalse()
        }
    }

    @Test
    fun `validar que o mainState retorne com Erro caso um dos endpoints retorne com erro`() {

    }

    @Test
    fun `validar que o data State nao é null quando os 3 requests vierem com success`() {

    }
}