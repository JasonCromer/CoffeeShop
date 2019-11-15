package com.example.coffeeshop.dagger

import com.example.coffeeshop.CoffeeApplication
import com.example.coffeeshop.coffeesearch.CoffeeSearchViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(coffeeApplication: CoffeeApplication)
    fun inject(coffeeSearchViewModel: CoffeeSearchViewModel)
}
