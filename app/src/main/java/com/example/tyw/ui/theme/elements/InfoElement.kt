package com.example.tyw.ui.theme.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tyw.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tyw.model.Weight
import com.example.tyw.ui.theme.BasicMargin
import com.example.tyw.ui.theme.HalfMargin
import com.example.tyw.utils.DateUtilitis

@Composable
fun InfoElementHomepageScreen(
    weight: Weight,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(HalfMargin())
            .height(58.dp)
            .background(Color(0xFFE6E6FA))
    ) {
        Row(
            modifier = Modifier
                .weight(1.0f)
                .align(Alignment.CenterVertically)
                .padding(horizontal = BasicMargin())
        ) {
            Column {
                Text(
                    text = weight.date?.let { DateUtilitis.getDateString(it) } ?: stringResource(id = R.string.No_date),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${weight.vaha} Kg",

                )
            }
        }
    }
}

data class PlaceholderScreenDefiniton(
    val image: Int? =null,
    val text: String? = null,
    )


@Composable
fun PlaceHolderScreen(
    definiton: PlaceholderScreenDefiniton
){
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(BasicMargin())){
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment =  Alignment.CenterHorizontally
        ){
            if(definiton.image != null){
                Image(
                    modifier = Modifier.size(300.dp),
                    painter = painterResource(id = definiton.image),
                    contentDescription = null)
                Spacer(modifier = Modifier.height(BasicMargin()))
            }
            if(definiton.text != null){
                Text(text = definiton.text, textAlign = TextAlign.Center)
            }
        }
    }
}

