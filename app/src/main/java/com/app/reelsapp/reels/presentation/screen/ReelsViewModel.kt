package com.app.reelsapp.reels.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.app.reelsapp.reels.domain.model.Product
import com.app.reelsapp.reels.domain.model.ProductOwner
import com.app.reelsapp.reels.domain.repository.ProductRepository
import com.app.reelsapp.core.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReelsViewModel @Inject constructor(
    productRepository: ProductRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _currentReelPlaying = MutableStateFlow(false)
    val currentReelPlaying: StateFlow<Boolean> = _currentReelPlaying

    private var currentUsername: String? = null

    private val _productPagingData = MutableStateFlow(PagingData.empty<Product>())
    val productPagingData: StateFlow<PagingData<Product>> = _productPagingData

    private val _productOwnerPagingData = MutableStateFlow(PagingData.empty<ProductOwner>())
    val productOwnerPagingData: StateFlow<PagingData<ProductOwner>> = _productOwnerPagingData

    init {
        viewModelScope.launch {
            currentUsername = userRepository.getCurrentUsername().getOrNull()
        }

        productRepository.getProducts()
            .cachedIn(viewModelScope)
            .onEach { _productPagingData.value = it }
            .launchIn(viewModelScope)

        productRepository.getProductOwner()
            .cachedIn(viewModelScope)
            .onEach { _productOwnerPagingData.value = it }
            .launchIn(viewModelScope)
    }

    fun onChangeCurrentReelPlaying(currentReelPlaying: Boolean) {
        _currentReelPlaying.value = currentReelPlaying
    }

    fun onChangeFavorite(productID: String, isFavorite: Boolean) = viewModelScope.launch {
        currentUsername?.let {
            userRepository.toggleFavoriteStatusForUserProduct(
                it,
                productID,
                isFavorite
            )
        }
        _productPagingData.update { data ->
            data.map {
                it.takeIf { p -> p.id.toString() == productID }?.copy(isFavorite = isFavorite) ?: it
            }
        }
    }

    fun onChangeFollow(productOwnerID: String, isFollow: Boolean) = viewModelScope.launch {
        currentUsername?.let {
            userRepository.toggleProductOwnerFollowStatusForUser(
                it,
                productOwnerID,
                isFollow
            )
        }
        _productOwnerPagingData.update { data ->
            data.map {
                it.takeIf { o -> o.id.toString() == productOwnerID }?.copy(isFollow = isFollow)
                    ?: it
            }
        }
    }
}
