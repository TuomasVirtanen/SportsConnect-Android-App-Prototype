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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fi.tuni.sportsconnect.R
import fi.tuni.sportsconnect.model.Post
import fi.tuni.sportsconnect.ui.theme.Dark
import fi.tuni.sportsconnect.ui.theme.White
import fi.tuni.sportsconnect.viewModels.PlayerHomeViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun PlayerHomeScreen(
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    viewModel: PlayerHomeViewModel = hiltViewModel()
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
    val posts by viewModel.filteredPosts.collectAsState(emptyList())
    val updateCityFilters: (String) -> Unit = { filter -> viewModel.onCityFilterChange(filter) }
    val updatePositionFilters: (String) -> Unit = { filter -> viewModel.onPositionFilterChange(filter) }
    val updateLevelFilters: (String) -> Unit = { filter -> viewModel.onLevelFilterChange(filter) }

    LaunchedEffect(Unit) {
        viewModel.initialize(restartApp)
    }

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
                items(posts, key = { it.id }) {postItem ->
                    PostItem(
                        post = postItem,
                        onActionClick = { viewModel.onProfileClick(openScreen, postItem.club["clubId"]!!)})
                }
            }
        }
    }
    // Sheet content
    if (openBottomSheet) {

        ModalBottomSheet(
            onDismissRequest = {
                openBottomSheet = false
                viewModel.updatePosts()
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
                            viewModel.updatePosts()
                        }
                    ) {
                        Text("Suodata")
                    }

                    Button(
                        onClick = {
                            viewModel.clearFilters()
                        }
                    ) {
                        Text("Poista suodattimet")
                    }
                }
                Spacer(modifier = Modifier.padding(0.dp, 50.dp))
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PostItem(
    post: Post,
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
                    painter = painterResource(id = R.drawable.no_team_logo),
                    contentDescription = "noLogo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable { onActionClick(post.club["clubId"]!!) }
                )
                Text(
                    text = post.club["clubName"].orEmpty(),
                    modifier = Modifier
                        .padding(5.dp, 12.dp, 5.dp, 12.dp)
                        .clickable { onActionClick(post.club["clubId"]!!) },
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "·",
                    modifier = Modifier
                        .padding(5.dp),
                    style = TextStyle(
                        fontSize = 25.sp
                    ),
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(post.created.toDate().toInstant().atZone(
                        ZoneOffset.UTC).toLocalDate()),
                    modifier = Modifier.padding(5.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.W200
                )
            }
            FlowRow(
                modifier = Modifier
                    .padding(5.dp, 0.dp, 5.dp, 10.dp),

            ) {
                    Text(
                        text = post.club["city"].orEmpty(),
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
                    Text(
                        text = post.club["level"].orEmpty(),
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
                    post.positions.forEach{ post ->
                        Text(
                            text = post,
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
            Text(
                text = post.text,
                modifier = Modifier.padding(10.dp, 0.dp, 12.dp, 5.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

fun LazyListScope.cityFilterList(onFilterClick: (String) -> Unit, filters: MutableList<String>) {
    items(listOf("Tampere", "Helsinki", "Espoo", "Oulu", "Kuopio")) {item ->
        var filterChecked by remember {
            mutableStateOf( filters.any { city -> item == city })
        }

        if (filters.isEmpty()) {
            filterChecked = false
        }

        Row(
            Modifier
                .fillMaxSize()
                .toggleable(
                    value = filterChecked,
                    role = Role.Checkbox,
                    onValueChange = {
                        onFilterClick(item)
                        filterChecked = it
                    }
                )
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = filterChecked,
                onCheckedChange = null,
                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp))
            Spacer(Modifier.width(16.dp))
            Text(item)
        }
    }
}

fun LazyListScope.positionFilterList(onFilterClick: (String) -> Unit, filters: MutableList<String>) {
    items(listOf("Maalivahti", "Puolustaja", "Hyökkääjä")) {item ->
        var filterChecked by remember {
            mutableStateOf( filters.any { position -> item == position })
        }

        if (filters.isEmpty()) {
            filterChecked = false
        }

        Row(
            Modifier
                .fillMaxSize()
                .toggleable(
                    value = filterChecked,
                    role = Role.Checkbox,
                    onValueChange = {
                        onFilterClick(item)
                        filterChecked = it
                    }
                )
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = filterChecked, onCheckedChange = null, modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp))
            Spacer(Modifier.width(16.dp))
            Text(item)
        }
    }
}

fun LazyListScope.levelFilterList(onFilterClick: (String) -> Unit, filters: MutableList<String>) {
    items(listOf("M 2. divisioona", "N 2. divisioona", "M 3. divisioona", "N 3. divisioona")) {item ->
        var filterChecked by remember {
            mutableStateOf( filters.any { level -> item == level })
        }

        if (filters.isEmpty()) {
            filterChecked = false
        }

        Row(
            Modifier
                .fillMaxSize()
                .toggleable(
                    value = filterChecked,
                    role = Role.Checkbox,
                    onValueChange = {
                        onFilterClick(item)
                        filterChecked = it
                    }
                )
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = filterChecked, onCheckedChange = null, modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp))
            Spacer(Modifier.width(16.dp))
            Text(item)
        }
    }
}

@Composable
fun rememberFilterList(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "filter_list",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(18.083f, 29.333f)
                quadToRelative(-0.583f, 0f, -0.958f, -0.395f)
                quadToRelative(-0.375f, -0.396f, -0.375f, -0.938f)
                quadToRelative(0f, -0.542f, 0.375f, -0.938f)
                quadToRelative(0.375f, -0.395f, 0.958f, -0.395f)
                horizontalLineToRelative(3.834f)
                quadToRelative(0.541f, 0f, 0.937f, 0.395f)
                quadToRelative(0.396f, 0.396f, 0.396f, 0.938f)
                quadToRelative(0f, 0.583f, -0.396f, 0.958f)
                reflectiveQuadToRelative(-0.937f, 0.375f)
                close()
                moveTo(6.5f, 12.792f)
                quadToRelative(-0.542f, 0f, -0.917f, -0.375f)
                reflectiveQuadToRelative(-0.375f, -0.959f)
                quadToRelative(0f, -0.541f, 0.375f, -0.937f)
                reflectiveQuadToRelative(0.917f, -0.396f)
                horizontalLineToRelative(26.958f)
                quadToRelative(0.584f, 0f, 0.959f, 0.396f)
                reflectiveQuadToRelative(0.375f, 0.937f)
                quadToRelative(0f, 0.584f, -0.375f, 0.959f)
                reflectiveQuadToRelative(-0.959f, 0.375f)
                close()
                moveToRelative(4.958f, 8.25f)
                quadToRelative(-0.541f, 0f, -0.937f, -0.375f)
                reflectiveQuadToRelative(-0.396f, -0.959f)
                quadToRelative(0f, -0.541f, 0.396f, -0.916f)
                reflectiveQuadToRelative(0.937f, -0.375f)
                horizontalLineToRelative(17.084f)
                quadToRelative(0.541f, 0f, 0.916f, 0.395f)
                quadToRelative(0.375f, 0.396f, 0.375f, 0.938f)
                quadToRelative(0f, 0.542f, -0.375f, 0.917f)
                reflectiveQuadToRelative(-0.916f, 0.375f)
                close()
            }
        }.build()
    }
}