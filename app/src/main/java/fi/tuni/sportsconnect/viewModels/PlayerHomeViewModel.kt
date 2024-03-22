package fi.tuni.sportsconnect.viewModels

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fi.tuni.sportsconnect.CLUB_ACCOUNT_ID
import fi.tuni.sportsconnect.CLUB_PROFILE_SCREEN
import fi.tuni.sportsconnect.SPLASH_SCREEN
import fi.tuni.sportsconnect.model.AccountService
import fi.tuni.sportsconnect.model.FirestoreService
import fi.tuni.sportsconnect.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlayerHomeViewModel @Inject constructor(
    private val accountService: AccountService, private val firestoreService: FirestoreService
) : SportsConnectAppViewModel() {
    val filters = MutableStateFlow(
        mutableMapOf<String, MutableList<String>>(
            Pair("cities", mutableListOf()),
            Pair("positions", mutableListOf()),
            Pair("levels", mutableListOf())
        )
    )
    val filteredPosts = MutableStateFlow<List<Post>>(emptyList())

    fun updatePosts() {
        launchCatching {
            firestoreService.posts.collect { posts ->
                filteredPosts.value = posts.filter {
                    (filters.value["positions"]?.isEmpty() ?: true || filters.value["positions"]?.any { position ->
                        it.positions.contains(position)
                    } ?: true) &&
                    (filters.value["cities"]?.isEmpty() ?: true || filters.value["cities"]?.any { city ->
                        it.club["city"] == city
                    } ?: true) &&
                    (filters.value["levels"]?.isEmpty()
                        ?: true) || filters.value["levels"]?.any { level -> it.club["level"] == level } ?: true
                }.sortedByDescending { it.created }
                Log.d("posts", filteredPosts.toString())
            }
        }
    }

    fun initialize(restartApp: (String) -> Unit) {
        launchCatching {
            updatePosts()
        }

        observeAuthState(restartApp)
    }

    private fun observeAuthState(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.currentUser.collect { user ->
                if (user == null) restartApp(SPLASH_SCREEN)
            }
        }
    }

    fun onCityFilterChange(filter: String) {
        if (filters.value["cities"]?.any { city -> filter == city } == false) {
            filters.value["cities"]?.add(filter)
        } else {
            filters.value["cities"]?.remove(filter)
        }
    }

    fun onPositionFilterChange(filter: String) {
        if (filters.value["positions"]?.any { pos -> filter == pos } == false) {
            filters.value["positions"]?.add(filter)
        } else {
            filters.value["positions"]?.remove(filter)
        }
    }

    fun onLevelFilterChange(filter: String) {
        if (filters.value["levels"]?.any { level -> filter == level } == false) {
            filters.value["levels"]?.add(filter)
        } else {
            filters.value["levels"]?.remove(filter)
        }
    }

    fun clearFilters() {
        filters.value = mutableMapOf(
            Pair("positions", mutableListOf()),
            Pair("cities", mutableListOf()),
            Pair("levels", mutableListOf())
        )
    }

    fun onProfileClick(openScreen: (String) -> Unit, clubId: String) {
        openScreen("$CLUB_PROFILE_SCREEN?$CLUB_ACCOUNT_ID=${clubId}")
    }
}