// ValidateUsernameUseCaseTest.kt
package com.example.profilekeeper.domain.usecase.validation

import com.example.profilekeeper.domain.usecase.validation.core.ValidationErrorType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ValidateUsernameUseCaseTest {

    private lateinit var validateUsernameUseCase: ValidateUsernameUseCase

    @Before
    fun setUp() {
        validateUsernameUseCase = ValidateUsernameUseCase()
    }

    @Test
    fun `given an empty username when validating then return FieldEmpty error`() {
        // Arrange
        val username = ""

        // Act
        val result = validateUsernameUseCase.validate(username)

        // Assert
        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.FieldEmpty, result.errorType)
    }

    @Test
    fun `given a username containing digits when validating then return InvalidFormat error`() {
        // Arrange
        val username = "John123"

        // Act
        val result = validateUsernameUseCase.validate(username)

        // Assert
        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.InvalidFormat, result.errorType)
    }

    @Test
    fun `given a username containing special characters when validating then return InvalidFormat error`() {
        // Arrange
        val username = "John_Doe!"

        // Act
        val result = validateUsernameUseCase.validate(username)

        // Assert
        assertEquals(false, result.isSuccessful)
        assertEquals(ValidationErrorType.InvalidFormat, result.errorType)
    }

    @Test
    fun `given a valid username when validating then return success`() {
        // Arrange
        val username = "JohnDoe"

        // Act
        val result = validateUsernameUseCase.validate(username)

        // Assert
        assertEquals(true, result.isSuccessful)
        assertEquals(null, result.errorType)
    }
}
