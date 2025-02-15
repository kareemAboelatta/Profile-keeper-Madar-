
package com.example.profilekeeper.domain.usecase

import com.example.profilekeeper.domain.model.UserModel
import com.example.profilekeeper.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetAllUsersUseCaseTest {

    @Mock
    lateinit var repository: RegisterRepository

    private lateinit var getAllUsersUseCase: GetAllUsersUseCase

    @Before
    fun setUp() {
        getAllUsersUseCase = GetAllUsersUseCase(repository)
    }

    @Test
    fun `when invoke then calls getAllUsers in repository and returns flow`() = runTest {
        // Arrange
        val mockUsers = listOf(
            UserModel(
                id = 1,
                username = "Kareem Aboelatta",
                age = 22,
                jobTitle = "Android Developer"
            ),
            UserModel(
                id = 2,
                username = "Jane",
                age = 32,
                jobTitle = "HR"
            )
        )
        val expectedFlow: Flow<List<UserModel>> = flowOf(mockUsers)

        // Stub repository response
        whenever(repository.getAllUsers()).thenReturn(expectedFlow)

        // Act
        val resultFlow = getAllUsersUseCase()
        val resultList = resultFlow.first() // collect first emission

        // Assert
        verify(repository, times(1)).getAllUsers()
        assertEquals(mockUsers.size, resultList.size)
        assertEquals(mockUsers[0].username, resultList[0].username)
        assertEquals(mockUsers[1].jobTitle, resultList[1].jobTitle)
    }
}
