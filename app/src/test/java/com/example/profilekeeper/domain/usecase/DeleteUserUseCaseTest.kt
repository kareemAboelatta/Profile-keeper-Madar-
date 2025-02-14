package com.example.profilekeeper.domain.usecase

import com.example.profilekeeper.domain.model.UserModel
import com.example.profilekeeper.domain.repository.RegisterRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class DeleteUserUseCaseTest {

    @Mock
    lateinit var repository: RegisterRepository

    private lateinit var deleteUserUseCase: DeleteUserUseCase

    @Before
    fun setUp() {
        deleteUserUseCase = DeleteUserUseCase(repository)
    }

    @Test
    fun `when invoke with valid user then calls deleteUser in repository`() = runTest {
        // Arrange
        val user = UserModel(
            id = 1,
            username = "Kareem",
            age = 22,
            jobTitle = "Android Developer"
        )

        // Act
        deleteUserUseCase.invoke(user)

        // Assert
        verify(repository, times(1)).deleteUser(user)
    }
}
