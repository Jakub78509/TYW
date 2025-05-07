package com.example.tyw.ui.theme.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.tyw.ui.theme.BasicMargin
import com.example.tyw.ui.theme.HalfMargin

@Composable
fun AddElement(
    icon: ImageVector,
    value: String?,
    hint: String,
    onClick: () -> Unit,
    onClearClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(start = BasicMargin(), end = BasicMargin(), top = HalfMargin(), bottom = HalfMargin()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(HalfMargin()))
        Column {
            Text(text = value ?: "Not set")
            Text(text = hint)
        }
        Spacer(modifier = Modifier.weight(1.0f))
        if (!value.isNullOrEmpty()) {
            IconButton(onClick = onClearClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = Color.Red)
            }
        }
    }
}
