package com.example.profilekeeper.data.repository

import com.example.profilekeeper.data.local.UserDao
import com.example.profilekeeper.data.model.UserEntity
import com.example.profilekeeper.domain.model.UserModel
import com.example.profilekeeper.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import com.google.common.truth.Truth.assertThat

@RunWith(MockitoJUnitRunner::class)
class RegisterRepositoryImplTest {

    @Mock
    lateinit var userDao: UserDao

    private lateinit var registerRepository: RegisterRepository

    @Before
    fun setUp() {
        registerRepository = RegisterRepositoryImpl(userDao)
    }

    // --------------------------------
    // 1) insertUser
    // --------------------------------
    @Test
    fun `insertUser should call userDao_insertUser with mapped entity`() = runTest {
        // Arrange
        val domainUser = UserModel(
            id = null,
            username = "JohnDoe",
            age = 25,
            jobTitle = "Developer"
        )

        // Act
        registerRepository.insertUser(domainUser)

        // Assert
        argumentCaptor<UserEntity>().apply {
            // Verify DAO call & capture entity argument
            verify(userDao, times(1)).insertUser(capture())
            val capturedEntity = firstValue

            // Compare the captured entity's fields to the domain user
            assertThat(capturedEntity.id).isNull() // expecting auto-generate
            assertThat(capturedEntity.username).isEqualTo(domainUser.username)
            assertThat(capturedEntity.age).isEqualTo(domainUser.age)
            assertThat(capturedEntity.jobTitle).isEqualTo(domainUser.jobTitle)
        }
    }

    // --------------------------------
    // 2) deleteUser
    // --------------------------------
    @Test
    fun `deleteUser should call userDao_deleteUser with mapped entity`() = runTest {
        // Arrange
        val domainUser = UserModel(
            id = 1,
            username = "Alice",
            age = 30,
            jobTitle = "HR Manager"
        )

        // Act
        registerRepository.deleteUser(domainUser)

        // Assert
        argumentCaptor<UserEntity>().apply {
            verify(userDao, times(1)).deleteUser(capture())
            val capturedEntity = firstValue

            assertThat(capturedEntity.id).isEqualTo(domainUser.id)
            assertThat(capturedEntity.username).isEqualTo(domainUser.username)
            assertThat(capturedEntity.age).isEqualTo(domainUser.age)
            assertThat(capturedEntity.jobTitle).isEqualTo(domainUser.jobTitle)
        }
    }

    // --------------------------------
    // 3) getAllUsers
    // --------------------------------
    @Test
    fun `getAllUsers should return domain models mapped from entities`() = runTest {
        // Arrange
        val userEntity1 = UserEntity(
            id = 1,
            username = "User1",
            age = 20,
            jobTitle = "Junior Dev"
        )
        val userEntity2 = UserEntity(
            id = 2,
            username = "User2",
            age = 35,
            jobTitle = "Senior Dev"
        )
        // Mock DAO to return a flow of [userEntity1, userEntity2]
        whenever(userDao.getAllUsers()).thenReturn(flowOf(listOf(userEntity1, userEntity2)))

        // Act
        val result = registerRepository.getAllUsers().first()

        // Assert
        assertThat(result).hasSize(2)

        val firstUser = result[0]
        val secondUser = result[1]

        assertThat(firstUser.id).isEqualTo(userEntity1.id)
        assertThat(firstUser.username).isEqualTo(userEntity1.username)
        assertThat(firstUser.age).isEqualTo(userEntity1.age)
        assertThat(firstUser.jobTitle).isEqualTo(userEntity1.jobTitle)

        assertThat(secondUser.id).isEqualTo(userEntity2.id)
        assertThat(secondUser.username).isEqualTo(userEntity2.username)
        assertThat(secondUser.age).isEqualTo(userEntity2.age)
        assertThat(secondUser.jobTitle).isEqualTo(userEntity2.jobTitle)
    }
}
