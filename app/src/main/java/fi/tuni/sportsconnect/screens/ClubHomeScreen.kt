package fi.tuni.sportsconnect.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import fi.tuni.sportsconnect.model.PlayerAccount
import fi.tuni.sportsconnect.ui.theme.Dark
import fi.tuni.sportsconnect.ui.theme.Violet
import fi.tuni.sportsconnect.ui.theme.White
import fi.tuni.sportsconnect.viewModels.ClubHomeViewModel
import kotlinx.coroutines.launch
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ClubHomeScreen(
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ClubHomeViewModel = hiltViewModel()
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showCities by rememberSaveable {
        mutableStateOf(false)
    }
    var showLevels by rememberSaveable {
        mutableStateOf(false)
    }
    var showPositions by rememberSaveable {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val filters by viewModel.filters.collectAsState()
    val players by viewModel.shownPlayers.collectAsState(emptyList())
    val updateCityFilters: (String) -> Unit = { filter -> viewModel.onCityFilterChange(filter) }
    val updatePositionFilters: (String) -> Unit = { filter -> viewModel.onPositionFilterChange(filter) }
    val updateLevelFilters: (String) -> Unit = { filter -> viewModel.onLevelFilterChange(filter) }

    LaunchedEffect(Unit) { viewModel.initialize(restartApp) }

    Scaffold {

        Column(modifier = Modifier
            .fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                IconButton(onClick = { openBottomSheet = !openBottomSheet }) {
                    Icon(rememberFilterList(), "Filters")
                }
                filters.forEach{ filterList ->
                    Log.d("filterit home screenissä", "filtterit päivitetty")
                    filterList.value.forEach {
                        Text(
                            text = it,
                            modifier = Modifier
                                .padding(5.dp)
                                .clip(CircleShape)
                                .background(White)
                                .border(
                                    border = BorderStroke(2.dp, color = Dark),
                                    shape = CircleShape
                                )
                                .padding(5.dp)
                        )
                    }
                }
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 12.dp, 0.dp, 0.dp)) {
                LazyColumn {
                    items(players, key = { it.id }) {playerItem ->
                        PlayerItem(
                            player = playerItem,
                            onActionClick = {viewModel.onProfileClick(openScreen, playerItem.id)})
                    }
                }
            }
        }
        if (openBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    openBottomSheet = false
                    viewModel.updatePlayers()
                },
                sheetState = bottomSheetState,
                modifier = Modifier.padding(0.dp, 10.dp),
            ) {
                Column {
                    LazyColumn(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.85f),
                    ) {
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clickable { showCities = !showCities }
                                    .fillMaxWidth()
                                    .padding(20.dp, 20.dp, 20.dp, 10.dp)
                            ) {
                                Icon(Icons.Filled.ArrowDropDown, "City filters")
                                Text(
                                    text = "Paikkakunnat",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                        if (showCities) cityFilterList(
                            onFilterClick = updateCityFilters,
                            filters = filters["cities"] ?: mutableListOf())
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clickable { showPositions = !showPositions }
                                    .fillMaxWidth()
                                    .padding(20.dp, 20.dp, 20.dp, 10.dp)
                            ) {
                                Icon(Icons.Filled.ArrowDropDown, "Position filters")
                                Text(
                                    text = "Pelipaikat",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                        if (showPositions) positionFilterList(
                            onFilterClick = updatePositionFilters,
                            filters = filters["positions"] ?: mutableListOf()
                        )
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clickable { showLevels = !showLevels }
                                    .fillMaxWidth()
                                    .padding(20.dp, 20.dp, 20.dp, 10.dp)
                            ) {
                                Icon(Icons.Filled.ArrowDropDown, "Level filters")
                                Text(
                                    text = "Sarjatasot",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                        if (showLevels) levelFilterList(
                            onFilterClick = updateLevelFilters,
                            filters = filters["levels"] ?: mutableListOf()
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        Button(
                            onClick = {
                                scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                                    if (!bottomSheetState.isVisible) {
                                        openBottomSheet = false
                                    }
                                }
                                viewModel.updatePlayers()
                            },
                            colors = ButtonColors(Violet, White, Violet, White)
                        ) {
                            Text("Suodata")
                        }

                        Button(
                            onClick = {
                                viewModel.clearFilters()
                            },
                            colors = ButtonColors(Violet, White, Violet, White)
                        ) {
                            Text("Poista suodattimet")
                        }
                    }
                    Spacer(modifier = Modifier.padding(0.dp, 50.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlayerItem(
    player: PlayerAccount,
    onActionClick: (String) -> Unit
) {
    Card(
        modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp, 0.dp, 5.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Image(
                    painter = painterResource(id = R.drawable.no_profile_picture),
                    contentDescription = "noProfilePic",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable(enabled = true) {
                            onActionClick(player.id)
                        }
                )
                Text(
                    text = "${player.firstName} ${player.lastName}, ${player.age}" +
                            if (player.positions!![0] != "Maalivahti") ", ${player.shoot}"
                            else "",
                    modifier = Modifier
                        .padding(5.dp, 12.dp, 5.dp, 12.dp)
                        .clickable(enabled = true) {
                            Log.d("tyhjää", player.id)
                            onActionClick(player.id)

                        },
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            if (player.searchingForTeam) {
                Text(
                    text = "Hakee uutta joukkuetta!",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(10.dp, 5.dp)
                        .clip(CircleShape)
                        .background(White)
                        .border(
                            border = BorderStroke(2.dp, color = Dark),
                            shape = CircleShape
                        )
                        .padding(5.dp)
                )
            }
            FlowRow(
                modifier = Modifier
                    .padding(5.dp, 0.dp, 5.dp, 5.dp),

                ) {
                player.cities?.forEach {
                    Text(
                        text = it,
                        modifier = Modifier
                            .padding(5.dp)
                            .clip(CircleShape)
                            .background(White)
                            .border(
                                border = BorderStroke(2.dp, color = Dark),
                                shape = CircleShape,
                            )
                            .padding(5.dp)

                    )
                }
                player.leagueLevels?.forEach {
                    Text(
                        text = it,
                        modifier = Modifier
                            .padding(5.dp)
                            .clip(CircleShape)
                            .background(White)
                            .border(
                                border = BorderStroke(2.dp, color = Dark),
                                shape = CircleShape,
                            )
                            .padding(5.dp)

                    )
                }
                player.positions?.forEach {
                    Text(
                        text = it,
                        modifier = Modifier
                            .padding(5.dp)
                            .clip(CircleShape)
                            .background(White)
                            .border(
                                border = BorderStroke(2.dp, color = Dark),
                                shape = CircleShape,
                            )
                            .padding(5.dp)

                    )
                }
            }
            Text(
                text = player.bio.orEmpty(),
                modifier = Modifier.padding(10.dp, 0.dp, 12.dp, 5.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}