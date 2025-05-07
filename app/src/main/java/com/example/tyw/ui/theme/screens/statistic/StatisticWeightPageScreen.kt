package com.example.tyw.ui.theme.screens.statistic

import android.content.Context
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tyw.R
import com.example.tyw.model.Weight
import com.example.tyw.navigations.INavigationsRouter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import androidx.compose.ui.viewinterop.AndroidView
import com.example.tyw.ui.theme.BasicMargin
import com.example.tyw.ui.theme.elements.PlaceHolderScreen
import com.example.tyw.ui.theme.elements.PlaceholderScreenDefiniton
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.components.XAxis
import com.example.tyw.utils.DateUtilitis


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticWeightPageScreen(navigationRouter: INavigationsRouter) {

    val viewModel = hiltViewModel<StatisticWeightViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.Statistic))
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
        Box(modifier = Modifier.padding(it)) {
            when (uiState) {
                is StatisticWeightUIState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    )
                }
                is StatisticWeightUIState.Success -> {
                    StatisitcScreen(weightData = (uiState as StatisticWeightUIState.Success).weight)
                }
            }
        }
    }
}

@Composable
fun StatisitcScreen(weightData: List<Weight>) {

    if (weightData.isEmpty()) {
        PlaceHolderScreen(
            PlaceholderScreenDefiniton(
                image = R.drawable.statistic_picture,
                text = stringResource(id = R.string.no_weight_track)
            )
        )
    } else {
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(BasicMargin())
        ) {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp),
                factory = { ctx ->
                    LineChart(ctx).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                },
                update = { lineChart ->
                    lineChart.data = createLineChartData(weightData, context)
                    lineChart.description.text = context.getString(R.string.Time)
                    lineChart.xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        valueFormatter = DateAxisValueFormatter()
                        labelRotationAngle = -45f
                    }
                    lineChart.axisRight.isEnabled = false
                    lineChart.axisLeft.valueFormatter = WeightAxisValueFormatter()
                    lineChart.invalidate()
                }
            )
        }
    }
}

fun createLineChartData(weightData: List<Weight>, context: Context): LineData {
    val entries = weightData.map { weight ->
        Entry(weight.date?.toFloat() ?: 0f, weight.vaha.toFloat())
    }

    val dataSet = LineDataSet(entries, context.getString(R.string.weight))
    dataSet.color = ContextCompat.getColor(context, R.color.purple_200)
    dataSet.setCircleColor(ContextCompat.getColor(context, R.color.purple_200))
    dataSet.setDrawValues(false)
    dataSet.lineWidth = 2f
    dataSet.circleRadius = 4f

    return LineData(dataSet)
}

class DateAxisValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return DateUtilitis.getDateString(value.toLong())
    }
}

class WeightAxisValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return "$value Kg"
    }
}
