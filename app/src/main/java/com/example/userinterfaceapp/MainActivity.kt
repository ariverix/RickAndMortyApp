package com.example.userinterfaceapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.userinterfaceapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val logTag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Инициализация ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(logTag, "onCreate() завершён")

        if (savedInstanceState == null) {
            navigateToOnboard(addToBackStack = false)
        }
    }

    fun navigateToOnboard(addToBackStack: Boolean = true) {
        Log.d(logTag, "Навигация к OnboardFragment")
        replaceFragment(OnboardFragment(), addToBackStack)
    }

    fun navigateToSignIn(args: Bundle? = null, addToBackStack: Boolean = true) {
        Log.d(logTag, "Навигация к SignInFragment")
        val fragment = SignInFragment().apply { arguments = args }
        replaceFragment(fragment, addToBackStack)
    }

    fun navigateToSignUp(addToBackStack: Boolean = true) {
        Log.d(logTag, "Навигация к SignUpFragment")
        replaceFragment(SignUpFragment(), addToBackStack)
    }

    fun navigateToHome(addToBackStack: Boolean = true) {
        Log.d(logTag, "Навигация к HomeFragment")
        replaceFragment(HomeFragment(), addToBackStack)
    }

    private fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            if (addToBackStack) {
                addToBackStack(null)
            }
        }.commit()
    }
}
