package kz.littlebrains.main

class Log {
    companion object {
        private val platform: Platform = getPlatform()

        fun d(text: String) {
            platform.d(text)
        }

        fun toast(text: String) {
            platform.toast(text)
        }
    }
}