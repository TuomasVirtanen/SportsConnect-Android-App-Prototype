package fi.tuni.sportsconnect.viewModels

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import fi.tuni.sportsconnect.PLAYER_ACCOUNT_ID
import fi.tuni.sportsconnect.PLAYER_PROFILE_SCREEN
import fi.tuni.sportsconnect.SPLASH_SCREEN
import fi.tuni.sportsconnect.model.AccountService
import fi.tuni.sportsconnect.model.FirestoreService
import fi.tuni.sportsconnect.model.PlayerAccount
import fi.tuni.sportsconnect.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.singleOrNull
import javax.inject.Inject

@HiltViewModel
class ClubHomeViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService
): SportsConnectAppViewModel() {
    val filters = MutableStateFlow(
        mutableMapOf<String, MutableList<String>>(
            Pair("cities", mutableListOf()),
            Pair("positions", mutableListOf()),
            Pair("levels", mutableListOf())
        )
    )
    val shownPlayers = MutableStateFlow<List<PlayerAccount>>(emptyList())

    fun initialize(restartApp: (String) -> Unit) {
        launchCatching {
            updatePlayers()
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

    fun updatePlayers() {
        launchCatching {
            firestoreService.players.collect { players ->
                shownPlayers.value = players.filter {
                    (filters.value["positions"]?.isEmpty() ?: true || filters.value["positions"]?.any { position ->
                        it.positions?.contains(position) ?: true
                    } ?: true) &&
                            (filters.value["cities"]?.isEmpty() ?: true || filters.value["cities"]?.any { city ->
                                it.cities?.contains(city) ?: true
                            } ?: true) &&
                            (filters.value["levels"]?.isEmpty()
                                ?: true) || filters.value["levels"]?.any { level ->
                                    it.leagueLevels?.contains(level) ?: true
                                } ?: true
                }.sortedByDescending { it.searchingForTeam}
                Log.d("posts", players.toString())
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

    fun onProfileClick(openScreen: (String) -> Unit, playerId: String) {
        openScreen("$PLAYER_PROFILE_SCREEN?$PLAYER_ACCOUNT_ID=${playerId}")
    }
}