package com.example.profilekeeper.data.mapper

import com.example.profilekeeper.data.model.UserEntity
import com.example.profilekeeper.domain.model.UserModel

fun UserModel.toUserEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        username = this.username,
        age = this.age,
        jobTitle= this.jobTitle
    )
}

fun UserEntity.toUserModel(): UserModel {
    return UserModel(
        id = this.id,
        username = this.username,
        age = this.age,
        jobTitle= this.jobTitle
    )
}
