package com.example.tyw.navigations



import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tyw.datastore.IDataStoreRepository
import com.example.tyw.ui.theme.screens.AddEdit.AddEditWeightPageScreen
import com.example.tyw.ui.theme.screens.AuthRepository
import com.example.tyw.ui.theme.screens.bmi.BmiWeightPageScreen
import com.example.tyw.ui.theme.screens.homepage.HomePageScreen
import com.example.tyw.ui.theme.screens.sign_in.SignInScreen
import com.example.tyw.ui.theme.screens.sign_up.SignUpScreen
import com.example.tyw.ui.theme.screens.splash.SplashPageScreen
import com.example.tyw.ui.theme.screens.statistic.StatisticWeightPageScreen
import com.example.tyw.ui.theme.screens.targetweight.TargetWeightPageScreen
import com.example.tyw.ui.theme.screens.version.VersionPageScreen


@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestinations: String,
    dataStoreRepository: IDataStoreRepository,
    authRepository: AuthRepository,
    navigationRouter: INavigationsRouter = remember {
        NavigationRouterImpl(navController)
    }
            ){

    NavHost(navController = navController, startDestination = Destinations.SplashScreen.route) {
        composable(Destinations.SplashScreen.route) {
            SplashPageScreen {
                val startDestination = if (authRepository.getCurrentUserId() == null) {
                    Destinations.Sing_in.route
                } else {
                    Destinations.HomePageScreen.route
                }
                navController.navigate(startDestination) {
                    popUpTo(Destinations.SplashScreen.route) { inclusive = true }
                }
            }
        }
        composable(Destinations.HomePageScreen.route){
            HomePageScreen(navigationRouter = navigationRouter, authRepository = authRepository, dataStoreRepository = dataStoreRepository)

        }
        composable(Destinations.TargetWeightPageScreen.route){
            TargetWeightPageScreen(navigationRouter = navigationRouter)
        }

        composable(Destinations.AddEditScreen.route + "/{id}",
            arguments = listOf(
                navArgument(name = "id"){
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ){
            val id = it.arguments?.getLong("id")
            AddEditWeightPageScreen(navigationRouter = navigationRouter, id = id)
        }

        composable(Destinations.AddEditScreen.route){
            AddEditWeightPageScreen(navigationRouter = navigationRouter, id = null)
        }

        composable(Destinations.BmiScreen.route){
            BmiWeightPageScreen(navigationRouter = navigationRouter)
        }

        composable(Destinations.StatiscitScreen.route){
            StatisticWeightPageScreen(navigationRouter = navigationRouter)
        }

        composable(Destinations.Sing_up.route){
            SignUpScreen(navigationRouter = navigationRouter)
        }
        composable(Destinations.Sing_in.route){
            SignInScreen(navigationRouter = navigationRouter)
        }

        composable(Destinations.VerisonScreen.route){
            VersionPageScreen(navigationRouter = navigationRouter)
        }



    }
}
