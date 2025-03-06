package com.alecpts.formproject.ui.component.dialog

import android.Manifest
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun PermissionDeniedDialog(
    showPermissionDeniedDialog: MutableState<Boolean>,
    requestPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    permissionType: String
) {
    AlertDialog(
        onDismissRequest = {
            showPermissionDeniedDialog.value = false
        },
        title = {
            Text("Permission Required")
        },
        text = {
            Text("Permission to access the $permissionType is required to add a new photo.")
        },
        confirmButton = {
            Button(onClick = {
                showPermissionDeniedDialog.value = false

                if (permissionType == "camera") {
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
                else if (permissionType == "gallery") {
                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }

            }) {
                Text("Retry")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                showPermissionDeniedDialog.value = false
            }) {
                Text("Cancel")
            }
        }
    )
}
