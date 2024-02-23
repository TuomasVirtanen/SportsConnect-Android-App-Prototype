package fi.tuni.sportsconnect.viewModels

import androidx.compose.material.FabPosition
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import fi.tuni.sportsconnect.CREATE_PLAYER_PROFILE
import fi.tuni.sportsconnect.HOME_SCREEN
import fi.tuni.sportsconnect.SIGN_UP_SCREEN
import fi.tuni.sportsconnect.model.AccountService
import fi.tuni.sportsconnect.model.FirestoreService
import fi.tuni.sportsconnect.model.PlayerAccount
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService
) : SportsConnectAppViewModel() {
    val firstName = MutableStateFlow("")
    val lastName = MutableStateFlow("")
    val bio = MutableStateFlow("")
    val career = MutableStateFlow("")
    val cities: MutableStateFlow<MutableList<String>> = MutableStateFlow(mutableListOf(""))
    val phoneNumber = MutableStateFlow("")
    val currentTeam = MutableStateFlow("")
    val positions: MutableStateFlow<MutableList<String>> = MutableStateFlow(mutableListOf(""))
    val searchingForTeam: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val strengths = MutableStateFlow("")

    fun updateFirstName(newFirstName: String) {
        firstName.value = newFirstName
    }

    fun updateLastName(newLastName: String) {
        lastName.value = newLastName
    }

    fun updateBio(newBio: String) {
        bio.value = newBio
    }

    fun updateCareer(newCareer: String) {
        career.value = newCareer
    }

    fun updateCities(newCity: String) {
        cities.value[0] = cities.value[0] + newCity
    }

    fun updatePhoneNumber(newPhoneNumber: String) {
        phoneNumber.value = newPhoneNumber
    }

    fun updateCurrentTeam(newCurrentTeam: String) {
        currentTeam.value = newCurrentTeam
    }

    fun updatePositions(newPosition: String) {
        positions.value[0] = positions.value[0] + newPosition
    }

    fun updateSearchingForTeam() {
        searchingForTeam.value = !(searchingForTeam.value)
    }

    fun updateStrengths(newStrengths: String) {
        strengths.value = newStrengths
    }

    fun onFinishClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            firestoreService.createPlayerProfile(PlayerAccount(
                accountService.currentUserId,
                bio.value,
                career.value,
                cities.value,
                mapOf("email" to Firebase.auth.currentUser?.email.orEmpty(),
                    "phoneNumber" to phoneNumber.value),
                currentTeam.value,
                firstName.value,
                lastName.value,
                positions.value,
                searchingForTeam.value,
                strengths.value
                ))
            openAndPopUp(HOME_SCREEN, CREATE_PLAYER_PROFILE)
        }
    }
}