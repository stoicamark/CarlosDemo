package com.markstoica.ui.carlos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.icellmobilsoft.carlosformito.core.CarlosFormManager
import hu.icellmobilsoft.carlosformito.core.api.FormManager
import hu.icellmobilsoft.carlosformito.core.api.model.FormFieldValidationStrategy
import kotlinx.coroutines.launch

class CarlosDemoViewModel : ViewModel(),
    FormManager by CarlosFormManager(
        validationStrategy = FormFieldValidationStrategy.AutoInline(),
        formFields = CarlosDemoFormFields.build()
    ) {

    init {
        viewModelScope.launch {
            initFormManager()
        }
    }

    fun onSubmit() {
        viewModelScope.launch {
            validateForm()
        }
    }
}
