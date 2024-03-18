package fi.tuni.sportsconnect

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fi.tuni.sportsconnect.screens.AddPostScreen
import fi.tuni.sportsconnect.screens.ClubHomeScreen
import fi.tuni.sportsconnect.screens.ClubProfileScreen
import fi.tuni.sportsconnect.screens.ClubSignUpScreen
import fi.tuni.sportsconnect.screens.CreateClubProfileScreen
import fi.tuni.sportsconnect.screens.CreatePlayerProfileScreen
import fi.tuni.sportsconnect.screens.EditClubProfileScreen
import fi.tuni.sportsconnect.screens.EditPlayerProfileScreen
import fi.tuni.sportsconnect.screens.PlayerHomeScreen
import fi.tuni.sportsconnect.screens.PlayerProfileScreen
import fi.tuni.sportsconnect.screens.SignInScreen
import fi.tuni.sportsconnect.screens.SignUpScreen
import fi.tuni.sportsconnect.screens.SplashScreen
import fi.tuni.sportsconnect.ui.theme.SportsConnectTheme
import fi.tuni.sportsconnect.viewModels.NavViewModel

@Composable
fun SportsConnectApp(
    viewModel: NavViewModel = hiltViewModel()
) {
    val userIsPlayer = viewModel.userIsPlayer.collectAsState()

    SportsConnectTheme {
        val playerNavItems = listOf(
            BottomNavItem(
                title = "Etusivu",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                hasNews = false,
                route = PLAYER_HOME_SCREEN,
            ),
            BottomNavItem(
                title = "Profiili",
                selectedIcon = Icons.Filled.AccountCircle,
                unselectedIcon = Icons.Outlined.AccountCircle,
                hasNews = false,
                route = PLAYER_PROFILE_SCREEN
            ),
        )

        val clubNavItems = listOf(
            BottomNavItem(
                title = "Etusivu",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                hasNews = false,
                route = CLUB_HOME_SCREEN,
            ),
            BottomNavItem(
                title = "Julkaise",
                selectedIcon = Icons.Filled.AddCircle,
                unselectedIcon = Icons.Outlined.Add,
                hasNews = false,
                route = ADD_POST_SCREEN
            ),
            BottomNavItem(
                title = "Profiili",
                selectedIcon = Icons.Filled.AccountCircle,
                unselectedIcon = Icons.Outlined.AccountCircle,
                hasNews = false,
                route = CLUB_PROFILE_SCREEN
            ),
        )

        var selectedNavItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }

        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState()

            Scaffold(
                bottomBar = {
                    if(appState.shouldShowBottomNavBar) {
                        viewModel.isUserPlayer()
                        if(userIsPlayer.value) {
                            NavigationBar {
                                playerNavItems.forEachIndexed { index, bottomNavItem ->
                                    NavigationBarItem(
                                        label = {
                                            Text(bottomNavItem.title)
                                        },
                                        selected = selectedNavItemIndex == index,
                                        onClick = {
                                            selectedNavItemIndex = index
                                            appState.navigate(bottomNavItem.route)
                                        },
                                        icon = {
                                            BadgedBox(
                                                badge = {
                                                    if (bottomNavItem.hasNews) {
                                                        Badge()
                                                    }
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = if (index == selectedNavItemIndex) {
                                                        bottomNavItem.selectedIcon
                                                    } else bottomNavItem.unselectedIcon,
                                                    ""
                                                )
                                            }
                                        })
                                }
                            }
                        } else {
                            NavigationBar {
                                clubNavItems.forEachIndexed { index, bottomNavItem ->
                                    NavigationBarItem(
                                        label = {
                                            Text(bottomNavItem.title)
                                        },
                                        selected = selectedNavItemIndex == index,
                                        onClick = {
                                            selectedNavItemIndex = index
                                            appState.navigate(bottomNavItem.route)
                                        },
                                        icon = {
                                            BadgedBox(
                                                badge = {
                                                    if (bottomNavItem.hasNews) {
                                                        Badge()
                                                    }
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = if (index == selectedNavItemIndex) {
                                                        bottomNavItem.selectedIcon
                                                    } else bottomNavItem.unselectedIcon,
                                                    ""
                                                )
                                            }
                                        })
                                }
                            }
                        }
                    }
                }
            ) {innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    Log.d("NavHost", "Mennään jo eteenpäin")
                    sportsConnectGraph(appState)
                }
            }
        }
    }
}


@Composable
fun rememberAppState(navController: NavHostController = rememberNavController()) =
    remember(navController) {
        AppState(navController)
    }

fun NavGraphBuilder.sportsConnectGraph(appState: AppState) {
    composable(PLAYER_HOME_SCREEN) {

        PlayerHomeScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openScreen = { route -> appState.navigate(route) }
        )
    }

    composable(CLUB_HOME_SCREEN) {

        ClubHomeScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openScreen = { route -> appState.navigate(route) }
        )
    }

    composable(ADD_POST_SCREEN) {
        AddPostScreen(openAndPopUp = { route, popUp, -> appState.navigateAndPopUp(route, popUp)})
    }

    composable(PLAYER_PROFILE_SCREEN) {
        PlayerProfileScreen(
            openAndPopUp = { route, popUp, -> appState.navigateAndPopUp(route, popUp) },
            restartApp = { route -> appState.clearAndNavigate(route) }
        )
    }

    composable(CLUB_PROFILE_SCREEN) {
        ClubProfileScreen(
            openAndPopUp = { route, popUp, -> appState.navigateAndPopUp(route, popUp) },
            restartApp = { route -> appState.clearAndNavigate(route) }
        )
    }

    composable(EDIT_CLUB_PROFILE_SCREEN) {
        EditClubProfileScreen(openAndPopUp = { route, popUp, -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(EDIT_PLAYER_PROFILE_SCREEN) {
        EditPlayerProfileScreen(openAndPopUp = { route, popUp, -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(SIGN_IN_SCREEN) {
        SignInScreen(openAndPopUp = { route, popUp, -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(PLAYER_SIGN_UP_SCREEN) {
        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(CLUB_SIGN_UP_SCREEN) {
        ClubSignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(CREATE_PLAYER_PROFILE) {
        CreatePlayerProfileScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(CREATE_CLUB_PROFILE) {
        CreateClubProfileScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
}

data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val route: String
)
