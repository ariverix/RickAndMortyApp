package com.example.userinterfaceapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.userinterfaceapp.databinding.FragmentOnboardBinding

class OnboardFragment : BaseFragment() {

    // B: Настраиваем View Binding во фрагменте онбординга
    private var _binding: FragmentOnboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        logEvent("onCreateView() вызван")
        _binding = FragmentOnboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logEvent("onViewCreated() вызван")

        binding.buttonReady.setOnClickListener {
            logEvent("Кнопка 'Готов' нажата")
            // A: Навигация к экрану входа через NavController
            findNavController().navigate(OnboardFragmentDirections.actionOnboardFragmentToSignInFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
