package kz.littlebrains.main

import kz.littlebrains.main.DetailScreen.DetailViewModel
import kz.littlebrains.main.main_screen.MainViewModel
import org.koin.dsl.module

val featuterMainModule = module {
    factory { MainViewModel(get()) }
    factory { DetailViewModel() }
}