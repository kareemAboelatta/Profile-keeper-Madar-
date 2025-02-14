// BaseValidationUseCase.kt
package com.example.profilekeeper.domain.usecase.validation.core

abstract class BaseValidationUseCase<T> {

    // Abstract function to be implemented by each validator.
    abstract fun validate(input: T): ValidationResult


}
