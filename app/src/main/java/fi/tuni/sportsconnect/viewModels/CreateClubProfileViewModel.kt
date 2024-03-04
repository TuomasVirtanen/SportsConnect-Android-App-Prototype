package fi.tuni.sportsconnect.viewModels

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import fi.tuni.sportsconnect.CLUB_HOME_SCREEN
import fi.tuni.sportsconnect.CREATE_CLUB_PROFILE
import fi.tuni.sportsconnect.model.AccountService
import fi.tuni.sportsconnect.model.ClubAccount
import fi.tuni.sportsconnect.model.FirestoreService
import fi.tuni.sportsconnect.model.PlayerAccount
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateClubProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService
) : SportsConnectAppViewModel() {
    val clubName = MutableStateFlow("")
    val city = MutableStateFlow("")
    val bio = MutableStateFlow("")
    val phoneNumber = MutableStateFlow("")
    val firstTeam = MutableStateFlow("")
    val firstTeamLevel = MutableStateFlow("")
    val secondTeam = MutableStateFlow("")
    val secondTeamLevel = MutableStateFlow("")
    val thirdTeam = MutableStateFlow("")
    val thirdTeamLevel = MutableStateFlow("")
    val trainingPlaceAndTime = MutableStateFlow("")

    fun updateClubName(newClubName: String) {
        clubName.value = newClubName
    }
    fun updateCity(newCity: String) {
        city.value = newCity
    }

    fun updateBio(newBio: String) {
        bio.value = newBio
    }

    fun updatePhoneNumber(newPhoneNumber: String) {
        phoneNumber.value = newPhoneNumber
    }

    fun updateFirstTeam(newFirstTeam: String) {
        firstTeam.value = newFirstTeam
    }

    fun updateFirstTeamLevel(newFirstTeamLevel: String) {
        firstTeamLevel.value = newFirstTeamLevel
    }

    fun updateSecondTeam(newSecondTeam: String) {
        secondTeam.value = newSecondTeam
    }

    fun updateSecondTeamLevel(newSecondTeamLevel: String) {
        secondTeamLevel.value = newSecondTeamLevel
    }

    fun updateThirdTeam(newThirdTeam: String) {
        thirdTeam.value = newThirdTeam
    }

    fun updateThirdTeamLevel(newThirdTeamLevel: String) {
        thirdTeamLevel.value = newThirdTeamLevel
    }

    fun updateTrainingPlaceAndTime(newTrainingPlaceAndTime: String) {
        trainingPlaceAndTime.value = newTrainingPlaceAndTime
    }

    fun onFinishClick(openAndPopUp: (String, String) -> Unit) {
        val teamsMap = mapOf(firstTeam.value to firstTeamLevel.value,
            secondTeam.value to secondTeamLevel.value,
            thirdTeam.value to thirdTeamLevel.value)
            .filterKeys { it.isNotBlank() }

        launchCatching {
            firestoreService.createClubProfile(
                ClubAccount(
                    accountService.currentUserId,
                    clubName.value,
                    city.value,
                    bio.value,
                    mapOf("email" to Firebase.auth.currentUser?.email.orEmpty(),
                        "phoneNumber" to phoneNumber.value),
                    teamsMap,
                    trainingPlaceAndTime.value
                )
            )
            openAndPopUp(CLUB_HOME_SCREEN, CREATE_CLUB_PROFILE)
        }
    }
}