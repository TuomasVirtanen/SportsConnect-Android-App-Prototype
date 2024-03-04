package fi.tuni.sportsconnect.viewModels

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import fi.tuni.sportsconnect.CLUB_HOME_SCREEN
import fi.tuni.sportsconnect.CLUB_SIGN_UP_SCREEN
import fi.tuni.sportsconnect.PLAYER_HOME_SCREEN
import fi.tuni.sportsconnect.PLAYER_SIGN_UP_SCREEN
import fi.tuni.sportsconnect.SIGN_IN_SCREEN
import fi.tuni.sportsconnect.model.AccountService
import fi.tuni.sportsconnect.model.FirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class EmailSignInViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService
): SportsConnectAppViewModel() {
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            accountService.signIn(email.value, password.value)
            Log.d("onko käyttäjä pelaaja", firestoreService.isUserPlayer().toString())
            if(firestoreService.isUserPlayer()) {
                openAndPopUp(PLAYER_HOME_SCREEN, SIGN_IN_SCREEN)
            } else openAndPopUp(CLUB_HOME_SCREEN, SIGN_IN_SCREEN)
        }
    }

    fun onPlayerSignUpClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(PLAYER_SIGN_UP_SCREEN, SIGN_IN_SCREEN)
    }

    fun onClubSignUpClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(CLUB_SIGN_UP_SCREEN, SIGN_IN_SCREEN)
    }
}