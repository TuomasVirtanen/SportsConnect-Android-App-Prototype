package fi.tuni.sportsconnect.viewModels

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import fi.tuni.sportsconnect.CLUB_HOME_SCREEN
import fi.tuni.sportsconnect.CLUB_PROFILE_SCREEN
import fi.tuni.sportsconnect.CREATE_CLUB_PROFILE
import fi.tuni.sportsconnect.EDIT_CLUB_PROFILE_SCREEN
import fi.tuni.sportsconnect.model.AccountService
import fi.tuni.sportsconnect.model.ClubAccount
import fi.tuni.sportsconnect.model.FirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class EditClubProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService
): SportsConnectAppViewModel() {
    val club = MutableStateFlow(ClubAccount())
    val clubName = MutableStateFlow("")
    val city = MutableStateFlow("")
    val bio = MutableStateFlow("")
    val phoneNumber = MutableStateFlow("")
    val trainingPlaceAndTime = MutableStateFlow("")

    fun initialize() {
        launchCatching {
            club.value = firestoreService.readClubProfile(accountService.currentUserId)!!
            clubName.value = club.value.clubName
            city.value = club.value.city
            bio.value = club.value.bio.orEmpty()
            phoneNumber.value = club.value.contactInfo?.get("phoneNumber").orEmpty()
            trainingPlaceAndTime.value = club.value.trainingPlaceAndTime.orEmpty()
        }
    }

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

    fun updateTrainingPlaceAndTime(newTrainingPlaceAndTime: String) {
        trainingPlaceAndTime.value = newTrainingPlaceAndTime
    }

    fun onFinishClick(openAndPopUp: (String, String) -> Unit) {

        launchCatching {
            firestoreService.createClubProfile(
                ClubAccount(
                    club.value.id,
                    clubName.value,
                    city.value,
                    bio.value,
                    mapOf("email" to Firebase.auth.currentUser?.email.orEmpty(),
                        "phoneNumber" to phoneNumber.value),
                    trainingPlaceAndTime.value
                )
            )
            openAndPopUp(CLUB_PROFILE_SCREEN, EDIT_CLUB_PROFILE_SCREEN)
        }
    }
}