package fi.tuni.sportsconnect.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fi.tuni.sportsconnect.R
import fi.tuni.sportsconnect.ui.theme.Dark
import fi.tuni.sportsconnect.ui.theme.White
import fi.tuni.sportsconnect.viewModels.PlayerProfileViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PlayerProfileScreen(
    openAndPopUp: (String, String) -> Unit,
    restartApp: (String) -> Unit,
    viewModel: PlayerProfileViewModel = hiltViewModel()
) {
    val playerAccount = viewModel.playerAccount.collectAsState()

    LaunchedEffect(Unit) { viewModel.initialize(restartApp) }

    Scaffold {
        var showSignOutDialog by remember {
            mutableStateOf(false)
        }
        var showDeleteAccountDialog by remember {
            mutableStateOf(false)
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp, 0.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp, 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.no_profile_picture),
                        contentDescription = "No Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "${playerAccount.value.firstName} ${playerAccount.value.lastName}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(10.dp, 5.dp)
                        )
                        if (playerAccount.value.searchingForTeam) {
                            Text(
                                text = "Hakee uutta joukkuetta!",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .padding(10.dp, 5.dp)
                                    .clip(CircleShape)
                                    .border(
                                        border = BorderStroke(2.dp, color = Dark),
                                        shape = CircleShape
                                    )
                                    .padding(5.dp)
                            )
                        }
                    }
                }
            }
            item {
                Text(
                    text = playerAccount.value.currentTeam.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(5.dp)
                )
                Text(
                    text = playerAccount.value.bio.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = { viewModel.onEditProfileClick(openAndPopUp) }) {
                        Text(text = "Muokkaa profiilia")
                    }
                    Row {
                        Button(onClick = { showSignOutDialog = true }) {
                            Text(text = "Kirjaudu ulos")
                        }
                        Button(onClick = { showDeleteAccountDialog = true }) {
                            Text(text = "Poista tilisi")
                        }
                    }
                }
            }
            item {
                Divider(
                    modifier = Modifier.padding(0.dp, 20.dp)
                )
            }
            item {
                Column {
                    Text(
                        text = "Yhteystiedot",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 5.dp)
                        )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Filled.Email, contentDescription = "Email Icon")
                        playerAccount.value.contactInfo?.get("email")?.let {
                            Text(
                                text = it,
                                modifier = Modifier
                                    .padding(5.dp)
                            )
                        }
                    }
                    Row {
                        Icon(imageVector = Icons.Filled.Phone, contentDescription = "Phone Number")
                        playerAccount.value.contactInfo?.get("phoneNumber")?.let {
                            Text(
                                text = it,
                                modifier = Modifier
                                    .padding(5.dp)
                            )
                        }
                    }

                    Text(
                        text = "Paikkakunnat",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(0.dp, 20.dp, 0.dp, 5.dp)
                    )
                    playerAccount.value.cities?.forEach {
                        Text(
                            text = it,
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(White)
                                .border(
                                    border = BorderStroke(2.dp, color = Dark),
                                    shape = CircleShape
                                )
                                .padding(5.dp)
                        )
                    }

                    Text(
                        text = "Sarjatasot",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(0.dp, 20.dp, 0.dp, 5.dp)
                    )
                    playerAccount.value.leagueLevels?.forEach {
                        Text(
                            text = it,
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(White)
                                .border(
                                    border = BorderStroke(2.dp, color = Dark),
                                    shape = CircleShape
                                )
                                .padding(5.dp)
                        )
                    }

                    Text(
                        text = "Pelikaikka/Pelipaikat",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(0.dp, 20.dp, 0.dp, 5.dp)
                    )
                    playerAccount.value.positions?.forEach {
                        Text(
                            text = it,
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(White)
                                .border(
                                    border = BorderStroke(2.dp, color = Dark),
                                    shape = CircleShape
                                )
                                .padding(5.dp)
                        )
                    }

                    Text(
                        text = "Ura",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(0.dp, 20.dp, 0.dp, 0.dp)
                    )
                    Text(
                        text = playerAccount.value.career.orEmpty(),
                        style = MaterialTheme.typography.bodyLarge,
                    )

                    Text(
                        text = "Vahvuudet pelaajana",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(0.dp, 20.dp, 0.dp, 0.dp)
                    )
                    Text(
                        text = playerAccount.value.strengths.orEmpty(),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }

            if (showSignOutDialog) {
                item {
                    AlertDialog(
                        title = { Text(text = "Haluatko kirjautua ulos?") },
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
            }

            if (showDeleteAccountDialog) {
                item {
                    AlertDialog(
                        title = { Text(text = "Haluatko poistaa tilisi?") },
                        text = { Text(text = "Menetät kaikki tietosi ja tilisi poistetaan lopullisesti. Et voi perua toimintoa!") },
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
}