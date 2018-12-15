package com.ludovic.vimont.brightnesssample

import android.content.ContentResolver
import android.database.ContentObserver
import android.os.Handler
import android.provider.Settings

class ScreenBrightnessManager(private val contentResolver: ContentResolver, handler: Handler) : ContentObserver(handler) {
    companion object {
        val MAX_BRIGHTNESS = 255
    }
    private var screenBrightnessListener: ScreenBrightnessListener? = null

    fun registerContentObserver() {
        contentResolver.registerContentObserver(
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS), false, this
        )
    }

    fun unregisterContentObserver() {
        contentResolver.unregisterContentObserver(this)
    }

    override fun onChange(selfChange: Boolean) {
        screenBrightnessListener?.onBrightnessChange(getScreenBrightness())
    }

    fun getScreenBrightness(): Int {
        return Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0)
    }

    fun setScreenBrightnessListener(screenBrightnessListener: ScreenBrightnessListener) {
        this.screenBrightnessListener = screenBrightnessListener
    }
}