package com.example.profilekeeper.presentation.screens.user_list.viewmodel

import com.example.profilekeeper.domain.model.UserModel

data class UsersListState(
    val users: List<UserModel> = emptyList(),
    val isLoading: Boolean = false
)
