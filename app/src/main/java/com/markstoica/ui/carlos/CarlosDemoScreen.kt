package com.markstoica.ui.carlos

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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.markstoica.R
import hu.icellmobilsoft.carlosformito.core.api.FormManager
import hu.icellmobilsoft.carlosformito.core.ui.extensions.collectFieldStateWithLifecycle
import hu.icellmobilsoft.carlosformito.ui.field.FormPasswordTextField
import hu.icellmobilsoft.carlosformito.ui.field.FormTextField

@Composable
fun CarlosDemoScreen(
    formManager: FormManager,
    onSubmitClick: () -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(R.drawable.carlos_logo),
                contentDescription = null,
                modifier = Modifier
                    .width(260.dp)
                    .padding(vertical = 32.dp)
            )

            val usernameFieldItem = formManager.getFieldItem<String>(CarlosDemoFormFields.USERNAME)
            val usernameState by usernameFieldItem.collectFieldStateWithLifecycle()

            FormTextField(
                fieldItem = usernameFieldItem,
                label = {
                    Text(text = stringResource(R.string.username_label))
                },
                trailingIcon = {
                    AnimatedVisibility(usernameState.validationInProgress) {
                        CircularProgressIndicator(
                            strokeWidth = 3.dp,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )

            FormPasswordTextField(
                fieldItem = formManager.getFieldItem(CarlosDemoFormFields.PASSWORD),
                label = {
                    Text(text = stringResource(R.string.password_label))
                }
            )

            Text(
                text = stringResource(R.string.password_instructions),
                style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 16.dp)
            )

            FormPasswordTextField(
                fieldItem = formManager.getFieldItem(CarlosDemoFormFields.CONFIRM_PASSWORD),
                label = {
                    Text(text = stringResource(R.string.confirm_password_label))
                }
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
