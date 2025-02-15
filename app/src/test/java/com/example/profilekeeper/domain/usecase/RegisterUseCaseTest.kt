package com.example.profilekeeper.domain.usecase

import com.example.profilekeeper.domain.model.UserModel
import com.example.profilekeeper.domain.repository.RegisterRepository
import com.example.profilekeeper.domain.usecase.RegisterUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class RegisterUseCaseTest {

    @Mock
    lateinit var repository: RegisterRepository

    private lateinit var registerUseCase: RegisterUseCase

    @Before
    fun setUp() {
        registerUseCase = RegisterUseCase(repository)
    }

    @Test
    fun `when invoke with valid user then calls insertUser in repository`() = runTest {
        // Arrange
        val user = UserModel(
            id = null,
            username = "Kareem",
            age = 22,
            jobTitle = "Android Developer"
        )
        // Act
        registerUseCase.invoke(user)
        // Assert
        verify(repository, times(1)).insertUser(user)
    }

}