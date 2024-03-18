package fi.tuni.sportsconnect.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fi.tuni.sportsconnect.ui.theme.Purple200
import fi.tuni.sportsconnect.ui.theme.SportsConnectTheme
import fi.tuni.sportsconnect.viewModels.CreateClubProfileViewModel
import fi.tuni.sportsconnect.viewModels.CreatePlayerProfileViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun CreateClubProfileScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateClubProfileViewModel = hiltViewModel()
) {
    val clubName = viewModel.clubName.collectAsState()
    val city = viewModel.city.collectAsState()
    val bio = viewModel.bio.collectAsState()
    val phoneNumber = viewModel.phoneNumber.collectAsState()
//    val firstTeam = viewModel.firstTeam.collectAsState()
//    val firstTeamLevel = viewModel.firstTeamLevel.collectAsState()
//    val secondTeam = viewModel.secondTeam.collectAsState()
//    val secondTeamLevel = viewModel.secondTeamLevel.collectAsState()
//    val thirdTeam = viewModel.thirdTeam.collectAsState()
//    val thirdTeamLevel = viewModel.thirdTeamLevel.collectAsState()
    val trainingPlaceAndTime = viewModel.trainingPlaceAndTime.collectAsState()

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
//
//        Row(
//            modifier = modifier
//                .fillMaxWidth()
//        ) {
//            OutlinedTextField(
//                singleLine = true,
//                modifier = modifier
//                    .fillMaxWidth(0.5f)
//                    .padding(16.dp, 4.dp, 4.dp, 4.dp)
//                    .border(
//                        BorderStroke(width = 2.dp, color = Purple200),
//                        shape = RoundedCornerShape(50)
//                    ),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.Transparent,
//                    unfocusedContainerColor = Color.Transparent,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent
//                ),
//                value = firstTeam.value,
//                onValueChange = { viewModel.updateFirstTeam(it) },
//                placeholder = { Text("Edustusjoukkueen nimi") },
//            )
//
//            OutlinedTextField(
//                singleLine = true,
//                modifier = modifier
//                    .fillMaxWidth()
//                    .padding(4.dp, 4.dp, 16.dp, 4.dp)
//                    .border(
//                        BorderStroke(width = 2.dp, color = Purple200),
//                        shape = RoundedCornerShape(50)
//                    ),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.Transparent,
//                    unfocusedContainerColor = Color.Transparent,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent
//                ),
//                value = firstTeamLevel.value,
//                onValueChange = { viewModel.updateFirstTeamLevel(it) },
//                placeholder = { Text("Edustusjoukkueen sarjataso") },
//            )
//        }
//
//        Row(
//            modifier = modifier
//                .fillMaxWidth()
//        ) {
//            OutlinedTextField(
//                singleLine = true,
//                modifier = modifier
//                    .fillMaxWidth(0.5f)
//                    .padding(16.dp, 4.dp, 4.dp, 4.dp)
//                    .border(
//                        BorderStroke(width = 2.dp, color = Purple200),
//                        shape = RoundedCornerShape(50)
//                    ),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.Transparent,
//                    unfocusedContainerColor = Color.Transparent,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent
//                ),
//                value = secondTeam.value,
//                onValueChange = { viewModel.updateSecondTeam(it) },
//                placeholder = { Text("2. joukkueen nimi") },
//            )
//
//            OutlinedTextField(
//                singleLine = true,
//                modifier = modifier
//                    .fillMaxWidth()
//                    .padding(4.dp, 4.dp, 16.dp, 4.dp)
//                    .border(
//                        BorderStroke(width = 2.dp, color = Purple200),
//                        shape = RoundedCornerShape(50)
//                    ),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.Transparent,
//                    unfocusedContainerColor = Color.Transparent,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent
//                ),
//                value = secondTeamLevel.value,
//                onValueChange = { viewModel.updateSecondTeamLevel(it) },
//                placeholder = { Text("2. joukkueen sarjataso") },
//            )
//        }
//
//        Row(
//            modifier = modifier
//                .fillMaxWidth()
//        ) {
//            OutlinedTextField(
//                singleLine = true,
//                modifier = modifier
//                    .fillMaxWidth(0.5f)
//                    .padding(16.dp, 4.dp, 4.dp, 4.dp)
//                    .border(
//                        BorderStroke(width = 2.dp, color = Purple200),
//                        shape = RoundedCornerShape(50)
//                    ),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.Transparent,
//                    unfocusedContainerColor = Color.Transparent,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent
//                ),
//                value = thirdTeam.value,
//                onValueChange = { viewModel.updateThirdTeam(it) },
//                placeholder = { Text("3. joukkueen nimi") },
//            )
//
//            OutlinedTextField(
//                singleLine = true,
//                modifier = modifier
//                    .fillMaxWidth()
//                    .padding(4.dp, 4.dp, 16.dp, 4.dp)
//                    .border(
//                        BorderStroke(width = 2.dp, color = Purple200),
//                        shape = RoundedCornerShape(50)
//                    ),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.Transparent,
//                    unfocusedContainerColor = Color.Transparent,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent
//                ),
//                value = thirdTeamLevel.value,
//                onValueChange = { viewModel.updateThirdTeamLevel(it) },
//                placeholder = { Text("3. joukkueen sarjataso") },
//            )
//        }

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
                text = "Valmis",
                fontSize = 16.sp,
                modifier = modifier.padding(0.dp, 6.dp)
            )
        }
    }
}
