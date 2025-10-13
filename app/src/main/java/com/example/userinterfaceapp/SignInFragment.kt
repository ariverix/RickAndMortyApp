package com.example.userinterfaceapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.userinterfaceapp.databinding.FragmentSignInBinding

class SignInFragment : BaseFragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: DBHelper
    private val args: SignInFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        logEvent("onCreateView() вызван")
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logEvent("onViewCreated() вызван")

        dbHelper = DBHelper(requireContext())

        binding.buttonLog.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            logEvent("Попытка входа: email=$email")

            if (dbHelper.checkUser(email, password)) {
                logEvent("Вход успешен")
                Toast.makeText(requireContext(), "Вход выполнен", Toast.LENGTH_SHORT).show()
                val direction = SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                findNavController().navigate(direction)
            } else {
                logEvent("Вход неуспешен")
                Toast.makeText(requireContext(), "Неверный email или пароль", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerLink.setOnClickListener {
            logEvent("Переход на регистрацию")
            val direction = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(direction)
        }

        binding.arrowBackLog.setOnClickListener {
            logEvent("Возврат назад")
            findNavController().navigateUp()
        }

        val userName = args.userName
        val userEmail = args.userEmail
        val userObject = args.userObject

        logEvent("Получены данные: name=$userName, email=$userEmail, user=$userObject")

        val displayName = userName ?: userObject?.name
        val displayEmail = userEmail ?: userObject?.email

        if (!displayName.isNullOrEmpty() && !displayEmail.isNullOrEmpty()) {
            binding.userInfoText.text = "Пользователь: $displayName\nEmail: $displayEmail"
            binding.userInfoText.visibility = View.VISIBLE
        } else {
            binding.userInfoText.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
