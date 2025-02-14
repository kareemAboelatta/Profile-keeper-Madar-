package com.example.profilekeeper.domain.usecase

import com.example.profilekeeper.domain.model.UserModel
import com.example.profilekeeper.domain.repository.RegisterRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: RegisterRepository
) {

    suspend operator fun invoke(user: UserModel) {
        repository.deleteUser(user)
    }
}
