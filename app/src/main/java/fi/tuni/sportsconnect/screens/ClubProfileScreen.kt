package fi.tuni.sportsconnect.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fi.tuni.sportsconnect.R
import fi.tuni.sportsconnect.model.Post
import fi.tuni.sportsconnect.ui.theme.Violet
import fi.tuni.sportsconnect.ui.theme.White
import fi.tuni.sportsconnect.viewModels.ClubProfileViewModel
import kotlinx.coroutines.flow.emptyFlow

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ClubProfileScreen(
    clubId: String,
    openAndPopUp: (String, String) -> Unit,
    restartApp: (String) -> Unit,
    viewModel: ClubProfileViewModel = hiltViewModel()
) {
    val clubAccount = viewModel.clubAccount.collectAsState()
    val showActionButtons = viewModel.showActionButtons.collectAsState()

    LaunchedEffect(Unit) { viewModel.initialize(clubId, restartApp) }

    Scaffold {
        var showSignOutDialog by remember {
            mutableStateOf(false)
        }
        var showDeleteAccountDialog by remember {
            mutableStateOf(false)
        }
        val clubPosts by viewModel.clubPosts.collectAsState(emptyList())
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.no_team_logo),
                        contentDescription = "No Logo",
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
                            text = clubAccount.value.clubName,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(10.dp, 5.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = clubAccount.value.city,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .padding(10.dp, 0.dp)
                            )
                            Text(
                                text = "·",
                                style = TextStyle(
                                    fontSize = 25.sp
                                ),
                                modifier = Modifier
                                    .padding(5.dp, 0.dp)
                            )
                            Text(
                                text = clubAccount.value.leagueLevel,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .padding(10.dp, 0.dp)
                            )
                        }
                    }
                }
            }
            item {
                Text(
                    text = clubAccount.value.bio.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (showActionButtons.value) {
                        Button(onClick = { viewModel.onEditProfileClick(openAndPopUp) },
                            colors = ButtonColors(Violet, White, Violet, White)) {
                            Text(text = "Muokkaa profiilia")
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = { showSignOutDialog = true },
                                colors = ButtonColors(Violet, White, Violet, White)
                            ) {
                                Text(text = "Kirjaudu ulos")
                            }
                            Button(onClick = { showDeleteAccountDialog = true },
                                colors = ButtonColors(Violet, White, Violet, White)) {
                                Text(text = "Poista tilisi")
                            }
                        }
                    }
                }
            }
            item {
                Column {
                    Text(
                        text = "Yhteystiedot",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(10.dp, 5.dp, 0.dp, 0.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Filled.Email, contentDescription = "Email Icon",
                            modifier = Modifier
                                .padding(10.dp, 5.dp, 0.dp, 5.dp))
                        clubAccount.value.contactInfo?.get("email")?.let {
                            Text(
                                text = it,
                                modifier = Modifier
                                    .padding(5.dp)
                            )
                        }
                    }
                    Row {
                        Icon(imageVector = Icons.Filled.Phone, contentDescription = "Phone Number",
                            modifier = Modifier
                                .padding(10.dp, 5.dp, 0.dp, 0.dp))
                        clubAccount.value.contactInfo?.get("phoneNumber")?.let {
                            Text(
                                text = it,
                                modifier = Modifier
                                    .padding(5.dp)
                            )
                        }
                    }

                    Text(
                        text = "Kausimaksu",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(10.dp, 20.dp, 0.dp, 5.dp)
                    )
                    Text(
                        text = clubAccount.value.payment.orEmpty(),
                        modifier = Modifier
                            .padding(10.dp, 0.dp, 0.dp, 5.dp)
                    )

                    Text(
                        text = "Harjoitukset",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(10.dp, 20.dp, 0.dp, 5.dp)
                    )
                    Text(
                        text = clubAccount.value.trainingPlaceAndTime.orEmpty(),
                        modifier = Modifier
                            .padding(10.dp, 0.dp, 0.dp, 5.dp)
                    )
                }
            }
            item {
                Divider(
                    modifier = Modifier.padding(0.dp, 20.dp)
                )
            }
                Log.d("haluun nukkuun", clubPosts.toString())
            items(clubPosts, key = {it.id}) {
                PostItem(post = it, onActionClick = null)
            }

                if (showSignOutDialog) {
                    item {
                        AlertDialog(
                            title = { Text(text = "Haluatko kirjautua ulos?") },
                            dismissButton = {
                                Button(onClick = { showSignOutDialog = false },
                                    colors = ButtonColors(Violet, White, Violet, White)) {
                                    Text(text = "Peru")
                                }
                            },
                            onDismissRequest = { showSignOutDialog = false },
                            confirmButton = {
                                Button(onClick = {
                                    viewModel.onSignOutClick()
                                    showSignOutDialog = false
                                },
                                    colors = ButtonColors(Violet, White, Violet, White)) {
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
                                Button(onClick = { showDeleteAccountDialog = false },
                                    colors = ButtonColors(Violet, White, Violet, White)) {
                                    Text(text = "Peru")
                                }
                            },
                            onDismissRequest = { showDeleteAccountDialog = false },
                            confirmButton = {
                                Button(onClick = {
                                    viewModel.onDeleteAccountClick()
                                    showDeleteAccountDialog = false
                                },
                                    colors = ButtonColors(Violet, White, Violet, White)) {
                                    Text(text = "Poista tilisi")
                                }
                            })
                    }
                }
            }
        }
    }