package fi.tuni.sportsconnect.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fi.tuni.sportsconnect.R
import fi.tuni.sportsconnect.ui.theme.Dark
import fi.tuni.sportsconnect.ui.theme.Violet
import fi.tuni.sportsconnect.viewModels.CreatePlayerProfileViewModel
import fi.tuni.sportsconnect.viewModels.EditPlayerProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPlayerProfileScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditPlayerProfileViewModel = hiltViewModel()
) {
    val firstName = viewModel.firstName.collectAsState()
    val lastName = viewModel.lastName.collectAsState()
    val age = viewModel.age.collectAsState()
    val bio = viewModel.bio.collectAsState()
    val career = viewModel.career.collectAsState()
    val city = viewModel.city.collectAsState()
    val phoneNumber = viewModel.phoneNumber.collectAsState()
    val currentTeam = viewModel.currentTeam.collectAsState()
    val shoot = viewModel.shoot.collectAsState()
    val position = viewModel.position.collectAsState()
    val leagueLevel = viewModel.leagueLevel.collectAsState()
    val searchingForTeam = viewModel.searchingForTeam.collectAsState()
    val strengths = viewModel.strengths.collectAsState()
    val expandedShoot = viewModel.expandedShoot.collectAsState()
    val expandedPos = viewModel.expandedPos.collectAsState()
    val expandedLevel = viewModel.expandedLevel.collectAsState()

    val shootOptions = listOf("R", "L")
    val positionOptions = listOf("Maalivahti", "Puolustaja", "Hyökkääjä")
    val levelOptions = listOf("F-liiga M", "F-liiga N", "M Inssi-Divari", "N Divari", "M Suomisarja",
        "N Suomisarja", "M 2. div", "N 2. div", "M 3. div", "N 3. div", "M 4. div", "N 4. div", "M 5. div",
        "M 6. div", "Muu")

    LaunchedEffect(Unit) {viewModel.initialize()}

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
    ) {
            Text(
                modifier = Modifier
                    .padding(30.dp, 15.dp, 0.dp, 0.dp),
                text = stringResource(R.string.first_name),
                style = MaterialTheme.typography.bodyLarge,
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
            value = firstName.value,
            onValueChange = { viewModel.updateFirstName(it) },
            placeholder = { Text(stringResource(R.string.first_name)) },
        )

        Text(
            modifier = Modifier
                .padding(30.dp, 15.dp, 0.dp, 0.dp),
            text = stringResource(R.string.last_name),
            style = MaterialTheme.typography.bodyLarge,
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
            value = lastName.value,
            onValueChange = { viewModel.updateLastName(it) },
            placeholder = { Text(stringResource(R.string.last_name)) },
        )

        Text(
            modifier = Modifier
                .padding(30.dp, 15.dp, 0.dp, 0.dp),
            text = stringResource(R.string.age),
            style = MaterialTheme.typography.bodyLarge,
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
            value = age.value,
            onValueChange = { viewModel.updateAge(it) },
            placeholder = { Text(stringResource(R.string.age)) },
        )

        Text(
            modifier = Modifier
                .padding(30.dp, 15.dp, 0.dp, 0.dp),
            text = "Lyhyt kuvaus",
            style = MaterialTheme.typography.bodyLarge,
        )
        OutlinedTextField(
            singleLine = false,
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
            value = bio.value,
            onValueChange = { viewModel.updateBio(it) },
            placeholder = { Text(stringResource(R.string.bio)) },
        )

        Text(
            modifier = Modifier
                .padding(30.dp, 15.dp, 0.dp, 0.dp),
            text = "Ura",
            style = MaterialTheme.typography.bodyLarge,
        )
        OutlinedTextField(
            singleLine = false,
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
            value = career.value,
            onValueChange = { viewModel.updateCareer(it) },
            placeholder = { Text(stringResource(R.string.career)) },
        )

        Text(
            modifier = Modifier
                .padding(30.dp, 15.dp, 0.dp, 0.dp),
            text = stringResource(R.string.city),
            style = MaterialTheme.typography.bodyLarge,
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
            value = city.value,
            onValueChange = { viewModel.updateCity(it) },
            placeholder = { Text(stringResource(R.string.city)) },
        )

        Text(
            modifier = Modifier
                .padding(30.dp, 15.dp, 0.dp, 0.dp),
            text = stringResource(R.string.current_team),
            style = MaterialTheme.typography.bodyLarge,
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
            value = currentTeam.value,
            onValueChange = { viewModel.updateCurrentTeam(it) },
            placeholder = { Text(stringResource(R.string.current_team)) },
        )

        Text(
            modifier = Modifier
                .padding(30.dp, 15.dp, 0.dp, 0.dp),
            text = stringResource(R.string.phone_number),
            style = MaterialTheme.typography.bodyLarge,
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
            value = phoneNumber.value,
            onValueChange = { viewModel.updatePhoneNumber(it) },
            placeholder = { Text(stringResource(R.string.phone_number)) },
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 15.dp, 0.dp, 0.dp)) {
            ExposedDropdownMenuBox(
                expanded = expandedShoot.value,
                onExpandedChange = {viewModel.updateExpandedShoot()},
                modifier = modifier
                    .padding(16.dp, 4.dp),
            ) {
                TextField(
                    modifier = modifier.menuAnchor(),
                    readOnly = true,
                    value = shoot.value,
                    onValueChange = {},
                    label = { Text(stringResource(R.string.shoot)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedShoot.value) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                )
                ExposedDropdownMenu(
                    expanded = expandedShoot.value,
                    onDismissRequest = { viewModel.updateExpandedShoot() },
                ) {
                    shootOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                viewModel.updateShoot(selectionOption)
                                viewModel.updateExpandedShoot()
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = expandedPos.value,
                onExpandedChange = {viewModel.updateExpandedPos()},
                modifier = modifier
                    .padding(16.dp, 4.dp)
            ) {
                TextField(
                    // The `menuAnchor` modifier must be passed to the text field for correctness.
                    modifier = modifier.menuAnchor(),
                    readOnly = true,
                    value = position.value,
                    onValueChange = {},
                    label = { Text(stringResource(R.string.position)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPos.value) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                )
                ExposedDropdownMenu(
                    expanded = expandedPos.value,
                    onDismissRequest = { viewModel.updateExpandedPos() },
                ) {
                    positionOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                viewModel.updatePosition(selectionOption)
                                viewModel.updateExpandedPos()
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = expandedLevel.value,
                onExpandedChange = {viewModel.updateExpandedLevel()},
                modifier = modifier
                    .padding(16.dp, 4.dp)
            ) {
                TextField(
                    modifier = modifier.menuAnchor(),
                    readOnly = true,
                    value = leagueLevel.value,
                    onValueChange = {},
                    label = { Text(stringResource(R.string.level)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLevel.value) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                )
                ExposedDropdownMenu(
                    expanded = expandedLevel.value,
                    onDismissRequest = { viewModel.updateExpandedLevel() },
                ) {
                    levelOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                viewModel.updateLeagueLevel(selectionOption)
                                viewModel.updateExpandedLevel()
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
        }

        Text(
            modifier = Modifier
                .padding(30.dp, 15.dp, 0.dp, 0.dp),
            text = stringResource(R.string.strengths),
            style = MaterialTheme.typography.bodyLarge,
        )
        OutlinedTextField(
            singleLine = false,
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
            value = strengths.value,
            onValueChange = { viewModel.updateStrengths(it) },
            placeholder = { Text(stringResource(R.string.strengths)) },
        )

        Row(modifier = modifier
            .fillMaxSize()
            .padding(16.dp, 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = stringResource(R.string.searching_for_team),
                style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
            )
            Switch(
                checked = searchingForTeam.value,
                onCheckedChange = { viewModel.updateSearchingForTeam() },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = Dark
                )
            )
        }

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp))

        Button(
            onClick = { viewModel.onFinishClick(openAndPopUp) },
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Violet
            )
        ) {
            Text(
                text = "Tallenna muutokset",
                fontSize = 16.sp,
                modifier = modifier.padding(0.dp, 6.dp),
            )
        }
    }
}
