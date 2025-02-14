// ValidateJobTitleUseCaseTest.kt
package com.example.profilekeeper.domain.usecase.validation

import com.example.profilekeeper.domain.usecase.validation.core.ValidationErrorType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ValidateJobTitleUseCaseTest {

    private lateinit var validateJobTitleUseCase: ValidateJobTitleUseCase

    @Before
    fun setUp() {
        validateJobTitleUseCase = ValidateJobTitleUseCase()
    }

    @Test
    fun `given an empty job title when validating then return FieldEmpty error`() {
        // Arrange
        val jobTitle = ""

        // Act
        val result = validateJobTitleUseCase.validate(jobTitle)

        // Assert
        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.FieldEmpty, result.errorType)
    }

    @Test
    fun `given a job title with only spaces when validating then return FieldEmpty error`() {
        // Arrange
        val jobTitle = "   "

        // Act
        val result = validateJobTitleUseCase.validate(jobTitle)

        // Assert
        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.FieldEmpty, result.errorType)
    }

    @Test
    fun `given a job title with digits when validating then return InvalidFormat error`() {
        // Arrange
        val jobTitle = "Engineer123"

        // Act
        val result = validateJobTitleUseCase.validate(jobTitle)

        // Assert
        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.InvalidFormat, result.errorType)
    }

    @Test
    fun `given a job title with special characters when validating then return InvalidFormat error`() {
        // Arrange
        val jobTitle = "Software-Engineer!"

        // Act
        val result = validateJobTitleUseCase.validate(jobTitle)

        // Assert
        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.InvalidFormat, result.errorType)
    }

    @Test
    fun `given a valid job title when validating then return success`() {
        // Arrange
        val jobTitle = "Software Engineer"

        // Act
        val result = validateJobTitleUseCase.validate(jobTitle)

        // Assert
        assertEquals(true, result.isSuccessful)
        assertEquals(null, result.errorType)
    }
}
