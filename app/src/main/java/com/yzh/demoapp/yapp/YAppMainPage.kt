package com.yzh.demoapp.yapp

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun YAppMainPage() {
    Box(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = YAppRoute.Weather.routeName,
            modifier = Modifier.fillMaxSize()
        ) {
            yAppSupportList.forEach { route ->
                composable(route.routeName) {
                    route.page()
                }
            }
        }
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        LazyRow(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .safeContentPadding()
                .height(120.dp)
                .background(MaterialTheme.colorScheme.background)
                .shadow(1.dp),
            contentPadding = PaddingValues(12.dp)
        ) {
            items(yAppSupportList) { route ->
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            navController.navigate(route.routeName) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    val isSelected = currentDestination?.hierarchy?.any() {
                        it.route == route.routeName
                    } == true
                    val iconSize by animateDpAsState(
                        targetValue = if (isSelected) 50.dp else 30.dp,
                        label = "iconSize"
                    )
                    Box(
                        modifier = Modifier
                            .size(iconSize)
                            .background(
                                color = route.bgColor,
                                shape = MaterialTheme.shapes.medium
                            )
                    )
                    Text(
                        text = route.name.ifEmpty { route.routeName }
                    )
                }
            }
        }
    }
}