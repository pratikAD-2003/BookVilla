package com.pycreations.bookvilla.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pycreations.bookvilla.R
import com.pycreations.bookvilla.data.models.Item
import com.pycreations.bookvilla.data.repository.BookRepository
import com.pycreations.bookvilla.screens.components.BlueButton
import com.pycreations.bookvilla.screens.components.BookItemLy
import com.pycreations.bookvilla.screens.components.MyText
import com.pycreations.bookvilla.screens.components.TextValue
import com.pycreations.bookvilla.viewmodel.BookViewModel
import com.pycreations.bookvilla.viewmodel.GetSpecificBookState
import androidx.core.net.toUri
import com.pycreations.bookvilla.data.utils.Constants
import com.pycreations.bookvilla.data.utils.SharedPrefManager

@Composable
fun BookDetailScreen(
    bookViewModel: BookViewModel,
    id: String,
    navHostController: NavHostController
) {
    val bookByIdState by bookViewModel.getSpecificBookState.collectAsState()
    var data by remember { mutableStateOf<Item?>(null) }
    val context = LocalContext.current
    var checkSave by remember {
        mutableStateOf(
            SharedPrefManager.isBookSaved(
                context,
                id
            )
        )
    }

    LaunchedEffect(Unit) {
        bookViewModel.getBookById(id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(470.dp)
                .background(Color.Transparent)
        ) {
            val secureImageUrl =
                data?.volumeInfo?.imageLinks?.thumbnail?.replace("http://", "https://")
            if (!secureImageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(secureImageUrl)
                        .allowHardware(false)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.height(450.dp)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.page), // replace with your fallback image
                    contentDescription = "No Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.height(450.dp)
                )
            }
            Box(
                modifier = Modifier
                    .height(450.dp)
                    .background(Color.Black.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier
                        .height(450.dp)
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .background(Color.White.copy(alpha = 0.2f))
                                .clickable {
                                    navHostController.navigateUp()
                                }
                                .padding(15.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.arrow),
                                contentDescription = "back",
                                colorFilter = ColorFilter.tint(Color.White),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .background(Color.White.copy(alpha = 0.2f))
                                .clickable {

                                    val shareTemplate =
                                        "BookVilla\n\nTittle ~ ${data?.volumeInfo?.title}\nAuthor ~ ${data?.volumeInfo?.authors[0]}\n\nPurchase Link ~ ${data?.volumeInfo?.infoLink}\n Website ~ ${data?.volumeInfo?.previewLink}"
                                    Constants.shareBook(context, shareTemplate)
                                }
                                .padding(15.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.share),
                                contentDescription = "share",
                                colorFilter = ColorFilter.tint(Color.White),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        val secureImageUrl =
                            data?.volumeInfo?.imageLinks?.thumbnail?.replace("http://", "https://")
                        if (!secureImageUrl.isNullOrEmpty()) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(secureImageUrl)
                                    .allowHardware(false)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(220.dp)
                                    .height(320.dp)
                                    .padding(vertical = 10.dp)
                                    .border(
                                        width = 4.dp,
                                        shape = RoundedCornerShape(20.dp),
                                        color = colorResource(R.color.white)
                                    )
                                    .clip(shape = RoundedCornerShape(20.dp)),
                                contentScale = ContentScale.FillBounds
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.page), // replace with your fallback image
                                contentDescription = "No Image",
                                modifier = Modifier
                                    .width(220.dp)
                                    .height(320.dp)
                                    .padding(vertical = 10.dp)
                                    .border(
                                        width = 4.dp,
                                        shape = RoundedCornerShape(20.dp),
                                        color = colorResource(R.color.white)
                                    )
                                    .clip(shape = RoundedCornerShape(20.dp)),
                                contentScale = ContentScale.FillBounds
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = colorResource(R.color.white))
                            .padding(horizontal = 12.dp, vertical = 5.dp)

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
                                text = data?.volumeInfo?.averageRating.toString(),
                                color = colorResource(R.color.black),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .height(470.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            if (checkSave) {
                                SharedPrefManager.removeBookFromList(context, id)
                            } else {
                                SharedPrefManager.addBookToList(context, id)
                            }
                            checkSave = !checkSave
                        }, modifier = Modifier
                            .clip(shape = CircleShape)
                            .background(Color.Red)
                    ) {
                        Image(
                            painter = if (checkSave) painterResource(R.drawable.like) else painterResource(
                                R.drawable.heart
                            ),
                            colorFilter = ColorFilter.tint(Color.White),
                            contentDescription = "save",
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 10.dp)
            ) {
                MyText(
                    text = data?.volumeInfo?.title.toString(),
                    color = colorResource(R.color.black),
                    fontSize = 22,
                    fontWeight = FontWeight.Bold
                )
                if (data?.volumeInfo?.subtitle.toString() != "null") {
                    MyText(
                        text = data?.volumeInfo?.subtitle.toString(),
                        color = colorResource(R.color.black),
                        fontSize = 15,
                        fontWeight = FontWeight.Normal
                    )
                }
                Spacer(Modifier.height(5.dp))
                if (data?.volumeInfo?.authors != null) {
                    TextValue(
                        "Author : ",
                        value = data?.volumeInfo?.authors[0].toString(),
                        fontSize1 = 15,
                        fontWeight1 = FontWeight.Normal,
                        fontSize2 = 16,
                        fontWeight2 = FontWeight.Bold
                    )
                }
                if (data?.volumeInfo?.publisher != "null") {
                    TextValue(
                        "Publisher : ",
                        value = data?.volumeInfo?.publisher.toString(),
                        fontSize1 = 15,
                        fontWeight1 = FontWeight.Normal,
                        fontSize2 = 16,
                        fontWeight2 = FontWeight.Bold
                    )
                }
                if (data?.volumeInfo?.publishedDate != "null") {
                    TextValue(
                        "Publish On : ",
                        value = data?.volumeInfo?.publishedDate.toString(),
                        fontSize1 = 15,
                        fontWeight1 = FontWeight.Normal,
                        fontSize2 = 16,
                        fontWeight2 = FontWeight.Bold
                    )
                }
                if (data?.volumeInfo?.language != "null") {
                    TextValue(
                        "Language : ",
                        value = data?.volumeInfo?.language.toString(),
                        fontSize1 = 15,
                        fontWeight1 = FontWeight.Normal,
                        fontSize2 = 16,
                        fontWeight2 = FontWeight.Bold
                    )
                }
                Spacer(Modifier.height(20.dp))
                if (data?.volumeInfo?.description != "null") {
                    MyText(
                        text = "Description : ",
                        color = colorResource(R.color.black),
                        fontSize = 15,
                        fontWeight = FontWeight.Bold
                    )
                    MyText(
                        text = data?.volumeInfo?.description.toString(),
                        color = colorResource(R.color.black),
                        fontSize = 15,
                        fontWeight = FontWeight.Normal
                    )
                }
                Spacer(modifier = Modifier.height(80.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.Transparent)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp)
                ) {
                    BlueButton(
                        text = "Purchase", modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)
                    ) {
//                        Log.d("CHECK_BTN","click")
                        val intent = Intent(Intent.ACTION_VIEW, data?.volumeInfo?.infoLink?.toUri())
                        context.startActivity(intent)
                    }
                    BlueButton(
                        text = " Website ", modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)
                    ) {
//                        Log.d("CHECK_BTN","click")
                        val intent =
                            Intent(Intent.ACTION_VIEW, data?.volumeInfo?.previewLink?.toUri())
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
    when (bookByIdState) {
        is GetSpecificBookState.Loading ->
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
//                .height(240.dp),
                contentAlignment = Alignment.Center
            ) {
//            CircularProgressIndicator()
            }

        is GetSpecificBookState.Error -> {
            val response = (bookByIdState as GetSpecificBookState.Error).error
            Log.d("by_id_DATA", response.toString())
            bookViewModel.resetBookByIdGenre()
        }

        is GetSpecificBookState.Success -> {
            val response = (bookByIdState as GetSpecificBookState.Success).response
            Log.d("by_id_DATA", response.toString())
            data = response
        }

        else -> {
            bookViewModel.resetBookByIdGenre()
        }
    }
}