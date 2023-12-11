import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.registry.screenModule
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import kz.littlebrains.core.httpClientModule
import kz.littlebrains.core.repositoryModule
import kz.littlebrains.main.Scanner_screen.ScannerScreen
import kz.littlebrains.main.featuterMainModule
import kz.littlebrains.main.main_screen.MainScreen
import kz.littlebrains.main.ScannerRouter
import kz.littlebrains.scanner.featureScannerModule
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.KoinApplication

val featureOnboaring = screenModule {
    register<ScannerRouter.ScannerScreen> {
        ScannerScreen()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    ScreenRegistry {
        featureOnboaring()
    }

    MaterialTheme {
        KoinApplication(application = {
            modules(
                httpClientModule,
                repositoryModule,
                featuterMainModule,
                featureScannerModule,
            )
        }) {
            val startScreen = MainScreen()
            Navigator(
                screen = startScreen,
                content = { navigator ->
                    CurrentScreen()
                }
            )
        }
    }
}