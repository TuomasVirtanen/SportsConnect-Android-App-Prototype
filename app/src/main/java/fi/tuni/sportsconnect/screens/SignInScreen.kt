package fi.tuni.sportsconnect.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fi.tuni.sportsconnect.R
import fi.tuni.sportsconnect.ui.theme.Dark
import fi.tuni.sportsconnect.ui.theme.Violet
import fi.tuni.sportsconnect.ui.theme.White
import fi.tuni.sportsconnect.viewModels.EmailSignInViewModel

@Composable
fun SignInScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EmailSignInViewModel = hiltViewModel()
) {
    val email = viewModel.email.collectAsState()
    val password = viewModel.password.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
                .border(
                    BorderStroke(width = 2.dp, color = Dark),
                    shape = RoundedCornerShape(50)
                ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            value = email.value,
            onValueChange = {viewModel.updateEmail(it)},
            placeholder = {Text(stringResource(R.string.email))},
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email")},
        )

        OutlinedTextField(
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
                .border(
                    BorderStroke(width = 2.dp, color = Dark),
                    shape = RoundedCornerShape(50)
                ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            value = password.value,
            onValueChange = {viewModel.updatePassword(it)},
            placeholder = {Text(stringResource(R.string.password))},
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Email")},
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
        )

        Button(
            onClick = { viewModel.onSignInClick(openAndPopUp) },
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            colors = ButtonColors(Violet, White, Violet, White)
        ) {
            Text(
                text = stringResource(R.string.log_in),
                fontSize = 16.sp,
                modifier = modifier.padding(0.dp, 6.dp)
            )
        }

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp))

        TextButton(onClick = { viewModel.onPlayerSignUpClick(openAndPopUp) }) {
            Text(
                text = stringResource(R.string.player_sign_up),
                fontSize = 16.sp,
                color = Dark
            )
        }

        TextButton(onClick = { viewModel.onClubSignUpClick(openAndPopUp) }) {
            Text(
                text = stringResource(R.string.club_sign_up),
                fontSize = 16.sp,
                color = Dark
            )
        }
    }
}