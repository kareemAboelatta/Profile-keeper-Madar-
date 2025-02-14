package com.example.profilekeeper.domain.model

data class UserModel(
    var id: Int? = null,
    val username: String,
    val age: Int,
    val jobTitle: String,
)