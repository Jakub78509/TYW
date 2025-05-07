package com.example.tyw.ui.theme.screens.homepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tyw.R
import com.example.tyw.datastore.IDataStoreRepository
import com.example.tyw.di.FirebaseDatabaseManager
import com.example.tyw.model.Weight
import com.example.tyw.navigations.INavigationsRouter
import com.example.tyw.ui.theme.BasicMargin
import com.example.tyw.ui.theme.elements.InfoElementHomepageScreen
import com.example.tyw.ui.theme.elements.PlaceHolderScreen
import com.example.tyw.ui.theme.elements.PlaceholderScreenDefiniton
import com.example.tyw.ui.theme.screens.AuthRepository
import kotlinx.coroutines.flow.firstOrNull

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageScreen(
    navigationRouter: INavigationsRouter,
    authRepository: AuthRepository,
    dataStoreRepository: IDataStoreRepository
) {
    val viewModel = hiltViewModel<HomePageViewModel>()
    val record: MutableList<Weight> = mutableListOf()

    viewModel.homePageUIState.value.let {
        when (it) {
            is HomePageUIState.Loading -> {
                viewModel.loadRecord(authRepository.getCurrentUserId() ?: "")
            }
            is HomePageUIState.Success -> {
                record.addAll(it.weight)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(text = stringResource(id = R.string.HomePage))
                    }
                },
                actions = {
                    IconButton(onClick = { navigationRouter.navigateToBMIWeightPageScreen() }) {
                        Image(
                            painter = painterResource(R.drawable.bmi),
                            contentDescription = "BMI",
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(Color(0xFF6200EE))
                        )
                    }
                    IconButton(onClick = { navigationRouter.navigateToStatisticWeightPageScreen() }) {
                        Image(
                            painter = painterResource(R.drawable.statisctic),
                            contentDescription = "Statistic",
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(Color(0xFF6200EE))
                        )
                    }
                    var expanded by remember { mutableStateOf(false) }
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More options",
                            tint = Color(0xFF6200EE)
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(id = R.string.Target))
                            },
                            onClick = { navigationRouter.navigateToTargetWeightPageScreen() },
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(id = R.string.application_version))
                            },
                            onClick = { navigationRouter.navigateToVersionPageScreen() },
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(id = R.string.log_out))
                            },
                            onClick = {
                                authRepository.signOut()
                                navigationRouter.navigateToSignInScreen()
                            },
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigationRouter.navigateToAddEditWeightPageScreen(null)
                },
                modifier = Modifier.padding(bottom = 55.dp),
                containerColor = Color(0xFF4CAF50)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        }
    ) {
        WeightListScreenContent(paddingValues = it, records = record, navigationRouter = navigationRouter, dataStoreRepository = dataStoreRepository, authRepository = authRepository)
    }
}

@Composable
fun WeightListScreenContent(
    paddingValues: PaddingValues,
    records: List<Weight>,
    navigationRouter: INavigationsRouter,
    dataStoreRepository: IDataStoreRepository,
    authRepository: AuthRepository
) {
    var user = authRepository.getCurrentUserId()
    if (records.isEmpty()) {
        PlaceHolderScreen(
            PlaceholderScreenDefiniton(
                image = R.drawable.undraw_add_notes_re_ln36,
                text = stringResource(id = R.string.no_weight_track)
            )
        )
    } else {
        var targetWeight by remember { mutableStateOf(0.0) }

        LaunchedEffect(user) {
            user?.let {
                dataStoreRepository.getWeight(userId = it).firstOrNull()?.let { weight ->
                    targetWeight = weight
                }

                records.forEach { weight ->
                    FirebaseDatabaseManager.uploadDataForUser(
                        weight = weight.vaha,
                        date = weight.date
                    )
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = BasicMargin())
                    .weight(1.0f)
            ) {
                records.forEach {
                    item {
                        InfoElementHomepageScreen(weight = it) {
                            navigationRouter.navigateToAddEditWeightPageScreen(it.id)
                        }
                    }
                }
            }

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(BasicMargin())
                .background(Color(0xFFB7B3E9))
                .height(55.dp)
            ) {
                Text(
                    text = "Target: $targetWeight Kg",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(BasicMargin())
                )
            }
        }
    }
}
