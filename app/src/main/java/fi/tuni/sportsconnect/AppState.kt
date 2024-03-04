package fi.tuni.sportsconnect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import fi.tuni.sportsconnect.model.FirestoreService

@Stable
class AppState(
    val navController: NavHostController
) {
    val shouldShowBottomNavBar: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in
                listOf(PLAYER_HOME_SCREEN, CLUB_HOME_SCREEN, PLAYER_PROFILE_SCREEN, CLUB_PROFILE_SCREEN)
    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        navController.navigate(route) { launchSingleTop = true }
    }

    fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }
}