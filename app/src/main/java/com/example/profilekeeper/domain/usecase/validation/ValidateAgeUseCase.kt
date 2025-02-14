package com.example.profilekeeper.domain.usecase.validation

import com.example.profilekeeper.domain.usecase.validation.core.BaseValidationUseCase
import com.example.profilekeeper.domain.usecase.validation.core.ValidationErrorType
import com.example.profilekeeper.domain.usecase.validation.core.ValidationResult
import javax.inject.Inject


class ValidateAgeUseCase @Inject constructor() : BaseValidationUseCase<Int>() {

    override fun validate(input: Int): ValidationResult {
        // TODO: Implement Age validation logic
        // For now, just return a placeholder
        return ValidationResult(
            isSuccessful = false,
            errorType = ValidationErrorType.FieldEmpty
        )
    }
}