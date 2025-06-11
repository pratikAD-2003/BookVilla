package com.pycreations.bookvilla.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.pycreations.bookvilla.R

@Composable
fun GenreItemLy(text: String, isSelected: Boolean, onClick: (String) -> Unit,modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(color = if (isSelected) colorResource(R.color.blue) else colorResource(R.color.screen_clr))
            .clickable {
                onClick(text)
            }
            .padding(horizontal = 15.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        MyText(
            text = text,
            color = if (isSelected) Color.White else colorResource(R.color.black),
            fontSize = 17,
            fontWeight = FontWeight.Medium
        )
    }
}