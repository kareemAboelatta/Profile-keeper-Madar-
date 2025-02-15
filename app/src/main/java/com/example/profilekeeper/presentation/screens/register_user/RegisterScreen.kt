package com.example.profilekeeper.presentation.screens.register_user

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.profilekeeper.presentation.core.components.DefaultTextField
import com.example.profilekeeper.presentation.core.components.getErrorText
import com.example.profilekeeper.presentation.core.navigation.LocalNavController
import com.example.profilekeeper.presentation.core.navigation.Screens
import com.example.profilekeeper.presentation.screens.register_user.viewmodel.RegisterEvent
import com.example.profilekeeper.presentation.screens.register_user.viewmodel.RegisterViewModel
import com.example.profilekeeper.presentation.screens.register_user.viewmodel.RegistrationUiEffect
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController = LocalNavController.current,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    // Collect side effects (UI events) from shared view model
    LaunchedEffect(Unit) {
        viewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                is RegistrationUiEffect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                RegistrationUiEffect.UserDataSaved -> {
                    Toast.makeText(context, "User data saved successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Register User") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Intro message
            Text(
                text = "Please enter your details below to continue",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 10.dp)
            )

            // Full Name Field
            DefaultTextField(
                keyboardType =KeyboardType.Text,
                hint = "Name",
                textInputHint = "e.g. John Doe (letters only)",
                textState = viewModel.state.name,
                error = getErrorText(
                    errorItem = viewModel.state.nameError,
                    fieldName = "Name",
                    invalidFormatMsg = "Name must contain letters only (no digits)."
                )
            ) { newValue ->
                viewModel.onEvent(RegisterEvent.OnNameChanged(newValue))
            }

            // Email Field
            DefaultTextField(
                keyboardType = KeyboardType.Number ,
                hint = "Age",
                textInputHint = "e.g. 22",
                textState = viewModel.state.age,
                error = getErrorText(
                    errorItem = viewModel.state.ageError,
                    fieldName = "Age",
                    invalidFormatMsg = "Please enter a valid Age (18 ~ 100)."
                )
            ) { newValue ->
                viewModel.onEvent(RegisterEvent.OnAgeChanged(newValue))
            }


            // Phone Field
            DefaultTextField(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                hint = "Job Title",
                textInputHint = "e.g. Android Developer",
                textState = viewModel.state.jobTitle,
                error = getErrorText(
                    errorItem = viewModel.state.jobTitleError,
                    fieldName = "Job Title",
                    invalidFormatMsg = "Job Title must contain letters only."
                )
            ) { newValue ->
                viewModel.onEvent(RegisterEvent.OnJobTitleChanged(newValue))
            }


            Button(
                onClick = {
                    viewModel.onEvent(RegisterEvent.Submit)
                },
                modifier = Modifier.fillMaxWidth().padding(top = 50.dp)
            ) {
                Text(text = "Register")
            }

            Button(
                onClick = {
                    navController.navigate(Screens.UsersListScreen)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(text = "Registered users")
            }
        }
    }
}
