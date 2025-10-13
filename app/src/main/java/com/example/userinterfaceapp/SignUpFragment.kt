package com.example.userinterfaceapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.os.bundleOf
import com.google.android.material.textfield.TextInputEditText

class SignUpFragment : BaseFragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        logEvent("onCreateView() вызван")
        return inflater.inflate(R.layout.activity_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logEvent("onViewCreated() вызван")

        dbHelper = DBHelper(requireContext())

        val loginLink = binding.loginLink

        binding.buttonInput.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.editTextTextEmailAddress.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            val age = binding.etAge.text.toString()
            val genderId = binding.rgGender.checkedRadioButtonId
            val gender = if (genderId != -1) {
                binding.rgGender.findViewById<RadioButton>(genderId).text.toString()
            } else {
                ""
            }

            logEvent("Попытка регистрации: name=$name, email=$email")

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || age.isEmpty() || gender.isEmpty()) {
                logEvent("Не все поля заполнены")
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    dbHelper.addUser(email, password)
                    logEvent("Пользователь добавлен в БД")
                    Toast.makeText(requireContext(), "Аккаунт создан", Toast.LENGTH_SHORT).show()

                    val user = User(name, email, password, age, gender)
                    val direction = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment(
                        userName = name,
                        userEmail = email,
                        userObject = user
                    )

                    logEvent("Передача данных в SignInFragment")
                    (activity as? MainActivity)?.navigateToSignIn(bundle, addToBackStack = false)
                } catch (e: Exception) {
                    logEvent("Ошибка регистрации: ${e.message}")
                    Toast.makeText(requireContext(), "Пользователь уже существует", Toast.LENGTH_SHORT).show()
                }
            }
        }

        loginText.setOnClickListener {
            logEvent("Возврат к входу")
            parentFragmentManager.popBackStack()
        }

        binding.arrowBack.setOnClickListener {
            Log.d(logTag, "Возврат назад")
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
