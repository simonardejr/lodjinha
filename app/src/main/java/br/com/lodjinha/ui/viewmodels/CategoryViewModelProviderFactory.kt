package br.com.lodjinha.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.lodjinha.repositories.LodjinhaRepository

class CategoryViewModelProviderFactory(
    private val repository: LodjinhaRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoryViewModel(repository) as T
    }
}