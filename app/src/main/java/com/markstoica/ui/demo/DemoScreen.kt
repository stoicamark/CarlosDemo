package com.markstoica.ui.demo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.markstoica.R

@Composable
fun DemoScreen(
    uiState: UiState,
    updateUsername: (String) -> Unit,
    updatePassword: (String) -> Unit,
    updateConfirmPassword: (String) -> Unit,
    onSubmitClick: () -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.carlos_logo),
                contentDescription = null,
                modifier = Modifier
                    .width(260.dp)
                    .padding(vertical = 32.dp)
            )

            OutlinedTextField(
                value = uiState.username,
                onValueChange = updateUsername,
                label = {
                    Text(text = stringResource(R.string.username_label))
                },
                trailingIcon = {
                    AnimatedVisibility(uiState.usernameValidationInProgress) {
                        CircularProgressIndicator(
                            strokeWidth = 3.dp,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                isError = uiState.usernameErrorRes != null,
                supportingText = {
                    val errorText = uiState.usernameErrorRes?.let { stringResource(it) }
                    Text(text = errorText.orEmpty())
                },
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedPasswordTextField(
                value = uiState.password,
                onValueChange = updatePassword,
                label = {
                    Text(text = stringResource(R.string.password_label))
                },
                isError = uiState.passwordErrorRes != null,
                supportingText = {
                    val errorText = uiState.passwordErrorRes?.let { stringResource(it) }
                    Text(text = errorText.orEmpty())
                },
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = stringResource(R.string.password_instructions),
                style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 16.dp)
            )

            OutlinedPasswordTextField(
                value = uiState.confirmPassword,
                onValueChange = updateConfirmPassword,
                label = {
                    Text(text = stringResource(R.string.confirm_password_label))
                },
                isError = uiState.confirmPasswordErrorRes != null,
                supportingText = {
                    val errorText = uiState.confirmPasswordErrorRes?.let { stringResource(it) }
                    Text(text = errorText.orEmpty())
                },
                modifier = Modifier.fillMaxWidth(),
            )

            Button(
                onClick = onSubmitClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            ) {
                Text(text = stringResource(R.string.submit_button_label))
            }
        }
    }
}

@Composable
fun OutlinedPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    isError: Boolean,
    supportingText: @Composable (() -> Unit)? = null,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        trailingIcon = {
            IconButton(
                onClick = { passwordVisible = !passwordVisible }
            ) {
                Icon(
                    imageVector = if (passwordVisible) {
                        Icons.Default.Visibility
                    } else {
                        Icons.Default.VisibilityOff
                    },
                    contentDescription = null
                )
            }
        },
        isError = isError,
        supportingText = supportingText,
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        modifier = modifier,
    )
}
