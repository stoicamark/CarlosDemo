package com.markstoica.ui.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markstoica.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

data class UiState(
    val username: String = "",
    val usernameValidationInProgress: Boolean = false,
    val usernameErrorRes: Int? = null,
    val password: String = "",
    val passwordErrorRes: Int? = null,
    val confirmPassword: String = "",
    val confirmPasswordErrorRes: Int? = null
)

class DemoViewModel : ViewModel() {

    private val fieldStates = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = fieldStates.asStateFlow()

    private var usernameValidationJob: Job? = null

    fun updateUsername(username: String) {
        fieldStates.value = fieldStates.value.copy(username = username)

        usernameValidationJob?.cancel()
        usernameValidationJob = viewModelScope.launch {
            delay(1.seconds)
            validateUsername(username)
        }
    }

    private val specialCharRegex = ".*[!@#$%^&*(),.?\":{}|<>].*".toRegex()
    private val takenUsernames = listOf(
        "username",
        "demo",
        "guest",
        "john_doe",
        "admin"
    )

    private suspend fun validateUsername(username: String) {
        fieldStates.value = fieldStates.value.copy(usernameValidationInProgress = true)

        var errorRes: Int? = null
        if (username.isBlank()) {
            errorRes = R.string.username_required_error
        } else if (username.length < 4) {
            errorRes = R.string.username_length_error
        }

        delay(1.seconds)
        if (username in takenUsernames) {
            errorRes = R.string.username_taken_error
        }

        fieldStates.value = fieldStates.value.copy(
            usernameValidationInProgress = false,
            usernameErrorRes = errorRes
        )
    }

    fun updatePassword(password: String) {
        fieldStates.value = fieldStates.value.copy(password = password)
        validatePassword(password)

        // Revalidate confirm password
        val confirmPassword = fieldStates.value.confirmPassword
        if (!confirmPassword.isBlank()) {
            validateConfirmPassword(confirmPassword, password)
        }
    }

    private fun validatePassword(password: String) {
        val errorRes: Int? = when {
            password.isBlank() -> R.string.password_required_error
            password.length < 8 -> R.string.password_length_error
            !password.matches(specialCharRegex) -> R.string.password_special_char_missing_error
            else -> null
        }
        fieldStates.value = fieldStates.value.copy(passwordErrorRes = errorRes)
    }

    fun updateConfirmPassword(confirmPassword: String) {
        fieldStates.value = fieldStates.value.copy(confirmPassword = confirmPassword)
        validateConfirmPassword(
            confirmPassword = confirmPassword,
            password = fieldStates.value.password
        )
    }

    private fun validateConfirmPassword(confirmPassword: String, password: String) {
        val errorRes: Int? = if (confirmPassword != password) {
            R.string.confirm_password_error
        } else {
            null
        }
        fieldStates.value = fieldStates.value.copy(confirmPasswordErrorRes = errorRes)
    }

    fun onSubmit() {
        viewModelScope.launch {
            validateUsername(fieldStates.value.username)
            validatePassword(fieldStates.value.password)
            validateConfirmPassword(
                confirmPassword = fieldStates.value.confirmPassword,
                password = fieldStates.value.password
            )
        }
    }
}
