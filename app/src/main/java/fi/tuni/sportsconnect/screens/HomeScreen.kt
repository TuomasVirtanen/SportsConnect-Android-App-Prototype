package fi.tuni.sportsconnect.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
//    Todo: viewmodel, kun tulee käyttäjien julkaisut
//    viewModel:
) {
    Column(modifier = Modifier
        .fillMaxSize()) {
        Text(text = "Seuran julkaisu")
    }
}