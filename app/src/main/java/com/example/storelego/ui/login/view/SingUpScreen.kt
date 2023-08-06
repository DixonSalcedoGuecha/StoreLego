package com.example.storelego.ui.login.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.storelego.ui.login.viewmodel.LoginViewModel
import com.example.storelego.ui.navigation.Routes
import com.example.storelego.ui.theme.Purple40

@Composable
fun SingUpScreen(viewModel: LoginViewModel, navigate: NavController) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        SingUp(navigate, viewModel)

    }

}

@Composable
fun SingUp(navigate: NavController, viewModel: LoginViewModel) {
    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val buttonEnabled: Boolean by viewModel.loginEnable.observeAsState(initial = false)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {


        Text(
            text = "Crear cuenta",
            style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Purple40)
        )
        Spacer(modifier = Modifier.padding(30.dp))
        EmailField(email) { viewModel.onLoginChanged(it, password) }
        Spacer(modifier = Modifier.padding(20.dp))
        PasswordField(password) { viewModel.onLoginChanged(email, it) }
        Spacer(modifier = Modifier.height(40.dp))
        CreateAccountButton(navigate, buttonEnabled, viewModel)


    }
}

@Composable
fun CreateAccountButton(
    navigate: NavController,
    buttonEnabled: Boolean,
    viewModel: LoginViewModel
) {
    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
        Button(
            onClick = { viewModel.createUserEmailPassword { navigate.navigate(Routes.LoginScreen.route) } },
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), enabled = buttonEnabled
        ) {
            Text(text = "Registrarse")
        }
    }
}
