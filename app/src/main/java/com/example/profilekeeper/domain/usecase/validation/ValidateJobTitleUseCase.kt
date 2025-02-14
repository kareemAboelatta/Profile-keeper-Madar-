package com.example.profilekeeper.domain.usecase.validation

import com.example.profilekeeper.domain.usecase.validation.core.BaseValidationUseCase
import com.example.profilekeeper.domain.usecase.validation.core.ValidationErrorType
import com.example.profilekeeper.domain.usecase.validation.core.ValidationResult
import javax.inject.Inject

class ValidateJobTitleUseCase @Inject constructor() : BaseValidationUseCase<String>() {

    override fun validate(input: String): ValidationResult {
        // 1) Check if empty => FieldEmpty
        if (input.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.FieldEmpty
            )
        }

        // 2) Check for digits => InvalidFormat
        if (input.any { it.isDigit() }) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.InvalidFormat
            )
        }

        // 3) Check for special characters => InvalidFormat
        //    (Allow only letters and spaces, for example)
        if (input.any { !(it.isLetter() || it.isWhitespace()) }) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.InvalidFormat
            )
        }

        // If all checks pass, it's valid
        return ValidationResult(isSuccessful = true)
    }
}
