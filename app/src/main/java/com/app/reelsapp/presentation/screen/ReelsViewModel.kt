package com.app.reelsapp.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.reelsapp.domain.model.Product
import com.app.reelsapp.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReelsViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _productPagingData = MutableStateFlow<PagingData<Product>>(PagingData.empty())
    val productPagingData: StateFlow<PagingData<Product>> = _productPagingData.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            productRepository.getProducts()
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _productPagingData.value = pagingData
                }
        }
    }
}