package kz.littlebrains.main.Scanner_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kz.littlebrains.scanner.QrScannerScreen

class ScannerScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<ScannerViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigationEvent = viewModel.navigationEvent.collectAsState().value.getValue()
        when(navigationEvent){
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
            //is NavigationEvent.AuthRouter -> ScreenRegistry.get(AuthRouter.ProfileScreen())
        }


        val state = viewModel.state.collectAsState().value
        when(state){
            is ScannerState.Default -> {
                var scanning by remember { mutableStateOf("") }
                Column {
                    Button(
                        content = {
                            Text(text = "Back")
                        },
                        onClick = {
                            viewModel.sendEvent(ScannerEvent.Back)
                        }
                    )
                    Text(text = scanning)
                    Column {
                        QrScannerScreen(
                            modifier = Modifier.fillMaxSize(),
                            {
                                scanning = it
                            }
                        )
                    }
                }
            }
        }
    }
}