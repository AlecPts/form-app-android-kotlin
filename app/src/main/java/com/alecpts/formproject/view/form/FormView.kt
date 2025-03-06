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
import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.ui.layout.ContentScale
import coil3.compose.rememberAsyncImagePainter
import com.alecpts.formproject.R
import com.alecpts.formproject.classe.Product
import com.alecpts.formproject.ui.component.dialog.ImagePickerDialog
import com.alecpts.formproject.ui.component.dialog.MyAlertDialog
import com.alecpts.formproject.ui.component.dialog.PermissionDeniedDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Destination
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FormView(
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<Product>,
    id: Int,
    productToModify: Product? = null
) {
    // Snackbar
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val productId = 0
    var state by remember { mutableStateOf(ProductType.None) }

    // Textfields
    var productName by remember { mutableStateOf(productToModify?.productName ?: "") }
    var purchaseDate by remember { mutableStateOf(productToModify?.purchaseDate ?:"") }
    var productColor by remember { mutableStateOf(productToModify?.productColor ?:"") }
    var productOrigin by remember { mutableStateOf(productToModify?.productOrigin ?:"") }
    var checked by remember { mutableStateOf(productToModify?.favorite ?:false) }

    // Product image based on selected product type
    val imagePainter = productToModify?.imagePainterId
        ?: when (state) {
        ProductType.Consumable -> R.drawable.potion_of_healing
        ProductType.Durable -> R.drawable.pure_nail
        ProductType.Other -> R.drawable.animal_crossing_leaf
        else -> R.drawable.default_image
    }

    // Add new image from image picker dialog
    var selectedImageUri by remember { mutableStateOf<Uri?>(productToModify?.imageUri) }
    var selectedImageBitmap by remember { mutableStateOf<Bitmap?>(productToModify?.imageBitmap) }

    // Form info list
    val product = Product(
        state.toString(),
        productName,
        purchaseDate,
        productColor,
        productOrigin,
        checked,
        imagePainter,
        imageUri = selectedImageUri,
        imageBitmap = selectedImageBitmap
    )

    val showDialog = remember { mutableStateOf(false) }
    val showImagePickerDialog = remember { mutableStateOf(false) }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            if (bitmap != null) {
                selectedImageBitmap = bitmap
            }
        }
    )

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                selectedImageUri = uri
            }
        }
    )

    // Permissions
    val showPermissionDeniedDialog = remember { mutableStateOf(false) }

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val requestCameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            takePictureLauncher.launch(null)
        }
        else {
            showPermissionDeniedDialog.value = true
        }
    }

    val galleryPermissionState = rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)
    val requestGalleryPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            pickImageLauncher.launch("image/*")
        }
        else {
            showPermissionDeniedDialog.value = true
        }
    }

    // Show why permission is needed
    if (showPermissionDeniedDialog.value) {
        if (cameraPermissionState.status.shouldShowRationale) {
            PermissionDeniedDialog(showPermissionDeniedDialog, requestCameraPermissionLauncher, "camera")
        }
        else if (galleryPermissionState.status.shouldShowRationale) {
            PermissionDeniedDialog(showPermissionDeniedDialog, requestGalleryPermissionLauncher, "gallery")
        }
    }


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


            // Form profile picture
            Image(
                painter = when {
                    selectedImageBitmap != null -> rememberAsyncImagePainter(selectedImageBitmap)
                    selectedImageUri != null -> rememberAsyncImagePainter(selectedImageUri)
                    else -> painterResource(id = imagePainter)
                },
                contentDescription = "Product image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape)
                    .clickable(onClick = {
                        // Choose between camera and gallery
                        showImagePickerDialog.value = true
                    })
            )

            // Show camera picker
            if (showImagePickerDialog.value) {
                ImagePickerDialog(
                    showImagePickerDialog,
                    cameraPermissionState,
                    showPermissionDeniedDialog,
                    requestCameraPermissionLauncher,
                    requestGalleryPermissionLauncher,
                    takePictureLauncher,
                    pickImageLauncher
                )
            }


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
                    showDialog,
                    product,
                    navigator,
                    resultNavigator,
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