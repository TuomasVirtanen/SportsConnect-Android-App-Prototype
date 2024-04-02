package fi.tuni.sportsconnect.viewModels

import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import fi.tuni.sportsconnect.ADD_POST_SCREEN
import fi.tuni.sportsconnect.CLUB_PROFILE_SCREEN
import fi.tuni.sportsconnect.model.AccountService
import fi.tuni.sportsconnect.model.ClubAccount
import fi.tuni.sportsconnect.model.FirestoreService
import fi.tuni.sportsconnect.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService
): SportsConnectAppViewModel(){
    val position = MutableStateFlow ("")
    val text = MutableStateFlow("")
    private val clubAccount = MutableStateFlow(ClubAccount())
    val expanded = MutableStateFlow(false)

    fun updatePosition(newPosition: String) {
        position.value = newPosition
    }

    fun updateText(newText: String) {
        text.value = newText
    }

    fun updateExpanded() {
        expanded.value = !expanded.value
    }

    fun onFinishClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            clubAccount.value = firestoreService.readClubProfile(accountService.currentUserId)!!
            firestoreService.createPost(
                Post(
                    text = text.value,
                    positions = mutableListOf(position.value),
                    club = mutableMapOf(
                        "clubId" to clubAccount.value.id,
                        "clubName" to clubAccount.value.clubName,
                        "city" to clubAccount.value.city,
                        "level" to clubAccount.value.leagueLevel
                    ),
                    created = Timestamp(Date())
                )
            )
            openAndPopUp(CLUB_PROFILE_SCREEN, ADD_POST_SCREEN)
        }
    }
}