package com.alecpts.formproject.view.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alecpts.formproject.ui.component.MyColorPicker
import com.alecpts.formproject.ui.component.MyDatePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductTextFields(
    productName: String,
    onProductNameChange: (String) -> Unit,
    purchaseDate: String,
    onPurchaseDateChange: (String) -> Unit,
    productColor: String,
    onProductColorChange: (String) -> Unit,
    productOrigin: String,
    onProductOriginChange: (String) -> Unit,
) {
    val showDatePicker = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val showColorPicker = remember { mutableStateOf(false) }

    // Product Text Fields title
    Text(
        "Enter product properties",
        modifier = Modifier.padding(0.dp, 20.dp, 0.dp, 20.dp),
        fontSize = 20.sp
    )

    TextField(
        value = productName,
        onValueChange = onProductNameChange,
        placeholder = { Text("Product name") },
        maxLines = 1,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Select date"
            )
        },
    )

    // Date picker
    MyDatePicker(
        shouldShowDatePicker = showDatePicker,
        datePickerState = datePickerState,
        onSelectedDateChange = { formattedDate ->
            onPurchaseDateChange(formattedDate)
        }
    )

    TextField(
        value = purchaseDate,
        onValueChange = onPurchaseDateChange,
        placeholder = { Text("Date of purchase") },
        enabled = false,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Select date"
            )
        },
        modifier = Modifier
            .clickable(onClick = {
                showDatePicker.value = true
            })
    )

    // Color picker
    MyColorPicker(
        showColorPicker = showColorPicker,
        onSelectedColorChange = { selectedColor ->
            onProductColorChange(selectedColor)
        }
    )

    TextField(
        value = productColor,
        onValueChange = onProductColorChange,
        placeholder = { Text("Product color") },
        enabled = false,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Select color"
            )
        },
        modifier = Modifier
            .clickable(onClick = {
                showColorPicker.value = true
            })
    )

    TextField(
        value = productOrigin,
        onValueChange = onProductOriginChange,
        placeholder = { Text("Product origin") },
        maxLines = 1,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "Select date"
            )
        },
    )
}