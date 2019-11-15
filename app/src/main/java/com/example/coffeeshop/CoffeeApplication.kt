package com.example.coffeeshop

import android.app.Application
import com.example.coffeeshop.dagger.AppComponent
import com.example.coffeeshop.dagger.AppModule
import com.example.coffeeshop.dagger.DaggerAppComponent

class CoffeeApplication : Application() {

    internal lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        appComponent.inject(this)
    }
}
