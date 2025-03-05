package com.alecpts.formproject.view.form

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alecpts.formproject.Manifest
import com.alecpts.formproject.R
import com.alecpts.formproject.ui.component.MyAlertDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Destination
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FormView(
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<String>,
    id: Int
) {
    // Snackbar
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var state by remember { mutableStateOf(ProductType.None) }

    // Textfields
    var productName by remember { mutableStateOf("") }
    var purchaseDate by remember { mutableStateOf("") }
    var productColor by remember { mutableStateOf("") }
    var productOrigin by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }

    // Form info list
    val formInfoList: MutableList<String> = mutableListOf(
        state.toString(),
        productName,
        purchaseDate,
        productColor,
        productOrigin,
        checked.toString()
    )

    val showDialog = remember { mutableStateOf(false) }

    // Permission
    val cameraPermissions = rememberPermissionState(Manifest.permission.CAMERA)

    // Initialize Scaffold
    Scaffold(
        // Initialize snackbar
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(0.dp, 55.dp)
            )
        },
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(10.dp)
        ) {
            // Scaffold title
            TopAppBar(
                title = {
                    Text("Add Product Form")
                }
            )

            // Product image based on selected product type
            val imagePainter = when (state) {
                ProductType.Consumable -> R.drawable.potion_of_healing
                ProductType.Durable -> R.drawable.pure_nail
                ProductType.Other -> R.drawable.animal_crossing_leaf
                else -> R.drawable.default_image
            }

            // Form profile picture
            Image(
                painter = painterResource(id = imagePainter),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape)
            )

            // Radio Buttons to choose product type
            ProductTypeSelector(state) { selectedProductType ->
                state = selectedProductType
            }

            // Text Fields to enter product properties
            ProductTextFields(
                productName,
                onProductNameChange = { productName = it },
                purchaseDate,
                onPurchaseDateChange = { purchaseDate = it },
                productColor,
                onProductColorChange = { productColor = it },
                productOrigin,
                onProductOriginChange = { productOrigin = it },
            )

            // Checkbox to add to favorites
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(0.dp, 20.dp)
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked  = it },
                )
                Text("Add to favorites")
            }

            // Add informations to Toast
            if (showDialog.value) {
                MyAlertDialog(
                    shouldShowDialog = showDialog,
                    formInfoList,
                    navigator,
                    resultNavigator
                )
            }

            Button(
                onClick = {
                    if (productName.isEmpty() || purchaseDate.isEmpty()) {
                        scope.launch {
                            snackbarHostState
                                .showSnackbar(
                                    message = "Please fill in all fields",
                                    duration = SnackbarDuration.Short
                                )

                            // Wait 3 secondes after hide Snackbar
                            delay(3000)
                            snackbarHostState.currentSnackbarData?.dismiss()
                        }
                    }
                    else {
                        showDialog.value = true
                    }
                },
            ) {
                Text("Confirm")
            }
        }
    }
}