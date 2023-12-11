import kz.littlebrains.core.httpClientModule
import kz.littlebrains.core.repositoryModule

fun appModule() = listOf(
    httpClientModule,
    repositoryModule,
)