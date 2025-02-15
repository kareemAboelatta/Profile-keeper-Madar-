package com.example.profilekeeper.domain.usecase

import com.example.profilekeeper.domain.model.UserModel
import com.example.profilekeeper.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val repository: RegisterRepository
) {
    operator fun invoke(): Flow<List<UserModel>> {
        return repository.getAllUsers()
    }
}
