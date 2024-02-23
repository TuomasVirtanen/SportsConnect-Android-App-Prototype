package fi.tuni.sportsconnect.viewModels

import dagger.hilt.android.lifecycle.HiltViewModel
import fi.tuni.sportsconnect.CLUB_SIGN_UP_SCREEN
import fi.tuni.sportsconnect.CREATE_CLUB_PROFILE
import fi.tuni.sportsconnect.CREATE_PLAYER_PROFILE
import fi.tuni.sportsconnect.PLAYER_SIGN_UP_SCREEN
import fi.tuni.sportsconnect.model.AccountService
import fi.tuni.sportsconnect.model.FirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ClubSignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService
) : SportsConnectAppViewModel() {
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        confirmPassword.value = newConfirmPassword
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            if (password.value != confirmPassword.value) {
                throw Exception("Salasanat eivät täsmää")
            }

            accountService.signUp(email.value, password.value)
            openAndPopUp(CREATE_CLUB_PROFILE, CLUB_SIGN_UP_SCREEN)
        }
    }
}