package com.example.profilekeeper.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.example.profilekeeper.data.model.UserEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setUp() {
        // Use an in-memory database. Data disappears after the test completes.
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries() // OK for small tests; avoid on production code
            .build()

        userDao = database.userDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertUserAndRetrieveIt() = runTest {
        // Arrange
        val user = UserEntity(
            id = null, // Let Room generate an ID
            username = "JohnDoe",
            age = 30,
            jobTitle = "Android Developer"
        )

        // Act
        userDao.insertUser(user)
        val allUsers = userDao.getAllUsers().first() // Collect the first emission from Flow

        // Assert
        assertThat(allUsers).hasSize(1)
        val insertedUser = allUsers[0]
        assertThat(insertedUser.username).isEqualTo("JohnDoe")
        assertThat(insertedUser.age).isEqualTo(30)
        assertThat(insertedUser.jobTitle).isEqualTo("Android Developer")
    }

    @Test
    fun deleteUserRemovesItFromDB() = runTest {
        // Arrange
        val user = UserEntity(
            id = 1, // Let Room generate an ID
            username = "Alice",
            age = 28,
            jobTitle = "HR Manager"
        )
        userDao.insertUser(user)

        // Act
        userDao.deleteUser(user)
        val allUsers = userDao.getAllUsers().first()

        // Assert
        assertThat(allUsers).isEmpty()
    }

    @Test
    fun insertMultipleUsersAndVerifyCount() = runTest {
        // Arrange
        val user1 = UserEntity(
            id = null, // Let Room generate an ID
            username = "User1",
            age = 25,
            jobTitle = "Developer"
        )
        val user2 = UserEntity(
            id = null, // Let Room generate an ID
            username = "User2",
            age = 35,
            jobTitle = "Manager"
        )

        // Act
        userDao.insertUser(user1)
        userDao.insertUser(user2)
        val allUsers = userDao.getAllUsers().first()

        // Assert
        assertThat(allUsers).hasSize(2)
        val usernames = allUsers.map { it.username }
        assertThat(usernames).containsExactly("User1", "User2")
    }

    @Test
    fun insertDuplicateUser() = runTest {
        // Arrange
        val user = UserEntity(
            id = 1, // Let Room generate an ID
            username = "DuplicateUser",
            age = 40,
            jobTitle = "Engineer"
        )

        // Act
        userDao.insertUser(user)
        userDao.insertUser(user) // Insert the same user again
        val allUsers = userDao.getAllUsers().first()

        // Assert
        assertThat(allUsers).hasSize(1) // Duplicate inserts should be avoided, hence size should remain 1
        val insertedUser = allUsers[0]
        assertThat(insertedUser.username).isEqualTo("DuplicateUser")
        assertThat(insertedUser.age).isEqualTo(40)
    }

}
