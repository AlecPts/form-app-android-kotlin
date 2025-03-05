package com.alecpts.formproject.view.form

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Initialize product type enum
enum class ProductType {
    Consumable,
    Durable,
    Other,
    None
}

@Composable
fun ProductTypeSelector(
    productType: ProductType,
    onSelectProductType: (ProductType) -> Unit
) {

    // Product Type Selector title
    Text(
        "Choose a product type",
        modifier = Modifier.padding(0.dp, 20.dp, 0.dp, 15.dp),
        fontSize = 20.sp
    )

    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = productType == ProductType.Consumable,
            onClick = { onSelectProductType.invoke(ProductType.Consumable) }
        )
        Text("Consumable")
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = productType == ProductType.Durable,
            onClick = { onSelectProductType.invoke(ProductType.Durable) },
        )
        Text("Durable")
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = productType == ProductType.Other,
            onClick = { onSelectProductType.invoke(ProductType.Other) },
        )
        Text("Other")
    }
}