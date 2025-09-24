package com.markstoica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
                // CarlosDemoContent()
                DemoContent()
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

    DemoScreen(
        viewModel = viewModel,
        onSubmitClick = viewModel::onSubmit
    )
}
