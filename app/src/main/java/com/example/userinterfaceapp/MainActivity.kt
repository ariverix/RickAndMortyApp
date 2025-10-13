package com.example.userinterfaceapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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
        val fragment = SignInFragment.newInstance(args)
        replaceFragment(fragment, addToBackStack)
    }

    fun navigateToSignUp(addToBackStack: Boolean = true) {
        Log.d(logTag, "Навигация к SignUpFragment")
        replaceFragment(SignUpFragment(), addToBackStack)
    }

    fun navigateToHome(
        addToBackStack: Boolean = false,
        clearBackStack: Boolean = !addToBackStack,
    ) {
        Log.d(logTag, "Навигация к HomeFragment")
        replaceFragment(HomeFragment(), addToBackStack, clearBackStack = clearBackStack)
    }

    private fun replaceFragment(
        fragment: Fragment,
        addToBackStack: Boolean,
        clearBackStack: Boolean = false,
    ) {
        if (clearBackStack) {
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            if (addToBackStack) {
                addToBackStack(null)
            }
        }.commit()
    }
}
