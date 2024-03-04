package fi.tuni.sportsconnect.viewModels

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import fi.tuni.sportsconnect.model.FirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class NavViewModel @Inject constructor(
    private val firestoreService: FirestoreService,
): SportsConnectAppViewModel() {
    val userIsPlayer = MutableStateFlow(false)
    fun isUserPlayer() {
        launchCatching {
            Log.d("isUserPlayer", "Aloitetaan yhteys")
            userIsPlayer.value = firestoreService.isUserPlayer()
            Log.d("isUserPlayer", "Lopetetaan yhteys")
        }
    }
}