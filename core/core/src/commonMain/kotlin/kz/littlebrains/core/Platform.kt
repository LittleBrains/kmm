package kz.littlebrains.core

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform