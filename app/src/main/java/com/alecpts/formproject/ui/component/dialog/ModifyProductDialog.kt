package com.alecpts.formproject.ui.component.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.sp
import com.alecpts.formproject.classe.Product
import com.alecpts.formproject.view.destinations.FormViewDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ModifyProductDialog(
    product: Product,
    showDialog: MutableState<Boolean>,
    removeProduct: (Product) -> Unit,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    navigator: DestinationsNavigator
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = {
                Text(
                    text = "Modify product",
                    fontSize = 20.sp
                )
            },
            text = {
                Text("Would you like to modify or delete a product?")
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        removeProduct(product)
                        showDialog.value = false

                        scope.launch {
                            snackbarHostState.showSnackbar("Product removed!")
                        }
                    }
                ) {
                    Text(
                        text = "Delete",
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        navigator.navigate(FormViewDestination(1, product))
                        removeProduct(product)
                    }
                ) {
                    Text(
                        text = "Modify",
                    )
                }
            },
        )
    }
}