package com.example.profilekeeper.domain.repository

import com.example.profilekeeper.domain.model.UserModel
import kotlinx.coroutines.flow.Flow


interface RegisterRepository {
    suspend fun insertUser(user: UserModel)
    suspend fun deleteUser(user: UserModel)
    fun getAllUsers(): Flow<List<UserModel>>
}