package com.example.storelego.ui.login.view

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.storelego.ui.theme.Purple40
import com.example.storelego.R
import com.example.storelego.ui.login.viewmodel.LoginViewModel
import com.example.storelego.ui.navigation.Routes


@ExperimentalMaterial3Api
@Composable
fun LoginScreen(viewModel: LoginViewModel, navigate: NavController) {

    BackHandler(enabled = false , onBack = { navigate.navigate(Routes.LoginScreen.route) })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Login(navigate, viewModel)

    }

}


@Composable
fun Login(navigate: NavController, viewModel: LoginViewModel) {

    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {


        HeaderImage()
        Spacer(modifier = Modifier.padding(30.dp))
        EmailField(email) { viewModel.onLoginChanged(it, password) }
        Spacer(modifier = Modifier.padding(20.dp))
        PasswordField(password) { viewModel.onLoginChanged(email, it) }
        Spacer(modifier = Modifier.height(40.dp))
        LoginButton(navigate, loginEnable, viewModel)
        Spacer(modifier = Modifier.height(90.dp))
        UserRegister(navigate)

    }
}


@Composable
fun UserRegister(navigate: NavController) {
    ClickableText(
        text = AnnotatedString("¿No tienes una cuenta?, Registrate"),
        modifier = Modifier
            .padding(50.dp),
        onClick = {
            navigate.navigate(Routes.SingUpScreen.route) {
                popUpTo(Routes.SingUpScreen.route) {
                    inclusive = true
                }
            }
        },
        style = TextStyle(
            fontSize = 14.sp,
            fontFamily = FontFamily.Default,
            textDecoration = TextDecoration.Underline,
            color = Purple40
        )
    )
}

@Composable
fun LoginButton(navigate: NavController, loginEnabled: Boolean, viewModel: LoginViewModel) {
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val showDialog: Boolean by viewModel.showDialog.observeAsState(initial = false)

    Box(
        modifier = Modifier
            .padding(25.dp, 0.dp, 25.dp, 0.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (!isLoading) {
            Button(
                onClick = {

                    viewModel.signIn {
                        navigate.navigate(Routes.HomeScreen.route) {
                            popUpTo(Routes.LoginScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp), enabled = loginEnabled
            ) {
                Text(text = "Iniciar Sesión")
            }
        }
        LoadingSignIn(isLoading, showDialog)
    }
}

@Composable
fun LoadingSignIn(isLoading: Boolean, showDialog: Boolean) {

    if (isLoading) {
        CircularProgressIndicator(
            color = Color.LightGray,
            strokeWidth = 5.dp,
            modifier = Modifier.size(70.dp)
        )
    } else if (showDialog) {

        AlertDialog(
            onDismissRequest = { },
            title = { Text("¡Error en tu cuenta!") },
            text = { Text("Si no estas registrado crea una cuenta") },
            confirmButton = {}
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(password: String, onTextFieldChanged: (String) -> Unit) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    TextField(
        label = { Text(text = "Contraseña") },
        value = password,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = { onTextFieldChanged(it) },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                    painterResource(if (isPasswordVisible) R.drawable.ic_visibility_on else R.drawable.ic_visibility_off),
                    contentDescription = if (isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors()
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(email: String, onTextFieldChanged: (String) -> Unit) {


    TextField(
        label = { Text(text = "Correo Electronico") },
        value = email,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors()
    )
}


@Composable
fun HeaderImage() {
    Image(painter = painterResource(id = R.drawable.header_login), contentDescription = "Header")
}
