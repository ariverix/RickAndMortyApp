package com.example.userinterfaceapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class OnboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("OnboardFragment", "onCreateView вызван")
        return inflater.inflate(R.layout.activity_onboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("OnboardFragment", "onViewCreated вызван")

        val buttonReady = view.findViewById<Button>(R.id.button_ready)
        buttonReady.setOnClickListener {
            Log.d("OnboardFragment", "Кнопка 'Готов' нажата")
            (activity as? MainActivity)?.navigateToSignIn()
        }
    }
}