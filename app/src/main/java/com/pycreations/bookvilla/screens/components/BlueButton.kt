package com.pycreations.bookvilla.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.pycreations.bookvilla.R

@Composable
fun BlueButton(text: String,modifier: Modifier = Modifier,onClick:()-> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .background(color = colorResource(R.color.blue)).clickable{
                onClick()
            }, contentAlignment = Alignment.Center
    ) {
        MyText(
            text = text,
            color = Color.White,
            fontSize = 18,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun BBPrev() {
    BlueButton("Website"){

    }
}