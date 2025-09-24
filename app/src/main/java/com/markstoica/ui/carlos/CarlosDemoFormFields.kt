package com.markstoica.ui.carlos

import com.markstoica.R
import com.markstoica.ui.carlos.validator.UsernameAvailabilityValidator
import hu.icellmobilsoft.carlosformito.core.api.model.FormField
import hu.icellmobilsoft.carlosformito.core.api.model.FormFieldValidationStrategy
import hu.icellmobilsoft.carlosformito.core.validator.ContainsSpecialCharacterValidator
import hu.icellmobilsoft.carlosformito.core.validator.TextMinLengthValidator
import hu.icellmobilsoft.carlosformito.core.validator.ValueRequiredValidator
import hu.icellmobilsoft.carlosformito.core.validator.connections.EqualsToValidator
import kotlin.time.Duration.Companion.seconds

object CarlosDemoFormFields {

    const val USERNAME = "USERNAME"
    const val PASSWORD = "PASSWORD"
    const val CONFIRM_PASSWORD = "CONFIRM_PASSWORD"

    fun build(): List<FormField<*>> = listOf(
        FormField(
            id = USERNAME,
            validators = listOf(
                ValueRequiredValidator(errorMessageId = R.string.username_required_error),
                TextMinLengthValidator(errorMessageId = R.string.username_length_error, minLength = 4),
                UsernameAvailabilityValidator()
            ),
            customValidationStrategy = FormFieldValidationStrategy.AutoInline(delay = 1.seconds)
        ),
        FormField(
            id = PASSWORD,
            validators = listOf(
                ValueRequiredValidator(errorMessageId = R.string.password_required_error),
                TextMinLengthValidator(errorMessageId = R.string.password_length_error, minLength = 8),
                ContainsSpecialCharacterValidator(errorMessageId = R.string.password_special_char_missing_error)
            )
        ),
        FormField(
            id = CONFIRM_PASSWORD,
            validators = listOf(
                EqualsToValidator(
                    connectedFieldId = PASSWORD,
                    errorMessageId = R.string.confirm_password_error
                )
            )
        )
    )
}
