package com.example.profilekeeper.domain.usecase.validation

import com.example.profilekeeper.domain.usecase.validation.core.BaseValidationUseCase
import com.example.profilekeeper.domain.usecase.validation.core.ValidationErrorType
import com.example.profilekeeper.domain.usecase.validation.core.ValidationResult
import javax.inject.Inject



class ValidateUsernameUseCase @Inject constructor() : BaseValidationUseCase<String>() {

    override fun validate(input: String): ValidationResult {
        // TODO: Implement username validation logic
        // Placeholder
        return ValidationResult(
            isSuccessful = false,
            errorType = ValidationErrorType.FieldEmpty
        )
    }
}