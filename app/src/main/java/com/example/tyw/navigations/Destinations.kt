package com.example.tyw.navigations

sealed class Destinations (val route: String){
    object HomePageScreen: Destinations("home_page_scren")
    object TargetWeightPageScreen: Destinations("target_weight_page_screen")
    object AddEditScreen: Destinations("add_edit_weight_page_screen")
    object BmiScreen: Destinations("bmi_weight_page_screen")
    object StatiscitScreen: Destinations("statistic_weight_page_screen")
    object Sing_up: Destinations("sign_up_screen")
    object Sing_in: Destinations("sign_in_screen")
    object SplashScreen: Destinations("Splash_screen")
    object VerisonScreen: Destinations("Version_screen")

}