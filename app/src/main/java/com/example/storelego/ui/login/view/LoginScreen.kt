package com.example.storelego.ui.login.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.storelego.ui.theme.Purple40
import com.example.storelego.R
import com.example.storelego.ui.login.viewmodel.LoginViewModel
import com.example.storelego.ui.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@ExperimentalMaterial3Api
@Composable
fun LoginScreen(viewModel: LoginViewModel, navigate: NavController) {


    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Login(navigate, viewModel)

    }

}

@Composable
fun Login(navigate: NavController, viewModel: LoginViewModel) {

    val email : String by  viewModel.email.observeAsState(initial = "")
    val password : String by  viewModel.password.observeAsState(initial = "")
    val loginEnable : Boolean by  viewModel.loginEnable.observeAsState(initial = false)

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
        LoginButton( navigate, loginEnable, viewModel)
        Spacer(modifier = Modifier.height(30.dp))
        ForgotPassword()
        Spacer(modifier = Modifier.height(25.dp))
        Text(text = "--------- o ---------")
        Spacer(modifier = Modifier.height(25.dp))
        SocialMediaButton(
            onClick = {

            },
            text = "Continuar con Google",
            icon = R.drawable.ic_google,
            color = Color.Black
        )
        UserRegister(navigate)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialMediaButton(onClick: () -> Unit, text: String, icon: Int, color: Color, ) {
    var click by remember { mutableStateOf(false) }
    Surface(
        onClick = onClick,
        modifier = Modifier.padding(start = 40.dp, end = 40.dp).clickable { click = !click },
        shape = RoundedCornerShape(50),
        border = BorderStroke(width = 1.dp, color = Color.Gray),
        color = color
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                modifier = Modifier.size(24.dp),
                contentDescription = text,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "$text", color = Color.White)
            click = true
        }
    }
}

@Composable
fun UserRegister(navigate: NavController) {
    ClickableText(
        text = AnnotatedString("¿No tienes una cuenta?, Registrate"),
        modifier = Modifier
            .padding(50.dp),
        onClick = {
            navigate.navigate(Routes.SingUpScreen.route)
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
    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
        Button(
            onClick = { viewModel.singIn{navigate.navigate(Routes.HomeScreen.route)} },
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), enabled = loginEnabled
        ) {
            Text(text = "Iniciar Sesión")
        }
    }
}

@Composable
fun ForgotPassword() {
    ClickableText(
        text = AnnotatedString("¿Olvidaste tu contraseña?"),
        onClick = {},
        style = TextStyle(
            fontSize = 14.sp,
            fontFamily = FontFamily.Default,
            textDecoration = TextDecoration.Underline,
            color = Purple40
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(password : String , onTextFieldChanged: (String) -> Unit) {
    TextField(
        label = { Text(text = "Contraseña") },
        value = password,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = {onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(email : String , onTextFieldChanged: (String) -> Unit) {


    TextField(
        label = { Text(text = "Correo Electronico") },
        value = email,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        onValueChange = {onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors()
    )
}


@Composable
fun HeaderImage() {
    Image(painter = painterResource(id = R.drawable.header_login), contentDescription = "Header" )
}
