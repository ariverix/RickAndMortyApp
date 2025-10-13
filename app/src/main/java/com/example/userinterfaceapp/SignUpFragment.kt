package com.example.userinterfaceapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import com.example.userinterfaceapp.databinding.FragmentSignUpBinding

class SignUpFragment : BaseFragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        logEvent("onCreateView() вызван")
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logEvent("onViewCreated() вызван")

        dbHelper = DBHelper(requireContext())

        binding.buttonInput.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.editTextTextEmailAddress.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            val age = binding.etAge.text.toString()
            val genderId = binding.rgGender.checkedRadioButtonId
            val gender = if (genderId != -1) {
                binding.rgGender.findViewById<RadioButton>(genderId)?.text?.toString().orEmpty()
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
                    val resultBundle = Bundle().apply {
                        putString(SignInFragment.ARG_USER_NAME, name)
                        putString(SignInFragment.ARG_USER_EMAIL, email)
                        putSerializable(SignInFragment.ARG_USER_OBJECT, user)
                    }

                    logEvent("Передача данных в SignInFragment")
                    parentFragmentManager.setFragmentResult(
                        SignInFragment.REQUEST_USER_DATA,
                        resultBundle,
                    )
                    activity?.onBackPressedDispatcher?.onBackPressed()
                } catch (e: Exception) {
                    logEvent("Ошибка регистрации: ${e.message}")
                    Toast.makeText(requireContext(), "Пользователь уже существует", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.loginLink.setOnClickListener {
            logEvent("Возврат к входу")
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        binding.arrowBack.setOnClickListener {
            logEvent("Возврат назад")
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
