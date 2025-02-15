package com.example.profilekeeper.presentation.screens.register_user.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.profilekeeper.domain.model.UserModel
import com.example.profilekeeper.domain.usecase.RegisterUseCase
import com.example.profilekeeper.domain.usecase.validation.ValidateAgeUseCase
import com.example.profilekeeper.domain.usecase.validation.ValidateJobTitleUseCase
import com.example.profilekeeper.domain.usecase.validation.ValidateUsernameUseCase
import com.example.profilekeeper.presentation.core.base.BaseViewModel
import com.example.profilekeeper.presentation.core.base.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject



sealed class RegistrationUiEffect : UiEffect() {
    data object UserDataSaved : RegistrationUiEffect()  // signals that all steps are complete
    data class ShowError(val message: String) : RegistrationUiEffect()
}


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateNameUseCase: ValidateUsernameUseCase,
    private val validateAgeUseCase: ValidateAgeUseCase,
    private val validateJobTitleUseCase: ValidateJobTitleUseCase,
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<RegisterState, RegisterEvent>(RegisterState(), RegisterEvent.Idle) {

    override fun onEvent(event: RegisterEvent) {
        when (event) {
            RegisterEvent.Idle -> {}
            is RegisterEvent.OnJobTitleChanged -> {
                state = state.copy(jobTitle = event.jobTitle, jobTitleError = null)
            }
            is RegisterEvent.OnNameChanged -> {
                state = state.copy(name = event.name, nameError = null)
            }
            is RegisterEvent.OnAgeChanged -> {
                state = state.copy(age = event.age, ageError = null)
            }
            RegisterEvent.Submit -> {
                onSubmit()
            }
        }
    }

    private fun onSubmit() {
        if (validateData()) {
            // Pass the validated data explicitly
            val validatedUsername = state.name
            val validatedAge = state.age.toIntOrNull() ?: 0
            val validatedJobTitle = state.jobTitle
            saveUserData(validatedUsername, validatedAge, validatedJobTitle)
        }
    }

    // Returns true if validation passes; updates state with errors otherwise.
    private fun validateData(): Boolean {
        val nameResult = validateNameUseCase.validate(state.name)
        val ageResult = validateAgeUseCase.validate(state.age.toIntOrNull() ?: 0)
        val jobTitleResult = validateJobTitleUseCase.validate(state.jobTitle)

        val validations = listOf(nameResult, ageResult, jobTitleResult)
        val validationFailed = validations.any { !it.isSuccessful }

        if (validationFailed) {
            state = state.copy(
                nameError = nameResult,
                ageError = ageResult,
                jobTitleError = jobTitleResult,
            )
        } else {
            state = state.copy(
                nameError = null,
                ageError = null,
                jobTitleError = null
            )
        }
        return validationFailed.not()
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("RegisterViewModel", "Error saving user data: ", throwable)
        setEffect { RegistrationUiEffect.ShowError("Error saving user data: ${throwable.message.orEmpty()}") }
    }

    private fun saveUserData(username: String, age: Int, jobTitle: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val user = UserModel(username = username, age = age, jobTitle = jobTitle)
            val rowId = registerUseCase(user)

            if (rowId > 0) {
                Log.d("RegisterViewModel", "User inserted with id = $rowId")
                setEffect { RegistrationUiEffect.UserDataSaved }
                state = RegisterState()

            } else {
                setEffect { RegistrationUiEffect.ShowError("User insertion failed or replaced, rowId = $rowId") }
            }
        }
    }
}




