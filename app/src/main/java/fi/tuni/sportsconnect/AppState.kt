package fi.tuni.sportsconnect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Stable
class AppState(
    val navController: NavHostController
) {
    val shouldShowBottomNavBar: Boolean
        @Composable get() = navController
                    .currentBackStackEntryAsState().value?.destination?.route?.startsWithAny(
                    arrayOf(PLAYER_HOME_SCREEN, CLUB_HOME_SCREEN, PLAYER_PROFILE_SCREEN, CLUB_PROFILE_SCREEN)
                    ) == true

    private fun String?.startsWithAny(prefixes: Array<String>): Boolean {
        return this != null && prefixes.any { this.startsWith(it) }
    }

//    fun popUp() {
//        navController.popBackStack()
//    }

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