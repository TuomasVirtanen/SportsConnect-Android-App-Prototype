package fi.tuni.sportsconnect.viewModels

import dagger.hilt.android.lifecycle.HiltViewModel
import fi.tuni.sportsconnect.SPLASH_SCREEN
import fi.tuni.sportsconnect.model.AccountService
import fi.tuni.sportsconnect.model.FirestoreService
import fi.tuni.sportsconnect.model.PlayerAccount
import javax.inject.Inject

@HiltViewModel
class ClubHomeViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService
): SportsConnectAppViewModel() {
    val players = firestoreService.players
    fun initialize(restartApp: (String) -> Unit) {
        launchCatching {
            // TODO: kun tehdään filteröinti, players.value = ...
        }

        observeAuthState(restartApp)
    }

    private fun observeAuthState(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.currentUser.collect {user ->
                if(user == null) restartApp(SPLASH_SCREEN)
            }
        }
    }
}