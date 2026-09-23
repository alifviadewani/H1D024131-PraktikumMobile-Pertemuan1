package com.example.alifvia.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alifvia.R
import com.example.alifvia.data.dummy.DummyData
import com.example.alifvia.data.model.Category
import com.example.alifvia.data.model.Product
import com.example.alifvia.ui.theme.JualanTheme
import kotlinx.coroutines.delay

@Composable
fun ProductItemCard(
    product: Product,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(
            size = 8.dp
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.surface
        )
    ) {

        Column(
            modifier = Modifier.padding(8.dp)
        ) {

            val imageRes =
                if (product.img == "dummy_product") {
                    R.drawable.dummy_product
                } else {
                    R.drawable.dummy_product
                }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(
                        RoundedCornerShape(
                            size = 8.dp
                        )
                    )
                    .background(
                        MaterialTheme.colorScheme.surface
                    )
            ) {

                Image(
                    painter = painterResource(
                        id = imageRes
                    ),
                    contentDescription =
                        product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale =
                        ContentScale.Fit
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = product.name,
                style =
                    MaterialTheme.typography.titleMedium,
                fontWeight =
                    FontWeight.Bold,
                maxLines = 1,
                overflow =
                    TextOverflow.Ellipsis
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Rp ${product.price}",
                style =
                    MaterialTheme.typography.bodyMedium,
                color =
                    MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val containerColor =
        if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        }

    val contentColor =
        if (isSelected) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        }

    Card(
        modifier = Modifier.clickable {
            onClick()
        },
        shape = RoundedCornerShape(
            size = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor =
                containerColor,
            contentColor =
                contentColor
        )
    ) {

        Text(
            text = category.name,
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 8.dp
            ),
            fontWeight =
                FontWeight.Medium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarProdukScreen(
    navController: NavController,
    onContactUsClick: () -> Unit = {}
) {

    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    var selectedCategoryId by rememberSaveable {
        mutableStateOf(
            DummyData.categories
                .firstOrNull()
                ?.id
        )
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(
        searchQuery,
        selectedCategoryId
    ) {

        isLoading = true

        delay(1000)

        isLoading = false
    }

    val products = DummyData.products

    val filteredProducts =
        products.filter { product ->

            val matchCategory =
                if (selectedCategoryId != null) {
                    product.category_id ==
                            selectedCategoryId
                } else {
                    true
                }

            val matchSearch =
                product.name.contains(
                    searchQuery,
                    ignoreCase = true
                )

            matchCategory && matchSearch
        }

    StatelessDaftarProduct(
        navController = navController,
        searchQuery = searchQuery,
        onSearchQueryChange = {
            searchQuery = it
        },
        selectedCategoryId =
            selectedCategoryId,
        onCategorySelected = {
            selectedCategoryId = it
        },
        isLoading = isLoading,
        products = filteredProducts,
        onContactUsClick = onContactUsClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatelessDaftarProduct(
    navController: NavController?,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedCategoryId: Int?,
    onCategorySelected: (Int) -> Unit,
    isLoading: Boolean,
    products: List<Product>,
    onContactUsClick: () -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text(
                        text = "Daftar Produk UMKM"
                    )
                },

                actions = {

                    IconButton(
                        onClick = {
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.ShoppingCart,
                            contentDescription =
                                "Keranjang"
                        )
                    }

                    IconButton(
                        onClick = {
                            expanded = true
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.MoreVert,
                            contentDescription =
                                "Menu"
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {

                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Hubungi Kami"
                                )
                            },
                            onClick = {
                                expanded = false
                                onContactUsClick()
                            }
                        )
                    }
                },

                colors =
                    androidx.compose.material3
                        .TopAppBarDefaults
                        .topAppBarColors(
                            containerColor =
                                MaterialTheme
                                    .colorScheme
                                    .primary,
                            titleContentColor =
                                MaterialTheme
                                    .colorScheme
                                    .onPrimary,
                            actionIconContentColor =
                                MaterialTheme
                                    .colorScheme
                                    .onPrimary
                        )
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    MaterialTheme.colorScheme.background
                )
        ) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange =
                    onSearchQueryChange,
                placeholder = {
                    Text(
                        text = "Cari produk..."
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    )
            )

            Text(
                text = "Kategori Produk",
                style =
                    MaterialTheme.typography.titleMedium,
                fontWeight =
                    FontWeight.Bold,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 4.dp
                )
            )

            LazyRow(
                contentPadding =
                    PaddingValues(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                horizontalArrangement =
                    Arrangement.spacedBy(
                        8.dp
                    )
            ) {

                items(
                    DummyData.categories
                ) { category ->

                    CategoryItem(
                        category = category,
                        isSelected =
                            category.id ==
                                    selectedCategoryId,
                        onClick = {
                            onCategorySelected(
                                category.id
                            )
                        }
                    )
                }
            }

            Text(
                text = "Daftar Produk",
                style =
                    MaterialTheme.typography.titleMedium,
                fontWeight =
                    FontWeight.Bold,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
            )

            if (isLoading) {

                androidx.compose.foundation.layout
                    .Box(
                        modifier =
                            Modifier.fillMaxSize(),
                        contentAlignment =
                            androidx.compose.ui
                                .Alignment
                                .Center
                    ) {

                        CircularProgressIndicator()
                    }

            } else {

                LazyVerticalGrid(

                    columns =
                        GridCells.Fixed(2),

                    contentPadding =
                        PaddingValues(
                            horizontal = 12.dp,
                            vertical = 8.dp
                        ),

                    horizontalArrangement =
                        Arrangement.spacedBy(
                            8.dp
                        ),

                    verticalArrangement =
                        Arrangement.spacedBy(
                            8.dp
                        ),

                    modifier =
                        Modifier.fillMaxSize()

                ) {

                    items(
                        products
                    ) { product ->

                        ProductItemCard(
                            product = product,
                            onClick = {

                                navController?.navigate(
                                    "detail/${product.id}"
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun DaftarProdukScreenPreview() {

    JualanTheme {

        StatelessDaftarProduct(
            navController = null,
            searchQuery = "",
            onSearchQueryChange = {},
            selectedCategoryId =
                DummyData.categories
                    .firstOrNull()
                    ?.id,
            onCategorySelected = {},
            isLoading = false,
            products =
                DummyData.products.take(4),
            onContactUsClick = {}
        )
    }
}