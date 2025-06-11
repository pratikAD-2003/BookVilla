package com.pycreations.bookvilla.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pycreations.bookvilla.R

@Composable
fun SearchBar(
    leadingIcon: Boolean,
    hint: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    fontColor: Color,
    hintColor: Color,
    text : String = "",
    clickable : Boolean = true,
    modifier: Modifier = Modifier
) {

    var text by remember { mutableStateOf(text) }
    val focusRequester = FocusRequester()
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 5.dp)
            .clip(shape = CircleShape)
            .shadow(elevation = 1.dp)
            .height(55.dp)
            .background(color = colorResource(R.color.screen_clr)).clickable{
                if (!clickable)
                onClick()            },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(Modifier.width(20.dp))
        if (leadingIcon) {
            Image(
                painter = painterResource(R.drawable.search),
                contentDescription = "search",
                colorFilter = ColorFilter.tint(colorResource(R.color.dark_text)),
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(15.dp))
        }
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onValueChange(text)
            },
            modifier = Modifier
                .background(color = Color.Transparent).focusRequester(focusRequester),
            maxLines = 1,
            enabled = clickable,
            textStyle = TextStyle(color = fontColor, fontSize = 17.sp),
            decorationBox = { innerTextField ->
                if (text.isEmpty()) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = hint,
                            modifier = Modifier
                                .background(color = Color.Transparent),
                            color = hintColor, fontSize = 17.sp
                        )
                    }
                }
                innerTextField()
            },
            cursorBrush = SolidColor(colorResource(R.color.dark_text))
        )
    }
}