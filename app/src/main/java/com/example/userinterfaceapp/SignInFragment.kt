package com.example.userinterfaceapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText

class SignInFragment : Fragment() {

    private lateinit var dbHelper: DBHelper
    private lateinit var userInfoText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("SignInFragment", "onCreateView вызван")
        return inflater.inflate(R.layout.activity_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("SignInFragment", "onViewCreated вызван")

        dbHelper = DBHelper(requireContext())

        val emailEdit = view.findViewById<TextInputEditText>(R.id.editTextTextEmailAddress)
        val passEdit = view.findViewById<TextInputEditText>(R.id.editTextTextPassword)
        val logButton = view.findViewById<Button>(R.id.button_log)
        val backButton = view.findViewById<ImageButton>(R.id.arrow_back_log)
        userInfoText = view.findViewById(R.id.user_info_text)

        val registerLayout = view.findViewById<LinearLayout>(R.id.dont_have_acc_text)
        val registerText = registerLayout.getChildAt(1) as TextView

        logButton.setOnClickListener {
            val email = emailEdit.text.toString()
            val password = passEdit.text.toString()
            Log.d("SignInFragment", "Попытка входа: email=$email")

            if (dbHelper.checkUser(email, password)) {
                Log.d("SignInFragment", "Вход успешен")
                Toast.makeText(requireContext(), "Вход выполнен", Toast.LENGTH_SHORT).show()
                (activity as? MainActivity)?.navigateToHome()
            } else {
                Log.d("SignInFragment", "Вход неуспешен")
                Toast.makeText(requireContext(), "Неверный email или пароль", Toast.LENGTH_SHORT).show()
            }
        }

        registerText.setOnClickListener {
            Log.d("SignInFragment", "Переход на регистрацию")
            (activity as? MainActivity)?.navigateToSignUp()
        }

        backButton.setOnClickListener {
            Log.d("SignInFragment", "Возврат назад")
            parentFragmentManager.popBackStack()
        }

        arguments?.let {
            val userName = it.getString("user_name")
            val userEmail = it.getString("user_email")
            Log.d("SignInFragment", "Получены данные: name=$userName, email=$userEmail")
            if (userName != null && userEmail != null) {
                userInfoText.text = "Пользователь: $userName\nEmail: $userEmail"
                userInfoText.visibility = View.VISIBLE
            }
        }
    }
}