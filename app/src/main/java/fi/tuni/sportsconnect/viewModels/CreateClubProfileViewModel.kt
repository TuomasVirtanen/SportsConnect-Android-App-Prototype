package fi.tuni.sportsconnect.viewModels

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import fi.tuni.sportsconnect.CLUB_HOME_SCREEN
import fi.tuni.sportsconnect.CREATE_CLUB_PROFILE
import fi.tuni.sportsconnect.model.AccountService
import fi.tuni.sportsconnect.model.ClubAccount
import fi.tuni.sportsconnect.model.FirestoreService
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
    val leagueLevel = MutableStateFlow("")
    val phoneNumber = MutableStateFlow("")
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

    fun updateLeagueLevel(newLeagueLevel: String) {
        leagueLevel.value = newLeagueLevel
    }

    fun updatePhoneNumber(newPhoneNumber: String) {
        phoneNumber.value = newPhoneNumber
    }

    fun updateTrainingPlaceAndTime(newTrainingPlaceAndTime: String) {
        trainingPlaceAndTime.value = newTrainingPlaceAndTime
    }

    fun onFinishClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            firestoreService.createClubProfile(
                ClubAccount(
                    accountService.currentUserId,
                    clubName.value,
                    city.value,
                    bio.value,
                    mapOf("email" to Firebase.auth.currentUser?.email.orEmpty(),
                        "phoneNumber" to phoneNumber.value),
//                    teamsMap,
                    trainingPlaceAndTime.value
                )
            )
            openAndPopUp(CLUB_HOME_SCREEN, CREATE_CLUB_PROFILE)
        }
    }
}