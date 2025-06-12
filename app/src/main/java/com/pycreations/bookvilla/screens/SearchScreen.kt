package com.pycreations.bookvilla.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pycreations.bookvilla.R
import com.pycreations.bookvilla.data.models.Item
import com.pycreations.bookvilla.navigation.Route
import com.pycreations.bookvilla.screens.components.BookItemLy
import com.pycreations.bookvilla.screens.components.MyText
import com.pycreations.bookvilla.screens.components.SearchBar
import com.pycreations.bookvilla.screens.components.shimmer.BookItemShimmer
import com.pycreations.bookvilla.viewmodel.BookViewModel
import com.pycreations.bookvilla.viewmodel.GetGenreState

@Composable
fun SearchScreen(
    navHostController: NavHostController,
    bookViewModel: BookViewModel,
    query: String
) {
    var text by remember { mutableStateOf(query.trim()) }

    val items = remember { mutableStateListOf<Item>() } // Replace with your model
    var startIndex by remember { mutableIntStateOf(0) }
    val maxResults = 10
    val searchState by bookViewModel.getSearchLimitState.collectAsState()
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(text) {
        if (text.length > 4) {
            startIndex = 0
            items.clear()
            isLoading = true
            bookViewModel.getSearchLimit(text, startIndex, maxResults)
        } else {
            bookViewModel.resetSearchLimitState()
            items.clear()
        }
    }

    LaunchedEffect(searchState) {
        when (searchState) {
            is GetGenreState.Success -> {
                val newItems = (searchState as GetGenreState.Success).response.items
                Log.d("SearchDebug", "New items received: ${newItems?.size}, Start: $startIndex")
                if (!newItems.isNullOrEmpty()) {
                    items.addAll(newItems)
                    startIndex += maxResults
                }
                isLoading = false
            }

            is GetGenreState.Error -> {
                isLoading = false
            }

            is GetGenreState.Loading -> {
                isLoading = true
            }

            else -> {}
        }
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
            Spacer(Modifier.height(10.dp))
            SearchBar(
                leadingIcon = true,
                hint = "Search Books",
                text = text,
                fontColor = colorResource(R.color.black),
                hintColor = colorResource(R.color.dark_text),
                onValueChange = {
                    text = it
                },
                clickable = true,
                onClick = {

                }
            )
            Spacer(Modifier.height(10.dp))
            if (isLoading) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(5.dp)
                ) {
                    items(10) {
                        BookItemShimmer()
                    }
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(5.dp)
            ) {
                items(items) {
                    BookItemLy(it) {
                        navHostController.navigate(Route.BookDetailRoute.route + "/${it}")
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MyText(
                            text = "Tap to load more..",
                            fontWeight = FontWeight.Normal,
                            fontSize = 16,
                            modifier = Modifier.clickable {
                                if (!isLoading) {
                                    isLoading = true
                                    bookViewModel.getSearchLimit(text, startIndex, maxResults)
                                }
                            })
                    }
                }
            }
        }
    }
}