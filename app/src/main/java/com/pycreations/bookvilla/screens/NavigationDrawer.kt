package com.pycreations.bookvilla.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pycreations.bookvilla.R
import com.pycreations.bookvilla.data.utils.Constants
import com.pycreations.bookvilla.data.utils.SharedPrefManager
import com.pycreations.bookvilla.navigation.Route
import com.pycreations.bookvilla.screens.components.CustomIconSwitch
import com.pycreations.bookvilla.screens.components.ExpandableCard
import com.pycreations.bookvilla.screens.components.MyText
import com.pycreations.bookvilla.viewmodel.BookViewModel
import kotlinx.coroutines.launch
import java.net.URLEncoder
import kotlin.collections.component1
import kotlin.collections.component2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerScr(bookViewModel: BookViewModel, navHostController: NavHostController) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var check by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(240.dp),
                drawerContainerColor = colorResource(
                    R.color.screen_clr
                ),
                drawerContentColor = Color.White
            ) {
                MyText(
                    text = "Genres and Subgenres",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 15.dp, top = 15.dp),
                    fontSize = 18
                )
                Spacer(Modifier.height(10.dp))
                Constants.myList.forEach { (genre, subGenres) ->
                    ExpandableCard(
                        genre = genre,
                        subGenre = subGenres,
                        fontSize = 16,
                        onGenreClick = {
                            // handle genre click
                        },
                        onClick = { selectedSubGenre ->
                            // handle sub-genre click
                            val encodedQuery = URLEncoder.encode(selectedSubGenre, "UTF-8")
                            navHostController.navigate(Route.SearchBookRoute.route + "/${"subject:$encodedQuery"}")
//                        bookViewModel.getGenre("subject:$encodedQuery")
                        }
                    )
                }
                Spacer(Modifier.height(10.dp))
                MyText(
                    text = "Settings",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 15.dp, top = 15.dp),
                    fontSize = 18
                )
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navHostController.navigate(Route.FavoriteBooksRoute.route)
                        }
                        .padding(end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MyText(
                        text = "Favorite Books",
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 15.dp),
                        fontSize = 16
                    )
                    Image(
                        painter = painterResource(R.drawable.arrow_right),
                        contentDescription = "more",
                        colorFilter = ColorFilter.tint(colorResource(R.color.dark_text)),
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MyText(
                        text = "Dark Mode",
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 15.dp),
                        fontSize = 16
                    )
                    CustomIconSwitch(
                        isChecked = check,
                        onCheckedChange = { checked ->
                            check = checked
                        }
                    )
                }
            }
        }
    ) {
        HomeScreen(bookViewModel = bookViewModel, navHostController = navHostController, onOpen = {
            scope.launch { drawerState.open() }
        })
    }
}
