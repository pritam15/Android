package com.example.jetreader.screens.login

import android.app.appsearch.AppSearchSchema
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetreader.R
import com.example.jetreader.components.EmailInput
import com.example.jetreader.components.PasswordInput
import com.example.jetreader.components.ReaderLogo
import com.example.jetreader.navigation.AppScreens

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val showLoginForm = remember {
        mutableStateOf(true)
    }
  Surface(modifier = Modifier.fillMaxSize()) {
      Column(verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            ReaderLogo(modifier = Modifier.padding(16.dp))

          if(showLoginForm.value){
              UserForm(loading = false, isCreateAccount = false){ email , pwd ->
                  // Login FB Account
                  viewModel.signInWithEmailAndPassword(email, pwd){
                      navController.navigate(AppScreens.HomeScreen.name)
                  }
              }
          }
          else{
              UserForm(loading = false, isCreateAccount = true, ){ email,password ->
                  // Create FB Account
                  viewModel.createUserWithEmailAndPassword(email,password){
                      navController.navigate(AppScreens.HomeScreen.name)
                  }
              }
          }



      }
      
      Spacer(modifier = Modifier.height(15.dp))
      
      Row(
          modifier = Modifier.padding(15.dp),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
      ) {
        
          val text = if(showLoginForm.value) "Sign Up" else "Login"
          Text(text = "New User?")
          Text(text = text,
           modifier = Modifier
               .clickable {
                   showLoginForm.value = !showLoginForm.value
               }
               .padding(5.dp),
          color = MaterialTheme.colors.secondaryVariant,
          fontWeight = FontWeight.Bold)

      }
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { email, pwd ->}
){
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("")}
    val passwordVisibality = rememberSaveable { mutableStateOf(value = false)}
    val FocuseRequest = FocusRequester.Default
    val keyBoardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value,password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }   

    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState())
    
    if(isCreateAccount) Text(text = stringResource(id = R.string.createAcc),
            modifier = Modifier.padding(4.dp))

    Column(modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally) {

        EmailInput(emailState = email,
                enabled = !loading,
                onAction = KeyboardActions{
                    FocuseRequest.requestFocus()
                }
        )
        PasswordInput(
            modifier = Modifier.focusRequester(FocuseRequest) ,
            passwordState = password,
            labelId = "Password",
            enabled = !loading,
            passwordVisibility = passwordVisibality
        )

        SumbitBtn( textId =  if(isCreateAccount) "Create Account" else "Login",
                    loading = loading,
                    validInputs = valid,
                    ){
            onDone(email.value.trim(), password.value.trim())
            keyBoardController?.hide()
        }

        
    }
}

@Composable
fun SumbitBtn(textId: String,
              loading: Boolean,
              validInputs: Boolean,
              onClick : () -> Unit) {

    Button(onClick = onClick,
            modifier = Modifier
                .padding(3.dp)
                .fillMaxWidth(),
            enabled = !loading && validInputs,
            shape = CircleShape
            ){
        if(loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId)

    }
}
