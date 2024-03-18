package fi.tuni.sportsconnect.viewModels

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import fi.tuni.sportsconnect.CREATE_PLAYER_PROFILE
import fi.tuni.sportsconnect.EDIT_PLAYER_PROFILE_SCREEN
import fi.tuni.sportsconnect.PLAYER_HOME_SCREEN
import fi.tuni.sportsconnect.PLAYER_PROFILE_SCREEN
import fi.tuni.sportsconnect.model.AccountService
import fi.tuni.sportsconnect.model.FirestoreService
import fi.tuni.sportsconnect.model.PlayerAccount
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class EditPlayerProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService
): SportsConnectAppViewModel() {
    val player = MutableStateFlow(PlayerAccount())
    val firstName = MutableStateFlow("")
    val lastName = MutableStateFlow("")
    val bio = MutableStateFlow("")
    val career = MutableStateFlow("")
    val city = MutableStateFlow("")
    val phoneNumber = MutableStateFlow("")
    val currentTeam = MutableStateFlow("")
    val position = MutableStateFlow("")
    val leagueLevel = MutableStateFlow("")
    val searchingForTeam: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val strengths = MutableStateFlow("")

    fun initialize() {
        launchCatching {
            player.value = firestoreService.readPlayerProfile(accountService.currentUserId)!!
            firstName.value = player.value.firstName
            lastName.value = player.value.lastName
            bio.value = player.value.bio.orEmpty()
            career.value = player.value.career.orEmpty()
            city.value = player.value.cities?.get(0).orEmpty()
            phoneNumber.value = player.value.contactInfo?.get("phoneNumber").orEmpty()
            currentTeam.value = player.value.currentTeam.orEmpty()
            position.value = player.value.positions?.get(0).orEmpty()
            leagueLevel.value = player.value.leagueLevels?.get(0).orEmpty()
            searchingForTeam.value = player.value.searchingForTeam!!
            strengths.value = player.value.strengths.orEmpty()
        }
    }

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

    fun updateCity(newCity: String) {
        city.value = newCity
    }

    fun updatePhoneNumber(newPhoneNumber: String) {
        phoneNumber.value = newPhoneNumber
    }

    fun updateCurrentTeam(newCurrentTeam: String) {
        currentTeam.value = newCurrentTeam
    }

    fun updatePosition(newPosition: String) {
        position.value = newPosition
    }

    fun updateLeagueLevel(newLevel: String) {
        leagueLevel.value = newLevel
    }

    fun updateSearchingForTeam() {
        searchingForTeam.value = !(searchingForTeam.value)
    }

    fun updateStrengths(newStrengths: String) {
        strengths.value = newStrengths
    }

    fun onFinishClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            firestoreService.createPlayerProfile(
                PlayerAccount(
                player.value.id,
                bio.value,
                career.value,
                mutableListOf(city.value),
                mapOf("email" to Firebase.auth.currentUser?.email.orEmpty(),
                    "phoneNumber" to phoneNumber.value),
                currentTeam.value,
                firstName.value,
                lastName.value,
                mutableListOf(leagueLevel.value),
                mutableListOf(position.value),
                searchingForTeam.value,
                strengths.value
            )
            )
            openAndPopUp(PLAYER_PROFILE_SCREEN, EDIT_PLAYER_PROFILE_SCREEN)
        }
    }
}