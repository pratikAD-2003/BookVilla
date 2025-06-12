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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pycreations.bookvilla.R
import com.pycreations.bookvilla.data.models.Item
import com.pycreations.bookvilla.data.utils.Constants
import com.pycreations.bookvilla.data.utils.SharedPrefManager
import com.pycreations.bookvilla.navigation.Route
import com.pycreations.bookvilla.screens.components.BookItemLy
import com.pycreations.bookvilla.screens.components.ExpandableCard
import com.pycreations.bookvilla.screens.components.GenreItemLy
import com.pycreations.bookvilla.screens.components.MyText
import com.pycreations.bookvilla.screens.components.SearchBar
import com.pycreations.bookvilla.screens.components.shimmer.BookItemShimmer
import com.pycreations.bookvilla.viewmodel.BookViewModel
import com.pycreations.bookvilla.viewmodel.GetGenreState
import java.net.URLEncoder

@Composable
fun HomeScreen(
    onOpen: () -> Unit,
    navHostController: NavHostController,
    bookViewModel: BookViewModel
) {
    val context = LocalContext.current
    var text by remember { mutableStateOf("") }
    var selectedGenre by remember { mutableStateOf<String?>(SharedPrefManager.getGenre(context)) }
    selectedGenre = SharedPrefManager.updateGenre(context, selectedGenre!!)

    val items1 = remember { mutableStateListOf<Item>() } // Replace with your model
    val items2 = remember { mutableStateListOf<Item>() } // Replace with your model
    var startIndex1 by remember { mutableIntStateOf(0) }
    var startIndex2 by remember { mutableIntStateOf(0) }
    val maxResults = 10
    var isLoading1 by remember { mutableStateOf(false) }
    var isLoading2 by remember { mutableStateOf(false) }


    val genreState by bookViewModel.getGenreState.collectAsState()
    val genreTrending by bookViewModel.getTrendingState.collectAsState()

    LaunchedEffect(Unit) {
        bookViewModel.getGenre("subject:${selectedGenre}", startIndex1, maxResults)
        bookViewModel.getTrending("subject:fantasy", startIndex2, maxResults)
    }

    LaunchedEffect(genreState) {
        when (genreState) {
            is GetGenreState.Success -> {
                val newItems = (genreState as GetGenreState.Success).response.items
                Log.d("SearchDebug-1", "New items received: ${newItems?.size}, Start: $startIndex1")
                if (!newItems.isNullOrEmpty()) {
                    items1.addAll(newItems)
                    startIndex1 += maxResults
                }
                isLoading1 = false
            }

            is GetGenreState.Error -> {
                isLoading1 = false
            }

            is GetGenreState.Loading -> {
                isLoading1 = true
            }

            else -> {}
        }
    }

    LaunchedEffect(genreTrending) {
        when (genreTrending) {
            is GetGenreState.Success -> {
                val newItems = (genreTrending as GetGenreState.Success).response.items
                Log.d("SearchDebug-2", "New items received: ${newItems?.size}, Start: $startIndex2")
                if (!newItems.isNullOrEmpty()) {
                    items2.addAll(newItems)
                    startIndex2 += maxResults
                }
                isLoading2 = false
            }

            is GetGenreState.Error -> {
                isLoading2 = false
            }

            is GetGenreState.Loading -> {
                isLoading2 = true
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
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    onOpen()
                }) {
                    Image(
                        painter = painterResource(R.drawable.list),
                        colorFilter = ColorFilter.tint(colorResource(R.color.black)),
                        contentDescription = "drawer",
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(Modifier.width(10.dp))
                MyText(
                    text = "BookVilla",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier,
                    fontSize = 24
                )
            }
            Spacer(Modifier.height(5.dp))
            SearchBar(
                leadingIcon = true,
                hint = "Search Books",
                fontColor = colorResource(R.color.black),
                hintColor = colorResource(R.color.dark_text),
                onValueChange = {
                    text = it
                },
                clickable = false,
                onClick = {
                    navHostController.navigate(Route.SearchBookRoute.route + "/${" "}")
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MyText(
                    text = "Popular Genre",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 15.dp, top = 15.dp),
                    fontSize = 19
                )
                MyText(
                    text = "See All",
                    fontWeight = FontWeight.Medium,
                    fontSize = 12,
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .clickable {
                            onOpen()
                        }
                )
            }
            Spacer(Modifier.height(10.dp))
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(Constants.popularGenres1) {
                    GenreItemLy(it, isSelected = it == selectedGenre, onClick = {
                        selectedGenre = it
                        startIndex1 = 0
                        items1.clear()
                        bookViewModel.resetGetGenre()
                        bookViewModel.getGenre("subject:${selectedGenre}", startIndex1, maxResults)
                    }, modifier = Modifier.padding(5.dp))
                }
            }
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(Constants.popularGenres2) {
                    GenreItemLy(it, isSelected = it == selectedGenre, onClick = {
                        selectedGenre = it
                        startIndex1 = 0
                        items1.clear()
                        bookViewModel.resetGetGenre()
                        bookViewModel.getGenre("subject:${selectedGenre}", startIndex1, maxResults)
                    }, modifier = Modifier.padding(5.dp))
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MyText(
                    text = selectedGenre!!,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 15.dp, top = 15.dp),
                    fontSize = 19
                )
                MyText(
                    text = "See All",
                    fontWeight = FontWeight.Medium,
                    fontSize = 12,
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .clickable {
                            val encodedQuery =
                                URLEncoder.encode(selectedGenre, "UTF-8")
                            navHostController.navigate(Route.SearchBookRoute.route + "/${"subject:$encodedQuery"}")
                        }
                )
            }

            Spacer(Modifier.height(10.dp))
            if (isLoading1) {
                LazyRow(
                    contentPadding = PaddingValues(5.dp)
                ) {
                    items(3) {
                        BookItemShimmer()
                    }
                }
            } else {
                LazyRow(
                    contentPadding = PaddingValues(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(items1) {
                        BookItemLy(it) {
                            navHostController.navigate(Route.BookDetailRoute.route + "/${it}")
                        }
                    }
                    item {
                        Box(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            MyText(
                                text = "Tap to more",
                                fontWeight = FontWeight.Medium,
                                fontSize = 14,
                                modifier = Modifier
                                    .padding(end = 15.dp)
                                    .clickable {
                                        if (!isLoading1) {
                                            isLoading1 = true
                                            bookViewModel.getGenre(
                                                "subject:${selectedGenre}",
                                                startIndex1,
                                                maxResults
                                            )
                                        }
                                    }
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MyText(
                    text = "Newest",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 15.dp, top = 15.dp),
                    fontSize = 19
                )
                MyText(
                    text = "See All",
                    fontWeight = FontWeight.Medium,
                    fontSize = 12,
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .clickable {
                            val encodedQuery = URLEncoder.encode("fantasy", "UTF-8")
                            navHostController.navigate(Route.SearchBookRoute.route + "/${"subject:$encodedQuery"}")
                        }
                )
            }

            Spacer(Modifier.height(10.dp))
            if (isLoading2) {
                LazyRow(
                    contentPadding = PaddingValues(5.dp)
                ) {
                    items(3) {
                        BookItemShimmer()
                    }
                }
            } else {
                LazyRow(
                    contentPadding = PaddingValues(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(items2) {
                        BookItemLy(it) {
                            navHostController.navigate(Route.BookDetailRoute.route + "/${it}")
                        }
                    }
                    item {
                        Box(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            MyText(
                                text = "Tap to more",
                                fontWeight = FontWeight.Medium,
                                fontSize = 14,
                                modifier = Modifier
                                    .padding(end = 15.dp)
                                    .clickable {
                                        if (!isLoading2) {
                                            isLoading2 = true
                                            bookViewModel.getTrending(
                                                "subject:fantasy",
                                                startIndex2,
                                                maxResults
                                            )
                                        }
                                    }
                            )
                        }
                    }
                }
            }

            MyText(
                text = "Genres and Subgenres",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 15.dp, top = 15.dp),
                fontSize = 19
            )
            Spacer(Modifier.height(10.dp))
            Constants.myList.forEach { (genre, subGenres) ->
                ExpandableCard(
                    genre = genre,
                    subGenre = subGenres,
                    onGenreClick = {
                        // handle genre click
                    },
                    onClick = { selectedSubGenre ->
                        // handle sub-genre click
                        val encodedQuery = URLEncoder.encode(selectedSubGenre, "UTF-8")
                        navHostController.navigate(Route.SearchBookRoute.route + "/${"subject:$encodedQuery"}")
                    }
                )

            }
        }
    }


}
