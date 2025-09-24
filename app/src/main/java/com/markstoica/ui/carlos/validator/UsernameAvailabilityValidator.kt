package com.markstoica.ui.carlos.validator

import com.markstoica.R
import hu.icellmobilsoft.carlosformito.core.api.validator.FormFieldValidationResult
import hu.icellmobilsoft.carlosformito.core.api.validator.FormFieldValidator
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class UsernameAvailabilityValidator(
    private val errorMessageId: Int = R.string.username_taken_error
) : FormFieldValidator<String> {

    // Mock taken usernames
    private val takenUsernames = listOf(
        "username",
        "demo",
        "guest",
        "john_doe",
        "admin"
    )

    override suspend fun validate(value: String?): FormFieldValidationResult {
        // Faking network delay
        delay(1.seconds)

        return if (value in takenUsernames) {
            FormFieldValidationResult.Invalid.of(errorMessageId)
        } else {
            FormFieldValidationResult.Valid
        }
    }
}
