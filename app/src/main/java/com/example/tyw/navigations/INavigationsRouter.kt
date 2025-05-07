package com.example.tyw.navigations

import androidx.navigation.NavController

interface INavigationsRouter {
    fun getNavController(): NavController
    fun navigateToTargetWeightPageScreen()
    fun navigateToAddEditWeightPageScreen(id: Long?)
    fun navigateToStatisticWeightPageScreen()
    fun navigateToBMIWeightPageScreen()
    fun returnBack()
    fun navigateToSingUpScreen()
    fun navigateToSignInScreen()
    fun navigateToHomePageScreen()
    fun navigateToVersionPageScreen()

}