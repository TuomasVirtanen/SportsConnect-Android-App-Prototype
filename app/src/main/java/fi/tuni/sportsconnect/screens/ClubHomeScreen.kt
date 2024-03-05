package fi.tuni.sportsconnect.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fi.tuni.sportsconnect.model.PlayerAccount
import fi.tuni.sportsconnect.viewModels.ClubHomeViewModel
import fi.tuni.sportsconnect.viewModels.PlayerHomeViewModel
import fi.tuni.sportsconnect.viewModels.PlayerProfileViewModel

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ClubHomeScreen(
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ClubHomeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) { viewModel.initialize(restartApp) }

    Scaffold {
        val players by viewModel.players.collectAsState(emptyList())

        Column(modifier = Modifier
            .fillMaxSize()) {
            LazyColumn {
                items(players, key = { it.id }) {playerItem ->
                    PlayerItem(
                        player = playerItem,
                        onActionClick = {})
                }
            }
        }
    }
}

@Composable
fun PlayerItem(
    player: PlayerAccount,
    onActionClick: (String) -> Unit
) {
    Card(
        modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onActionClick(player.id) }
        ) {
            Text(
                text = player.firstName + player.lastName,
                modifier = Modifier.padding(12.dp, 12.dp, 12.dp, 12.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}