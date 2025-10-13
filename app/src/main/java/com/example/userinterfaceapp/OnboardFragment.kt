package com.example.userinterfaceapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.userinterfaceapp.databinding.FragmentOnboardBinding

class OnboardFragment : BaseFragment() {

    private var _binding: FragmentOnboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        logEvent("onCreateView() вызван")
        _binding = FragmentOnboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonReady.setOnClickListener {
            Log.d(logTag, "Кнопка 'Готов' нажата")

            // Create the user object you need to pass
            val userToPass = User("exampleId", "exampleName") // Replace with your actual object and data

            // Pass the object as an argument
            val direction = OnboardFragmentDirections.actionOnboardFragmentToSignInFragment(userToPass)

            findNavController().navigate(direction)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
