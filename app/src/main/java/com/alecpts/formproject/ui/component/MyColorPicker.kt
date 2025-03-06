package com.alecpts.formproject.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = androidx.compose.material3.CardDefaults.cardColors(
                    containerColor = Color(0xff2e2c37)
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    Text(
                        "Select a color",
                        modifier = Modifier.padding(20.dp, 20.dp),
                        fontSize = 30.sp
                    )

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