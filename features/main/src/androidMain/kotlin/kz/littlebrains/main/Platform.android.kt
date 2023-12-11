package kz.littlebrains.main

import android.util.Log
import android.widget.Toast

class AndroidPlatform : Platform {
    override fun d(text: String) {
        Log.d("tag", text)
    }

    override fun toast(text: String) {
        //Toast.makeText()
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()