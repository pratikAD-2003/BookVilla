package com.pycreations.bookvilla.screens.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pycreations.bookvilla.R


@Composable
fun CustomIconSwitch(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val transition = updateTransition(targetState = isChecked, label = "SwitchTransition")

    val alignment by transition.animateDp(
        label = "IconAlignment"
    ) { state ->
        if (state) 15.dp else 0.dp
    }

    val backgroundColor by transition.animateColor(
        label = "SwitchColor"
    ) { state ->
        if (state) Color(0xFF4CAF50) else Color(0xFFF44336)
    }

    Box(
        modifier = Modifier
            .width(40.dp)
            .height(25.dp)
            .clip(CircleShape)
            .background(if (isChecked) Color.White else Color.Black)
            .clickable { onCheckedChange(!isChecked) }
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
        }

        Box(
            modifier = Modifier
                .size(18.dp)
                .offset(x = alignment)
                .clip(CircleShape)
                .background(if (isChecked) Color.White else Color.Black),
            contentAlignment = Alignment.Center
        ) {
            if (isChecked) {
                Image(
                    painter = painterResource(R.drawable.rigth),
                    contentDescription = "on",
//                    colorFilter = ColorFilter.tint(color = Color.Black),
                    modifier = Modifier.size(18.dp)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.wrong),
                    contentDescription = "on",
//                    colorFilter = ColorFilter.tint(color = Color.White),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
