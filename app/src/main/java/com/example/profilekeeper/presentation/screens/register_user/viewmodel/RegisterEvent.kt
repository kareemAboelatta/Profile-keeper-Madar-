package com.example.profilekeeper.presentation.screens.register_user.viewmodel

sealed class RegisterEvent {
    data object Idle : RegisterEvent()


    data class OnNameChanged(val name: String) : RegisterEvent()
    data class OnAgeChanged(val age: String) : RegisterEvent()
    data class OnJobTitleChanged(val jobTitle: String) : RegisterEvent()

    data object Submit : RegisterEvent()


}
