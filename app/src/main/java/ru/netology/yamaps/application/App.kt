package ru.netology.yamaps.application

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import ru.netology.yamaps.BuildConfig

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(BuildConfig.MAPS_API_KEY)
    }
}