package com.example.userinterfaceapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MainActivity", "onCreate() вызван")

        // Загружаем первый фрагмент при запуске
        if (savedInstanceState == null) {
            navigateToOnboard()
        }
    }

    fun navigateToOnboard() {
        Log.d("MainActivity", "Навигация к OnboardFragment")
        replaceFragment(OnboardFragment())
    }

    fun navigateToSignIn() {
        Log.d("MainActivity", "Навигация к SignInFragment")
        replaceFragment(SignInFragment())
    }

    fun navigateToSignUp() {
        Log.d("MainActivity", "Навигация к SignUpFragment")
        replaceFragment(SignUpFragment())
    }

    fun navigateToHome() {
        Log.d("MainActivity", "Навигация к HomeFragment")
        replaceFragment(HomeFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}