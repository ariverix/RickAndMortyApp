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
        savedInstanceState: Bundle?
    ): View {
        logEvent("onCreateView() вызван")
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logEvent("onViewCreated() вызван")

        dbHelper = DBHelper(requireContext())

        val emailEdit = binding.editTextTextEmailAddress
        val passEdit = binding.editTextTextPassword
        val registerText = binding.registerLink
        val userInfoText = binding.userInfoText
        val backButton = binding.arrowBackLog   // ✅ исправлено здесь!

        binding.buttonLog.setOnClickListener {
            val email = emailEdit.text.toString()
            val password = passEdit.text.toString()
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

        registerText.setOnClickListener {
            logEvent("Переход на регистрацию")
            val direction = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(direction)
        }

        backButton.setOnClickListener {
            logEvent("Возврат назад")
            findNavController().navigateUp()
        }

        arguments?.let {
            val userName = it.getString("user_name")
            val userEmail = it.getString("user_email")
            val userObject = it.getSerializable("user_object") as? User

            logEvent("Получены данные: name=$userName, email=$userEmail, user=$userObject")

            val displayName = userName ?: userObject?.name
            val displayEmail = userEmail ?: userObject?.email

            if (!displayName.isNullOrEmpty() && !displayEmail.isNullOrEmpty()) {
                userInfoText.text = "Пользователь: $displayName\nEmail: $displayEmail"
                userInfoText.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        logEvent("onDestroyView() вызван")
        _binding = null
    }
}
