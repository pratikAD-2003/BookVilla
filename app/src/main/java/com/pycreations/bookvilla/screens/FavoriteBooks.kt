package com.pycreations.bookvilla.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pycreations.bookvilla.R
import com.pycreations.bookvilla.data.utils.SharedPrefManager
import com.pycreations.bookvilla.navigation.Route
import com.pycreations.bookvilla.screens.components.BookItemLy
import com.pycreations.bookvilla.screens.components.MyText
import com.pycreations.bookvilla.screens.components.shimmer.BookItemShimmer
import com.pycreations.bookvilla.viewmodel.BookViewModel
import com.pycreations.bookvilla.viewmodel.GetMultipleByIdBookState

@Composable
fun FavoriteBooks(navHostController: NavHostController, bookViewModel: BookViewModel) {
    val context = LocalContext.current
//    Log.d("SAVE_BOOKS", list.toString())
    val bookByIdsState by bookViewModel.getMultipleBooksByIdState.collectAsState()
    LaunchedEffect(Unit) {
        bookViewModel.fetchMultipleBooksByIds(SharedPrefManager.getSavedBookList(context))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .clip(shape = CircleShape)
                        .background(color = colorResource(R.color.screen_clr))
                        .clickable {
                            navHostController.navigateUp()
                        }
                        .padding(15.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.arrow),
                        contentDescription = "back",
                        colorFilter = ColorFilter.tint(color = colorResource(R.color.black)),
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(Modifier.width(15.dp))
                MyText(
                    text = "Favorite Books",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier,
                    fontSize = 24
                )
            }
            Spacer(Modifier.height(20.dp))
            if (SharedPrefManager.getSavedBookList(context).isNotEmpty()) {
                when (bookByIdsState) {
                    is GetMultipleByIdBookState.Loading -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(5.dp)
                        ) {
                            items(10) {
                                BookItemShimmer()
                            }
                        }
                    }

                    is GetMultipleByIdBookState.Error -> {
                        val msg = (bookByIdsState as GetMultipleByIdBookState.Error).error
                        bookViewModel.resetMultipleBooks()
                    }

                    is GetMultipleByIdBookState.SuccessMultiple -> {
                        val books =
                            (bookByIdsState as GetMultipleByIdBookState.SuccessMultiple).response
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(5.dp)
                        ) {
                            items(books) {
                                BookItemLy(it) {
                                    navHostController.navigate(Route.BookDetailRoute.route + "/${it}")
                                }
                            }
                        }
                    }

                    else -> {
                        bookViewModel.resetMultipleBooks()
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    MyText(
                        text = "Empty Favorites!",
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier,
                        fontSize = 20
                    )
                }
            }
        }
    }
}