package fi.tuni.sportsconnect.viewModels

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import fi.tuni.sportsconnect.CREATE_PLAYER_PROFILE
import fi.tuni.sportsconnect.PLAYER_HOME_SCREEN
import fi.tuni.sportsconnect.model.AccountService
import fi.tuni.sportsconnect.model.FirestoreService
import fi.tuni.sportsconnect.model.PlayerAccount
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CreatePlayerProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService
) : SportsConnectAppViewModel() {
    val firstName = MutableStateFlow("")
    val lastName = MutableStateFlow("")
    val age = MutableStateFlow("")
    val bio = MutableStateFlow("")
    val career = MutableStateFlow("")
    val city = MutableStateFlow("")
    val phoneNumber = MutableStateFlow("")
    val currentTeam = MutableStateFlow("")
    val shoot = MutableStateFlow("")
    val position = MutableStateFlow("")
    val leagueLevel = MutableStateFlow("")
    val searchingForTeam: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val strengths = MutableStateFlow("")
    val expandedShoot = MutableStateFlow(false)
    val expandedPos = MutableStateFlow(false)
    val expandedLevel = MutableStateFlow(false)

    fun updateFirstName(newFirstName: String) {
        firstName.value = newFirstName
    }

    fun updateLastName(newLastName: String) {
        lastName.value = newLastName
    }

    fun updateAge(newAge: String) {
        age.value = newAge
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

    fun updateShoot(newShoot: String) {
        shoot.value = newShoot
    }

    fun updateExpandedShoot() {
        expandedShoot.value = !expandedShoot.value
    }

    fun updatePosition(newPosition: String) {
        position.value = newPosition
    }

    fun updateExpandedPos() {
        expandedPos.value = !expandedPos.value
    }

    fun updateLeagueLevel(newLevel: String) {
        leagueLevel.value = newLevel
    }

    fun updateExpandedLevel() {
        expandedLevel.value = !expandedLevel.value
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
                mutableListOf(city.value),
                mapOf("email" to Firebase.auth.currentUser?.email.orEmpty(),
                    "phoneNumber" to phoneNumber.value),
                currentTeam.value,
                firstName.value,
                lastName.value,
                age.value,
                shoot.value,
                mutableListOf(leagueLevel.value),
                mutableListOf(position.value),
                searchingForTeam.value,
                strengths.value
                ))
            openAndPopUp(PLAYER_HOME_SCREEN, CREATE_PLAYER_PROFILE)
        }
    }
}