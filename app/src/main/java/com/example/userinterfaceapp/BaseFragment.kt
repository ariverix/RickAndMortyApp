package com.example.userinterfaceapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected val logTag: String get() = this::class.java.simpleName

    protected fun logEvent(message: String) {
        Log.d(logTag, message)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        logEvent("onAttach() вызван")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logEvent("onCreate() вызван")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logEvent("onViewCreated() вызван")
    }

    override fun onStart() {
        super.onStart()
        logEvent("onStart() вызван")
    }

    override fun onResume() {
        super.onResume()
        logEvent("onResume() вызван")
    }

    override fun onPause() {
        super.onPause()
        logEvent("onPause() вызван")
    }

    override fun onStop() {
        super.onStop()
        logEvent("onStop() вызван")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        logEvent("onDestroyView() вызван")
    }

    override fun onDestroy() {
        super.onDestroy()
        logEvent("onDestroy() вызван")
    }

    override fun onDetach() {
        super.onDetach()
        logEvent("onDetach() вызван")
    }
}
