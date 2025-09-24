package com.markstoica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.markstoica.ui.carlos.CarlosDemoScreen
import com.markstoica.ui.carlos.CarlosDemoViewModel
import com.markstoica.ui.demo.DemoScreen
import com.markstoica.ui.demo.DemoViewModel
import com.markstoica.ui.theme.CarlosDemoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarlosDemoTheme {
                CarlosDemoContent()
                // DemoContent()
            }
        }
    }
}

@Composable
fun CarlosDemoContent() {
    val viewModel = remember { // for demo purposes only
        CarlosDemoViewModel()
    }
    CarlosDemoScreen(
        formManager = viewModel,
        onSubmitClick = viewModel::onSubmit
    )
}

@Composable
fun DemoContent() {
    val viewModel = remember { // for demo purposes only
        DemoViewModel()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DemoScreen(
        uiState = uiState,
        updateUsername = viewModel::updateUsername,
        updatePassword = viewModel::updatePassword,
        updateConfirmPassword = viewModel::updateConfirmPassword,
        onSubmitClick = viewModel::onSubmit
    )
}
