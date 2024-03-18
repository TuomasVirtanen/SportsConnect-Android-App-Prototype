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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fi.tuni.sportsconnect.R
import fi.tuni.sportsconnect.ui.theme.Dark
import fi.tuni.sportsconnect.ui.theme.Violet
import fi.tuni.sportsconnect.viewModels.CreatePlayerProfileViewModel

@Composable
fun CreatePlayerProfileScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreatePlayerProfileViewModel = hiltViewModel()
) {
    val firstName = viewModel.firstName.collectAsState()
    val lastName = viewModel.lastName.collectAsState()
    val bio = viewModel.bio.collectAsState()
    val career = viewModel.career.collectAsState()
    val city = viewModel.city.collectAsState()
    val phoneNumber = viewModel.phoneNumber.collectAsState()
    val currentTeam = viewModel.currentTeam.collectAsState()
    val position = viewModel.position.collectAsState()
    val leagueLevel = viewModel.leagueLevel.collectAsState()
    val searchingForTeam = viewModel.searchingForTeam.collectAsState()
    val strengths = viewModel.strengths.collectAsState()

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
            value = position.value,
            onValueChange = { viewModel.updatePosition(it) },
            placeholder = { Text(stringResource(R.string.position)) },
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
            value = leagueLevel.value,
            onValueChange = { viewModel.updateLeagueLevel(it) },
            placeholder = { Text(stringResource(R.string.level)) },
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
            Text(text = stringResource(R.string.searching_for_team))
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
                text = stringResource(R.string.ready),
                fontSize = 20.sp,
                modifier = modifier.padding(0.dp, 6.dp),
                fontWeight = FontWeight.Bold
            )
        }
    }
}
