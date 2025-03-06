package com.alecpts.formproject.ui.component.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.sp
import com.alecpts.formproject.classe.Product

@Composable
fun ProductInfoDialog(
    showDialog: MutableState<Boolean>,
    product: MutableState<Product?>
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = {
                Text(
                    text = "${product.value?.productName} Info",
                    fontSize = 20.sp
                )
            },
            text = {
                Text(
                    "Product type : " + product.value?.type + "\n" +
                        "Product name : " + product.value?.productName + "\n" +
                        "Purchase date : " + product.value?.purchaseDate + "\n" +
                        "Product color : " + product.value?.productColor + "\n" +
                        "Product origin : " + product.value?.productOrigin + "\n" +
                        "Favorite : " + product.value?.favorite
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                    }
                ) {
                    Text(
                        text = "Ok",
                    )
                }
            }
        )
    }
}