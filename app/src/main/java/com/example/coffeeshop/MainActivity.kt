package com.example.coffeeshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showCoffeeListFragment()
    }

    private fun showCoffeeListFragment() = with(supportFragmentManager.beginTransaction()) {
        val fragment = CoffeeListFragment.newInstance()
        addToBackStack(CoffeeListFragment.TAG)
        replace(R.id.contentView, fragment, CoffeeListFragment.TAG)
        commitAllowingStateLoss()
    }
}
