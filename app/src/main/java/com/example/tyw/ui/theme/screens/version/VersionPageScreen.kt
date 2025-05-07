package com.example.tyw.ui.theme.screens.version

import android.content.Context
import android.content.pm.PackageInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tyw.R
import com.example.tyw.navigations.INavigationsRouter
import com.example.tyw.ui.theme.BasicMargin
import com.example.tyw.ui.theme.HalfMargin

fun Context.getAppVersionName(): String {
    val pInfo: PackageInfo = this.packageManager.getPackageInfo(this.packageName, 0)
    return pInfo.versionName
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VersionPageScreen(navigationRouter: INavigationsRouter) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.application_version))
        }, navigationIcon = {
            IconButton(onClick = { navigationRouter.returnBack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "arrow_back",
                    tint = Color(0xFF6200EE)
                )
            }
        })
    }) {
        VersionPageScreenContent(paddingValues = it)
    }
}

@Composable
fun VersionPageScreenContent(paddingValues: PaddingValues) {
    val context = LocalContext.current
    val versionName = remember { context.getAppVersionName() }
    val appName = stringResource(id = R.string.app_name)
    val authorName = "Jakub Procházka (xproch40)"
    val subject = stringResource(id = R.string.subject_android)

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = appName,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE)
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(BasicMargin())
                .background(Color(0xFFEDE7F6), shape = MaterialTheme.shapes.medium)
                .padding(HalfMargin())
        )
        Spacer(modifier = Modifier.height(BasicMargin()))
        Text(
            text = "Version: $versionName",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(BasicMargin())
                .background(Color(0xFFF1F1F1), shape = MaterialTheme.shapes.medium)
                .padding(HalfMargin())
        )
        Spacer(modifier = Modifier.height(BasicMargin()))
        Text(
            text = authorName,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                color = Color.Gray
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(BasicMargin())
                .background(Color(0xFFF1F1F1), shape = MaterialTheme.shapes.medium)
                .padding(HalfMargin())
        )
        Spacer(modifier = Modifier.height(BasicMargin()))
        Text(
            text = subject,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                color = Color.Gray
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(BasicMargin())
                .background(Color(0xFFF1F1F1), shape = MaterialTheme.shapes.medium)
                .padding(HalfMargin())
        )
    }
}
