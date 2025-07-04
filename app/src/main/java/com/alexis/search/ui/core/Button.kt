package com.alexis.search.ui.core

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ShowButton(modifier: Modifier, label: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        shape = CircleShape,
        colors = buttonColors(containerColor = Color.Blue, contentColor = Color.White),
        modifier = modifier
            .height(50.dp)
    ) {
        Text(text = label)
    }
}