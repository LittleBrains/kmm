package kz.littlebrains.main

interface Platform {
    fun d(text: String)
    fun toast(text: String)
}

expect fun getPlatform(): Platform