package com.alecpts.formproject.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Composable
fun MyAlertDialog(
    shouldShowDialog: MutableState<Boolean>,
    formInfoList: MutableList<String>,
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<String>
    ) {
        if (shouldShowDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    shouldShowDialog.value = false
                },
                title = {
                    Text(
                        text = "Confirm this information?",
                        fontSize = 20.sp
                    )
                },
                text = {
                    Text(
                        "Product type : " + formInfoList[0] + "\n" +
                            "Product name : " + formInfoList[1] + "\n" +
                            "Purchase date : " + formInfoList[2] + "\n" +
                            "Product color : " + formInfoList[3] + "\n" +
                            "Product origin : " + formInfoList[4] + "\n" +
                            "Favorite : " + formInfoList[5]
                    )
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            shouldShowDialog.value = false
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
                            // Convert mutable list to string
                            val formInfoString = formInfoList.joinToString(separator = "\n")

                            // Return info list to main view
                            resultNavigator.navigateBack(formInfoString)
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