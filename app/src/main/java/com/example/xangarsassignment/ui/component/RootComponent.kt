package com.example.xangarsassignment.ui.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.xangarsassignment.ui.screens.Destinations
import com.example.xangarsassignment.ui.screens.home.HomeScreen
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.example.xangarsassignment.ui.screens.note.note.NoteScreen
import com.example.xangarsassignment.ui.screens.note.preview.PreviewScreen
import com.example.xangarsassignment.ui.screens.search.SearchScreen
import com.example.xangarsassignment.ui.theme.XangarsAssignmentTheme


@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun RootComponent() {
    val isDark = isSystemInDarkTheme()
  //  val systemUiController = rememberSystemUiController()
    val uriHandler = LocalUriHandler.current
    XangarsAssignmentTheme(isDark) {
       // val darkIcons = !isDark
      //  SideEffect { systemUiController.setSystemBarsColor(Color.Transparent, darkIcons) }
        Surface(color = MaterialTheme.colorScheme.background) {
            val scrollState = rememberLazyListState()
            val bottomSheetNavigator = rememberBottomSheetNavigator()
            val navController = rememberNavController(bottomSheetNavigator)
            ModalBottomSheetLayout(bottomSheetNavigator) {
                NavHost(
                    navController = navController,
                    startDestination = Destinations.HOME_ROUTE
                ) {
                    composable(Destinations.HOME_ROUTE) {
                        HomeScreen(isDark, scrollState) { navController.navigate(it) }
                    }
                    composable(Destinations.NOTE_ROUTE) {
                        NoteScreen({ navController.navigate(it) })
                        { navController.navigateUp() }
                    }
                    composable(Destinations.SEARCH_ROUTE) { SearchScreen(navController) }
                    composable(Destinations.PREVIEW_ROUTE) {
                        PreviewScreen({ navController.navigate(it) })
                        { navController.navigateUp() }
                    }
                }
            }
        }
    }
}
