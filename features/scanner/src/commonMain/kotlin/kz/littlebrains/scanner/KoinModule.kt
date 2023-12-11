package kz.littlebrains.scanner

import kz.littlebrains.main.Scanner_screen.ScannerViewModel
import org.koin.dsl.module

val featureScannerModule = module {
    factory { ScannerViewModel() }
}