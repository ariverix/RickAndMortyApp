package com.example.userinterfaceapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import com.google.android.material.textfield.TextInputEditText

class SignUpFragment : BaseFragment() {

    private lateinit var dbHelper: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logEvent("onCreateView() вызван")
        return inflater.inflate(R.layout.activity_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DBHelper(requireContext())

        val nameEdit = view.findViewById<TextInputEditText>(R.id.et_name)
        val emailEdit = view.findViewById<TextInputEditText>(R.id.editTextTextEmailAddress)
        val passEdit = view.findViewById<TextInputEditText>(R.id.editTextTextPassword)
        val ageEdit = view.findViewById<TextInputEditText>(R.id.et_age)
        val genderGroup = view.findViewById<RadioGroup>(R.id.rg_gender)
        val registerButton = view.findViewById<Button>(R.id.button_input)
        val loginLayout = view.findViewById<LinearLayout>(R.id.already_acc_reg)
        val loginText = loginLayout.getChildAt(1) as TextView

        registerButton.setOnClickListener {
            val name = nameEdit.text.toString()
            val email = emailEdit.text.toString()
            val password = passEdit.text.toString()
            val age = ageEdit.text.toString()
            val genderId = genderGroup.checkedRadioButtonId
            val gender = if (genderId != -1) view.findViewById<RadioButton>(genderId).text.toString() else ""

            Log.d(logTag, "Попытка регистрации: name=$name, email=$email")

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || age.isEmpty() || gender.isEmpty()) {
                Log.d(logTag, "Не все поля заполнены")
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    dbHelper.addUser(email, password)
                    Log.d(logTag, "Пользователь добавлен в БД")
                    Toast.makeText(requireContext(), "Аккаунт создан", Toast.LENGTH_SHORT).show()

                    val user = User(name, email, password, age, gender)
                    val bundle = bundleOf(
                        "user_name" to name,
                        "user_email" to email,
                        "user_object" to user
                    )

                    Log.d(logTag, "Передача данных в SignInFragment")

                    val signInFragment = SignInFragment()
                    signInFragment.arguments = bundle

                    parentFragmentManager.popBackStack()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, signInFragment)
                        .addToBackStack(null)
                        .commit()

                } catch (e: Exception) {
                    Log.e(logTag, "Ошибка регистрации: ${e.message}")
                    Toast.makeText(requireContext(), "Пользователь уже существует", Toast.LENGTH_SHORT).show()
                }
            }
        }

        loginText.setOnClickListener {
            Log.d(logTag, "Возврат к входу")
            parentFragmentManager.popBackStack()
        }
    }
}