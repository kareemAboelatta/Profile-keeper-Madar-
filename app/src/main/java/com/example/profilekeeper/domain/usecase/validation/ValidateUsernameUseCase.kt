package com.example.profilekeeper.domain.usecase.validation

import com.example.profilekeeper.domain.usecase.validation.core.BaseValidationUseCase
import com.example.profilekeeper.domain.usecase.validation.core.ValidationErrorType
import com.example.profilekeeper.domain.usecase.validation.core.ValidationResult
import javax.inject.Inject

class ValidateUsernameUseCase @Inject constructor() : BaseValidationUseCase<String>() {

    override fun validate(input: String): ValidationResult {
        // 1) Check if empty => FieldEmpty
        if (input.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.FieldEmpty
            )
        }

        // 2) Check if username contains any digits => InvalidFormat
        if (input.any { it.isDigit() }) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.InvalidFormat
            )
        }

        // 3) Check if username contains special characters => InvalidFormat
        //    (We consider anything non-letter a special character for demonstration.)
        if (
            input.any {
                (it.isLetter() or it.isWhitespace()).not()
            }
        ) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.InvalidFormat
            )
        }

        // If all checks pass, it's valid
        return ValidationResult(isSuccessful = true)
    }
}
