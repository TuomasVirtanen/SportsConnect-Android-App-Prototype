package fi.tuni.sportsconnect.viewModels

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import fi.tuni.sportsconnect.CLUB_PROFILE_SCREEN
import fi.tuni.sportsconnect.EDIT_CLUB_PROFILE_SCREEN
import fi.tuni.sportsconnect.SPLASH_SCREEN
import fi.tuni.sportsconnect.model.AccountService
import fi.tuni.sportsconnect.model.ClubAccount
import fi.tuni.sportsconnect.model.FirestoreService
import fi.tuni.sportsconnect.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ClubProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService
): SportsConnectAppViewModel() {
    val clubAccount = MutableStateFlow(ClubAccount())
    val showActionButtons = MutableStateFlow(false)
    val clubPosts = MutableStateFlow<List<Post>>(emptyList())


    fun initialize(clubId: String, restartApp: (String) -> Unit) {
        launchCatching {
            clubAccount.value =
                firestoreService.readClubProfile(clubId) ?: firestoreService.readClubProfile(
                    accountService.currentUserId
                ) ?: ClubAccount()
            showActionButtons.value =
                clubAccount.value == firestoreService.readClubProfile(accountService.currentUserId)
            firestoreService.posts.collect { posts ->
                clubPosts.value = posts.filter {
                    Log.d("haluun nukkuun", (it.club["clubId"] == clubAccount.value.id).toString())
                    it.club["clubId"] == clubAccount.value.id
                }.sortedByDescending { it.created }
            }
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
        openAndPopUp(EDIT_CLUB_PROFILE_SCREEN, CLUB_PROFILE_SCREEN)
    }

    fun onSignOutClick() {
        launchCatching {
            accountService.signOut()
        }
    }

    fun onDeleteAccountClick() {
        launchCatching {
            firestoreService.deleteClubProfile(accountService.currentUserId)
            accountService.deleteAccount()
        }
    }
}