package kz.littlebrains.main.main_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kz.littlebrains.main.DetailScreen.DetailScreen
import kz.littlebrains.main.Log
import kz.littlebrains.main.ScannerRouter

class MainScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<MainViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigationEvent = viewModel.navigationEvent.collectAsState().value.getValue()
        when(navigationEvent){
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
            //is NavigationEvent.AuthRouter ->
            is NavigationEvent.NextScreen -> {
                //navigator.push(OtherScreen())
                navigator.push(ScreenRegistry.get(ScannerRouter.ScannerScreen))
            }

            is NavigationEvent.DetailScreen -> {
                navigator.push(DetailScreen(navigationEvent.text))
            }
        }


        Column {
            Button(
                content = {
                    Text(text = "scanner screen")
                },
                onClick = {
                    viewModel.sendEvent(MainEvent.NextScreen)
                }
            )
            val state = viewModel.state.collectAsState().value
            when (state) {
                is MainState.Default -> {
                    Column {
                        Button(
                            content = {
                                Text(text = "network")
                            },
                            onClick = {
                                viewModel.sendEvent(MainEvent.Load)
                            }
                        )

                    }
                }

                MainState.Loading -> {
                    CircularProgressIndicator()
                }

                is MainState.Error -> {
                    Text(text = state.text.toString())
                }

                is MainState.Result -> {
                    LazyColumn {
                        items(state.text!!.content!!) { item ->
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.sendEvent(MainEvent.DetailScreen(item.caseNameRu?:""))
                                    }
                                    .padding(horizontal = 16.dp, vertical = 24.dp),
                                text = item.caseNameRu ?: "")
                            Divider()
                        }
                    }
                }
            }
        }
    }
}