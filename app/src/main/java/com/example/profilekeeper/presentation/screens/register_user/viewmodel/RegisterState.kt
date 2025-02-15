package com.example.profilekeeper.presentation.screens.register_user.viewmodel

import com.example.profilekeeper.domain.usecase.validation.core.ValidationResult

data class  RegisterState(

    val name: String = "",
    val nameError: ValidationResult? = null,

    val age: String = "",
    val ageError: ValidationResult? = null,

    val jobTitle: String = "",
    val jobTitleError: ValidationResult? = null,

)