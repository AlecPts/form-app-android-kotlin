package com.alecpts.formproject.view.main

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import kotlinx.coroutines.launch
import com.alecpts.formproject.classe.Product
import com.alecpts.formproject.ui.component.dialog.ProductInfoDialog
import com.alecpts.formproject.view.destinations.FormViewDestination
import com.ramcosta.composedestinations.result.ResultRecipient

@Destination(start = true)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainView(
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<FormViewDestination, Product>
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var productList by rememberSaveable { mutableStateOf(listOf<Product>()) }
    val selectedProduct = remember { mutableStateOf<Product?>(null) }
    val showProductDialog = remember { mutableStateOf(false) }
    val showModifyProductDialog = remember { mutableStateOf(false) }

    // Filter list
    var filteredProductList by remember { mutableStateOf(productList) }
    var searchQuery by remember { mutableStateOf("") }

    // Function to update filtered list
    fun updateFilteredProductList(query: String) {
        searchQuery = query
        filteredProductList = if (query.isEmpty()) {
            productList
        }
        else {
            productList.filter { it.productName.contains(query, ignoreCase = true) }
        }
    }

    // Function to add product
    fun addProduct(newProduct: Product) {
        productList = productList + newProduct
        updateFilteredProductList(searchQuery)
    }

    // Function to remove product
    fun removeProduct(product: Product) {
        productList = productList.filter { it != product }
        updateFilteredProductList(searchQuery)
    }

    Scaffold(
        // Initialize main view snackbar
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        },
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(10.dp)
        ) {
            // Main view title
            TopAppBar(
                title = {
                    Text("Main View")
                }
            )

            // Search bar
            TextField(
                value = searchQuery,
                onValueChange = { updateFilteredProductList(it) },
                label = { Text("Search product") },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true
            )

            resultRecipient.onNavResult {
                if (it is NavResult.Value) {
                    val newProduct = it.value
                    addProduct(newProduct)

                    // Show the snackbar when a product is added
                    scope.launch {
                        snackbarHostState.showSnackbar("New product added!")
                    }
                }
            }

            // Form information
            Text(
                "Product list",
                modifier = Modifier.padding(0.dp, 20.dp, 0.dp, 15.dp),
                fontSize = 20.sp
            )

            // Display all product from the profuct list
            filteredProductList.forEach { product ->
                ProductCard(
                    product,
                    showProductDialog,
                    showModifyProductDialog,
                    selectedProduct,
                    removeProduct = { removeProduct(product) },
                    scope,
                    snackbarHostState,
                    navigator,
                    paddingValues = PaddingValues(horizontal = 0.dp, vertical = 7.dp)
                )
            }

            // Product dialog
            if (showProductDialog.value && selectedProduct.value != null) {
                ProductInfoDialog(showProductDialog, selectedProduct)
            }

            // Favorites
            Text(
                "Favorite products",
                modifier = Modifier.padding(0.dp, 20.dp, 0.dp, 15.dp),
                fontSize = 20.sp
            )

            LazyRow {
                items(filteredProductList) { product ->
                    if (product.favorite) {
                        ProductCard(
                            product,
                            showProductDialog,
                            showModifyProductDialog,
                            selectedProduct,
                            removeProduct = { removeProduct(product) },
                            scope,
                            snackbarHostState,
                            navigator,
                            paddingValues = PaddingValues(end = 7.dp)
                        )
                    }
                }
            }

            // Form view destination button
            Button(
                modifier = Modifier
                    .padding(vertical = 10.dp),
                onClick = {
                    navigator.navigate(FormViewDestination(1))
                },
            ) {
                Text("Add product")
            }
        }

    }
}


