package com.alecpts.formproject.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController


@Composable
fun MyColorPicker(
    showColorPicker: MutableState<Boolean>,
    onSelectedColorChange: (String) -> Unit
) {
    val colorPickerController = rememberColorPickerController()
    var selectedColor: String = "ffffffff"

    if (showColorPicker.value) {
        Dialog(
            onDismissRequest = {
                showColorPicker.value = false
            }
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color(0xff2e2c37))
            ) {
                Column {
                    HsvColorPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(450.dp)
                            .padding(10.dp),
                        controller = colorPickerController,
                        onColorChanged = { colorEnvelope: ColorEnvelope ->
                            selectedColor = colorEnvelope.hexCode
                        }
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.End)
                    ) {
                        TextButton(
                            onClick = { showColorPicker.value = false }
                        ) {
                            Text("Cancel")
                        }
                        TextButton(
                            onClick = {
                                showColorPicker.value = false
                                onSelectedColorChange(selectedColor)
                            }
                        ) {
                            Text("Ok")
                        }
                    }
                }
            }
        }
    }
}