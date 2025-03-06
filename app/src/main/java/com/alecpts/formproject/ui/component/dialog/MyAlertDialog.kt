package com.alecpts.formproject.ui.component.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.sp
import com.alecpts.formproject.classe.Product
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Composable
fun MyAlertDialog(
    showDialog: MutableState<Boolean>,
    product: Product,
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<Product>,
    ) {
        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    showDialog.value = false
                },
                title = {
                    Text(
                        text = "Confirm this information?",
                        fontSize = 20.sp
                    )
                },
                text = {
                    Text(
                        "Product type : " + product.type + "\n" +
                            "Product name : " + product.productName + "\n" +
                            "Purchase date : " + product.purchaseDate + "\n" +
                            "Product color : " + product.productColor + "\n" +
                            "Product origin : " + product.productOrigin + "\n" +
                            "Favorite : " + product.favorite
                    )
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDialog.value = false
                        }
                    ) {
                        Text(
                            text = "Cancel",
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            // Return info list to main view
                            resultNavigator.navigateBack(product)
                        }
                    ) {
                        Text(
                            text = "Confirm",
                        )
                    }
                }
            )
        }
}