package com.alecpts.formproject.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alecpts.formproject.view.destinations.FormViewDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient

@Destination(start = true)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainView(
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<FormViewDestination, String>
) {
    var formInfo by remember { mutableStateOf("") }

    Scaffold {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(10.dp)
        ) {
            // Main view title
            TopAppBar(
                title = {
                    Text("Main View")
                }
            )

            // Form information
            Text(
                "Form information",
                modifier = Modifier.padding(0.dp, 20.dp, 0.dp, 15.dp),
                fontSize = 20.sp
            )

            resultRecipient.onNavResult {
                if (it is NavResult.Value) {
                     formInfo = it.value
                }
            }

            Text(formInfo)

            // Form view destination button
            Button(
                onClick = {
                    navigator.navigate(FormViewDestination(1))
                },
            ) {
                Text("Form")
            }
        }

    }
}


