package com.ludovic.vimont.brightnesssample

import android.content.Context
import android.database.ContentObserver
import android.os.Handler
import android.provider.Settings

class ScreenBrightnessManager(val context: Context, handler: Handler) : ContentObserver(handler) {
    companion object {
        val MAXIMUM_BRIGHTNESS = 255
        val NUM_SCREEN_BRIGHTNESS_BINS = 5
        var screenBrightnessTimer = LongArray(NUM_SCREEN_BRIGHTNESS_BINS)
    }

    var timer: Long = System.currentTimeMillis()
    var currentBrightness = getScreenBrightness()

    init {
        context.contentResolver.registerContentObserver(
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS), false, this
        )
    }

    override fun onChange(selfChange: Boolean) {
        if (selfChange) {
            screenBrightnessTimer[currentBrightness / (MAXIMUM_BRIGHTNESS / NUM_SCREEN_BRIGHTNESS_BINS)] += System.currentTimeMillis() - timer
            currentBrightness = getScreenBrightness()
            timer = System.currentTimeMillis()
        }
    }

    private fun getScreenBrightness(): Int {
        return Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0)
    }
}