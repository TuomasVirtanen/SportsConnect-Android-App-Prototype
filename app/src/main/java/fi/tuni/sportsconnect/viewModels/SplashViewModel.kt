package fi.tuni.sportsconnect.viewModels

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import fi.tuni.sportsconnect.CLUB_HOME_SCREEN
import fi.tuni.sportsconnect.PLAYER_HOME_SCREEN
import fi.tuni.sportsconnect.SIGN_IN_SCREEN
import fi.tuni.sportsconnect.SPLASH_SCREEN
import fi.tuni.sportsconnect.model.AccountService
import fi.tuni.sportsconnect.model.FirestoreService
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService
) : SportsConnectAppViewModel() {

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            if (accountService.hasUser() && firestoreService.isUserPlayer()) {
                openAndPopUp(PLAYER_HOME_SCREEN, SPLASH_SCREEN)
            } else if(accountService.hasUser()) {
                openAndPopUp(
                    CLUB_HOME_SCREEN, SPLASH_SCREEN
                )
            } else {
                openAndPopUp(SIGN_IN_SCREEN, SPLASH_SCREEN)
            }
        }
    }
}