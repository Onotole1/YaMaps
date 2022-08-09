package ru.netology.yamaps.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yandex.mapkit.MapKitFactory
import ru.netology.yamaps.R

class MainActivity : AppCompatActivity() {

    companion object {
        // TODO
        private const val MAPKIT_API_KEY = "PLACE_API_KEY_HERE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
        setContentView(R.layout.activity_main)
    }
}