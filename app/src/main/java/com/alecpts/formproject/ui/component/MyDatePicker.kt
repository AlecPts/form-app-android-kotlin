package com.alecpts.formproject.ui.component

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(
    shouldShowDatePicker: MutableState<Boolean>,
    datePickerState: DatePickerState,
    onSelectedDateChange: (String) -> Unit,
) {
    if (shouldShowDatePicker.value) {
        DatePickerDialog(
            onDismissRequest = {
                shouldShowDatePicker.value = false
            },
            confirmButton = {
                TextButton(onClick = {
                    shouldShowDatePicker.value = false

                    // Format selected date
                    val selectedDateMillis = datePickerState.selectedDateMillis

                    if (selectedDateMillis != null) {
                        val formattedDate = formatDate(selectedDateMillis)
                        onSelectedDateChange(formattedDate)
                    }
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    shouldShowDatePicker.value = false
                }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
            )
        }
    }
}

// Helper function to format date
fun formatDate(millis: Long): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = Date(millis)
    return dateFormat.format(date)
}