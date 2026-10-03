package com.example.alifvia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alifvia.data.model.Category
import com.example.alifvia.data.model.Product
import com.example.alifvia.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ProductUiState {

    data object Loading : ProductUiState

    data class Success(
        val categories: List<Category>,
        val products: List<Product>
    ) : ProductUiState

    data class Error(
        val message: String
    ) : ProductUiState
}

class ProductViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow<ProductUiState>(
            ProductUiState.Loading
        )

    val uiState: StateFlow<ProductUiState> =
        _uiState.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {

        viewModelScope.launch {

            try {

                val categoriesResponse =
                    ApiClient.instance.getCategories()

                val productsResponse =
                    ApiClient.instance.getProducts()

                val products =
                    productsResponse.map { product ->

                        val category =
                            categoriesResponse.find { category ->

                                category.id ==
                                        product.category_id
                            }

                        product.copy(
                            category = category
                        )
                    }

                _uiState.value =
                    ProductUiState.Success(
                        categories =
                            categoriesResponse,
                        products =
                            products
                    )

            } catch (e: Exception) {

                _uiState.value =
                    ProductUiState.Error(
                        message =
                            e.message
                                ?: "Terjadi kesalahan"
                    )
            }
        }
    }
}