package com.pycreations.bookvilla.screens.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.pycreations.bookvilla.R

@Composable
fun TextValue(title: String, value: String, fontSize1: Int,fontSize2: Int, fontWeight1: FontWeight,fontWeight2: FontWeight) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top
    ) {
        MyText(
            text = title,
            color = colorResource(R.color.black),
            fontSize = fontSize1,
            fontWeight = fontWeight1
        )

        MyText(
            text = value,
            color = colorResource(R.color.black),
            fontSize = fontSize2,
            fontWeight = fontWeight2
        )
    }
}