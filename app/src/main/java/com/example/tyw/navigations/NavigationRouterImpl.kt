package com.example.tyw.navigations

import androidx.navigation.NavController

class NavigationRouterImpl(private val navController: NavController) : INavigationsRouter{

    override fun getNavController(): NavController {
        return navController
    }

    override fun navigateToTargetWeightPageScreen() {
        navController.navigate(Destinations.TargetWeightPageScreen.route)
    }

    override fun navigateToAddEditWeightPageScreen(id: Long?) {
        if(id != null){
            navController.navigate(Destinations.AddEditScreen.route + "/" + id)
        }
        else{
            navController.navigate(Destinations.AddEditScreen.route)
        }
    }

    override fun navigateToStatisticWeightPageScreen() {
        navController.navigate(Destinations.StatiscitScreen.route)
    }

    override fun navigateToBMIWeightPageScreen() {
        navController.navigate(Destinations.BmiScreen.route)
    }

    override fun returnBack() {
        navController.popBackStack()
    }

    override fun navigateToSingUpScreen() {
        navController.navigate(Destinations.Sing_up.route)
    }

    override fun navigateToSignInScreen() {
        navController.navigate(Destinations.Sing_in.route)
    }

    override fun navigateToHomePageScreen() {
        navController.navigate(Destinations.HomePageScreen.route)
    }

    override fun navigateToVersionPageScreen() {
        navController.navigate(Destinations.VerisonScreen.route)
    }

}