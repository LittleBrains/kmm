package kz.littlebrains.scanner

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform