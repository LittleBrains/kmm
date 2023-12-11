package kz.littlebrains.main.DetailScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.serialization.Serializable

@Serializable
data class DetailScreen(val text: String) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<DetailViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val navigationEvent = viewModel.navigationEvent.collectAsState().value.getValue()
        when(navigationEvent){
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
            //is NavigationEvent.AuthRouter -> ScreenRegistry.get(AuthRouter.ProfileScreen())
        }


        val state = viewModel.state.collectAsState().value
        when(state){
            is DetailState.Default -> {
                Column {
                    Text(text = text)
                    Button(
                        content = {
                            Text(text = "Back")
                        },
                        onClick = {
                            viewModel.sendEvent(DetailEvent.Back)
                        }
                    )


                    var value by remember { mutableStateOf("") }
                    TextField(
                        modifier = Modifier.fillMaxWidth()
                            .padding(16.dp),
                        value = value,
                        onValueChange = {
                            value = it
                        },
                        label = {
                            Text("label")
                        }
                    )
                    var value2 by remember { mutableStateOf("") }
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth()
                            .padding(16.dp),
                        value = value2,
                        onValueChange = {
                            value2 = it
                        },
                        label = {
                            Text("label")
                        }
                    )

                    var switch by remember { mutableStateOf(false) }
                    Switch(switch, onCheckedChange = {
                        switch = it
                    })
                }
            }
        }
    }
}