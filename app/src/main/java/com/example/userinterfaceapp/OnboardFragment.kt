package com.example.userinterfaceapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class OnboardFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logEvent("onCreateView() вызван")
        return inflater.inflate(R.layout.activity_onboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonReady = view.findViewById<Button>(R.id.button_ready)
        buttonReady.setOnClickListener {
            Log.d(logTag, "Кнопка 'Готов' нажата")
            (activity as? MainActivity)?.navigateToSignIn()
        }
    }
}