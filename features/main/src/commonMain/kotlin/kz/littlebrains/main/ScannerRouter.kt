package kz.littlebrains.main

import cafe.adriel.voyager.core.registry.ScreenProvider


sealed class ScannerRouter : ScreenProvider {
    object ScannerScreen : ScannerRouter()
}