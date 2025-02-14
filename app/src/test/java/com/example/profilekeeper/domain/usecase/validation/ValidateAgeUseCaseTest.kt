// ValidateAgeUseCaseTest.kt
package com.example.profilekeeper.domain.usecase.validation

import com.example.profilekeeper.domain.usecase.validation.core.ValidationErrorType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ValidateAgeUseCaseTest {

    private lateinit var validateAgeUseCase: ValidateAgeUseCase

    @Before
    fun setUp() {
        validateAgeUseCase = ValidateAgeUseCase()
    }

    @Test
    fun `given age is 0 when validating then return FieldEmpty error`() {
        // Arrange
        val ageInput = 0

        // Act
        val result = validateAgeUseCase.validate(ageInput)

        // Assert
        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.FieldEmpty, result.errorType)
    }

    @Test
    fun `given negative age when validating then return InvalidFormat error`() {
        // Arrange
        val ageInput = -1

        // Act
        val result = validateAgeUseCase.validate(ageInput)

        // Assert
        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.InvalidFormat, result.errorType)
    }

    @Test
    fun `given age less than 18 when validating then return InvalidFormat error`() {
        // Arrange
        val ageInput = 17

        // Act
        val result = validateAgeUseCase.validate(ageInput)

        // Assert
        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.InvalidFormat, result.errorType)
    }

    @Test
    fun `given age above 100 when validating then return InvalidFormat error`() {
        // Arrange
        val ageInput = 150

        // Act
        val result = validateAgeUseCase.validate(ageInput)

        // Assert
        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.InvalidFormat, result.errorType)
    }

    @Test
    fun `given a valid age within acceptable range when validating then return success`() {
        // Arrange
        val ageInput = 25

        // Act
        val result = validateAgeUseCase.validate(ageInput)

        // Assert
        assertEquals(true, result.isSuccessful)
        assertEquals(null, result.errorType)
    }
}
