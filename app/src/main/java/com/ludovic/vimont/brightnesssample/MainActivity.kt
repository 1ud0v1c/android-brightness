package com.ludovic.vimont.brightnesssample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.RelativeLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(), ScreenBrightnessListener {
    private lateinit var screenBrightnessManager: ScreenBrightnessManager
    private lateinit var textView: TextView
    private lateinit var relativeLayout: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screenBrightnessManager = ScreenBrightnessManager(contentResolver, Handler(mainLooper))
        screenBrightnessManager.setScreenBrightnessListener(this)
        relativeLayout = findViewById(R.id.relative_layout_background)
        textView = findViewById(R.id.text_view_brightness)
        textView.text = getString(R.string.screen_brightness, brightnessPercentage(screenBrightnessManager.getScreenBrightness()))
    }

    override fun onResume() {
        super.onResume()
        screenBrightnessManager.registerContentObserver()
    }

    private fun brightnessPercentage(brightness: Int): Float {
        return brightness * 100.0f / ScreenBrightnessManager.MAX_BRIGHTNESS
    }

    override fun onBrightnessChange(brightness: Int) {
        textView.text = getString(R.string.screen_brightness, brightnessPercentage(brightness))
    }

    override fun onPause() {
        screenBrightnessManager.unregisterContentObserver()
        super.onPause()
    }
}