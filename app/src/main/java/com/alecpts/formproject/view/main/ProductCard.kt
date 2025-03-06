package com.alecpts.formproject.view.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.alecpts.formproject.classe.Product
import com.alecpts.formproject.ui.component.dialog.ModifyProductDialog
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductCard(
    product: Product,
    showProductDialog: MutableState<Boolean>,
    showModifyProductDialog: MutableState<Boolean>,
    selectedProduct: MutableState<Product?>,
    removeProduct: (Product) -> Unit,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    navigator: DestinationsNavigator,
    paddingValues: PaddingValues
) {

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .combinedClickable(
                onClick = {
                    selectedProduct.value = product
                    showProductDialog.value = true
                },
                onLongClick = {
                    selectedProduct.value = product
                    showModifyProductDialog.value = true
                }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 3.dp)
        ) {
            Image(
                painter = when {
                    product.imageBitmap != null -> rememberAsyncImagePainter(product.imageBitmap)
                    product.imageUri != null -> rememberAsyncImagePainter(product.imageUri)
                    else -> painterResource(id = product.imagePainterId!!)
                },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )

            Text(
                text = product.productName,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(16.dp),
            )
        }
    }

    if (showModifyProductDialog.value) {
        ModifyProductDialog(
            product,
            showModifyProductDialog,
            removeProduct = { removeProduct(product) },
            scope,
            snackbarHostState,
            navigator
        )
    }
}