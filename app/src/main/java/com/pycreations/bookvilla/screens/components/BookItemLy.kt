package com.pycreations.bookvilla.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pycreations.bookvilla.R
import com.pycreations.bookvilla.data.models.Item


@Composable
fun BookItemLy(item: Item, onClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .clickable {
                onClick(item.id)
            }
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .width(185.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = colorResource(R.color.screen_clr))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = colorResource(R.color.white))
                        .padding(horizontal = 8.dp, vertical = 5.dp)

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.star),
                            contentDescription = "rating",
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = item.volumeInfo.averageRating.toString(),
                            color = colorResource(R.color.black),
                            fontSize = 12.sp
                        )
                    }
                }
            }
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                val secureImageUrl = item.volumeInfo.imageLinks?.thumbnail?.replace("http://", "https://")

                if (!secureImageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(secureImageUrl)
                            .crossfade(true)
                            .allowHardware(false)
                            .build(),
                        contentDescription = "Book Thumbnail",
                        modifier = Modifier
                            .width(128.dp)
                            .height(192.dp)
                            .padding(vertical = 10.dp)
                            .border(
                                width = 4.dp,
                                shape = RoundedCornerShape(10.dp),
                                color = colorResource(R.color.white)
                            )
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.FillBounds
                    )
                } else {
                    // Fallback image (e.g., local drawable placeholder)
                    Image(
                        painter = painterResource(R.drawable.page), // replace with your fallback image
                        contentDescription = "No Image",
                        modifier = Modifier
                            .width(128.dp)
                            .height(192.dp)
                            .padding(vertical = 10.dp)
                            .border(
                                width = 4.dp,
                                shape = RoundedCornerShape(10.dp),
                                color = colorResource(R.color.white)
                            )
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
            Spacer(Modifier.height(20.dp))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.height(10.dp))
            val title = item.volumeInfo.title ?: "Untitled"
            Text(
                text = if (title.length > 17) title.substring(
                    0,
                    17
                ) + "..." else title,
                color = colorResource(R.color.black),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
            )
            val authors = item.volumeInfo.authors
            val authorName = when {
                authors.isNullOrEmpty() -> "Unknown Author"
                authors[0].length > 17 -> authors[0].substring(0, 17) + "..."
                else -> authors[0]
            }
            Text(
                text = authorName,
                color = colorResource(R.color.black),
                fontSize = 14.sp,
                maxLines = 1
            )

            Spacer(Modifier.height(5.dp))
        }
    }
}