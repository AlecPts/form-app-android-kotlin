package com.alecpts.formproject.ui.component.dialog

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ImagePickerDialog(
    showImagePickerDialog: MutableState<Boolean>,
    cameraPermissionState: PermissionState,
    showPermissionDeniedDialog: MutableState<Boolean>,
    requestCameraPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    requestGalleryPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    takePictureLauncher: ManagedActivityResultLauncher<Void?, Bitmap?>,
    pickImageLauncher: ManagedActivityResultLauncher<String, Uri?>,
) {
    AlertDialog(
        onDismissRequest = {
            showImagePickerDialog.value = false
        },
        title = {
            Text("Choose an Option")
        },
        text = {
            Text("Select an option to add an image.")
        },
        confirmButton = {
            Button(onClick = {
                // Request camera permission
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                when {
                    cameraPermissionState.status.isGranted -> {
                        // Permission already granted, proceed with camera action
//                        takePictureLauncher.launch(null)
                        showImagePickerDialog.value = false

                    }
                    cameraPermissionState.status.shouldShowRationale -> {
                        // Explain why the permission is needed
                        showPermissionDeniedDialog.value = true
                        showImagePickerDialog.value = false
                    }
                    else -> {
                        showImagePickerDialog.value = false
                    }
                }
            }) {
                Text("Take Photo")
            }
        },
        dismissButton = {
            Button(onClick = {
                // Request camera permission
                requestGalleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                when {
                    cameraPermissionState.status.isGranted -> {
                        // Permission already granted, proceed with gallery action
//                        pickImageLauncher.launch("image/*")
                        showImagePickerDialog.value = false
                    }
                    cameraPermissionState.status.shouldShowRationale -> {
                        // Explain why the permission is needed
                        showPermissionDeniedDialog.value = true
                        showImagePickerDialog.value = false
                    }
                    else -> {
                        showImagePickerDialog.value = false
                    }
                }
            }) {
                Text("Choose from gallery")
            }
        }
    )
}