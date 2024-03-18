package fi.tuni.sportsconnect.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import fi.tuni.sportsconnect.viewModels.PlayerProfileViewModel

@Composable
fun PlayerProfileScreen(
    openAndPopUp: (String, String) -> Unit,
    restartApp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlayerProfileViewModel = hiltViewModel()
) {
    val playerAccount = viewModel.playerAccount.collectAsState()

    LaunchedEffect(Unit) { viewModel.initialize(restartApp) }

    Scaffold {innerPaddingModifier ->
        var showSignOutDialog by remember {
            mutableStateOf(false)
        }
        var showDeleteAccountDialog by remember {
            mutableStateOf(false)
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPaddingModifier)) {
            Text(text = "Pelaajan profiili")
            Text(text = playerAccount.value.firstName)
            Button(onClick = { viewModel.onEditProfileClick(openAndPopUp) }) {
                Text(text = "Muokkaa profiilia")
            }
            Button(onClick = { showSignOutDialog = true }) {
                Text(text = "Kirjaudu ulos")
            }
            Button(onClick = { showDeleteAccountDialog = true }) {
                Text(text = "Poista tilisi")
            }

            if(showSignOutDialog) {
                AlertDialog(
                    title = { Text(text = "Haluatko kirjautua ulos?")},
                    dismissButton = {
                        Button(onClick = { showSignOutDialog = false }) {
                            Text(text = "Peru")
                        }
                    },
                    onDismissRequest = { showSignOutDialog = false },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.onSignOutClick()
                            showSignOutDialog = false
                        }) {
                            Text(text = "Kirjaudu ulos")
                        }
                    })
            }

            if(showDeleteAccountDialog) {
                AlertDialog(
                    title = { Text(text = "Haluatko poistaa tilisi?") },
                    text = { Text(text = "Menetät kaikki tietosi ja tilisi poistetaan lopullisesti. Et voi perua toimintoa!")},
                    dismissButton = {
                        Button(onClick = { showDeleteAccountDialog = false }) {
                            Text(text = "Peru")
                        }
                    },
                    onDismissRequest = { showDeleteAccountDialog = false },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.onDeleteAccountClick()
                            showDeleteAccountDialog = false
                        }) {
                            Text(text = "Poista tilisi")
                        }
                    })
            }
        }
    }
}