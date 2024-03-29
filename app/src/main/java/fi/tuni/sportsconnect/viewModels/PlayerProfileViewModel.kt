package fi.tuni.sportsconnect.viewModels

import dagger.hilt.android.lifecycle.HiltViewModel
import fi.tuni.sportsconnect.CLUB_PROFILE_SCREEN
import fi.tuni.sportsconnect.EDIT_CLUB_PROFILE_SCREEN
import fi.tuni.sportsconnect.EDIT_PLAYER_PROFILE_SCREEN
import fi.tuni.sportsconnect.PLAYER_PROFILE_SCREEN
import fi.tuni.sportsconnect.SPLASH_SCREEN
import fi.tuni.sportsconnect.model.AccountService
import fi.tuni.sportsconnect.model.FirestoreService
import fi.tuni.sportsconnect.model.PlayerAccount
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class PlayerProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService
): SportsConnectAppViewModel() {
    val playerAccount = MutableStateFlow(PlayerAccount())
    val showActionButtons = MutableStateFlow(false)

    fun initialize(playerId: String, restartApp: (String) -> Unit) {
        launchCatching {
            playerAccount.value = firestoreService.readPlayerProfile(playerId) ?:
                firestoreService.readPlayerProfile(accountService.currentUserId) ?:
                PlayerAccount()
            showActionButtons.value =
                playerAccount.value == firestoreService.readPlayerProfile(accountService.currentUserId)
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

    fun onEditProfileClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(EDIT_PLAYER_PROFILE_SCREEN, PLAYER_PROFILE_SCREEN)
    }

    fun onSignOutClick() {
        launchCatching {
            accountService.signOut()
        }
    }

    fun onDeleteAccountClick() {
        launchCatching {
            firestoreService.deletePlayerProfile(accountService.currentUserId)
            accountService.deleteAccount()
        }
    }
}