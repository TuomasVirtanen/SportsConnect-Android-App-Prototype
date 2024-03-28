package fi.tuni.sportsconnect.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fi.tuni.sportsconnect.R
import fi.tuni.sportsconnect.ui.theme.Purple200
import fi.tuni.sportsconnect.viewModels.EditClubProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditClubProfileScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditClubProfileViewModel = hiltViewModel()
) {
    val clubName = viewModel.clubName.collectAsState()
    val city = viewModel.city.collectAsState()
    val bio = viewModel.bio.collectAsState()
    val phoneNumber = viewModel.phoneNumber.collectAsState()
    val level = viewModel.leagueLevel.collectAsState()
    val trainingPlaceAndTime = viewModel.trainingPlaceAndTime.collectAsState()
    val payment = viewModel.payment.collectAsState()
    val expandedLevel = viewModel.expandedLevel.collectAsState()

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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
                .border(
                    BorderStroke(width = 2.dp, color = Purple200),
                    shape = RoundedCornerShape(50)
                ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            value = clubName.value,
            onValueChange = { viewModel.updateClubName(it) },
            placeholder = { Text("Seuran nimi") },
        )

        OutlinedTextField(
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
                .border(
                    BorderStroke(width = 2.dp, color = Purple200),
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
            placeholder = { Text("Seuran kotipaikkakunta") },
        )

        OutlinedTextField(
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
                .border(
                    BorderStroke(width = 2.dp, color = Purple200),
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
            placeholder = { Text("Lyhyt kuvaus seurasta") },
        )

        OutlinedTextField(
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
                .border(
                    BorderStroke(width = 2.dp, color = Purple200),
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
            placeholder = { Text("Puhelinnumero") },
        )

        ExposedDropdownMenuBox(
            expanded = expandedLevel.value,
            onExpandedChange = {viewModel.updateExpandedLevel()},
            modifier = modifier
                .padding(16.dp, 4.dp)
        ) {
            TextField(
                modifier = modifier.menuAnchor(),
                readOnly = true,
                value = level.value,
                onValueChange = {},
                label = { Text(stringResource(R.string.club_level)) },
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
                            viewModel.updateLevel(selectionOption)
                            viewModel.updateExpandedLevel()
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }

        OutlinedTextField(
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
                .border(
                    BorderStroke(width = 2.dp, color = Purple200),
                    shape = RoundedCornerShape(50)
                ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            value = trainingPlaceAndTime.value,
            onValueChange = { viewModel.updateTrainingPlaceAndTime(it) },
            placeholder = { Text("Seuran harjoituspaikat ja ajat") },
        )

        OutlinedTextField(
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp)
                .border(
                    BorderStroke(width = 2.dp, color = Purple200),
                    shape = RoundedCornerShape(50)
                ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            value = payment.value,
            onValueChange = { viewModel.updatePayment(it) },
            placeholder = { Text("Kausimaksu") },
        )

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp))

        Button(
            onClick = { viewModel.onFinishClick(openAndPopUp) },
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        ) {
            Text(
                text = "Tallenna muutokset",
                fontSize = 16.sp,
                modifier = modifier.padding(0.dp, 6.dp)
            )
        }
    }
}