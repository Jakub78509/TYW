package com.example.tyw

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tyw.datastore.IDataStoreRepository
import com.example.tyw.navigations.Destinations
import com.example.tyw.navigations.NavGraph
import com.example.tyw.ui.theme.TYWTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import com.example.tyw.ui.theme.screens.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var dataStoreRepository: IDataStoreRepository

    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        authRepository = AuthRepository(firebaseAuth)

        setContent {
            TYWTheme {
                NavGraph(startDestinations = Destinations.SplashScreen.route, authRepository = authRepository, dataStoreRepository = dataStoreRepository)
            }
        }
    }
}
