package com.example.profilekeeper.domain.usecase.validation

import com.example.profilekeeper.domain.usecase.validation.core.BaseValidationUseCase
import com.example.profilekeeper.domain.usecase.validation.core.ValidationErrorType
import com.example.profilekeeper.domain.usecase.validation.core.ValidationResult
import javax.inject.Inject

class ValidateAgeUseCase @Inject constructor() : BaseValidationUseCase<Int>() {

    override fun validate(input: Int): ValidationResult {
        // 1) Check if the age is 0 => FieldEmpty
        if (input == 0) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.FieldEmpty
            )
        }

        // 2) Check if negative => InvalidFormat
        if (input < 0) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.InvalidFormat
            )
        }

        // 3) Check if less than 18 => InvalidFormat
        if (input < 18) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.InvalidFormat
            )
        }

        // 4) Check if above 100 => InvalidFormat (edge-case threshold)
        if (input > 100) {
            return ValidationResult(
                isSuccessful = false,
                errorType = ValidationErrorType.InvalidFormat
            )
        }

        // If all checks pass, it's valid
        return ValidationResult(isSuccessful = true)
    }
}
