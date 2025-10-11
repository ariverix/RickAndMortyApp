package com.example.userinterfaceapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

        dbHelper = DBHelper(requireContext())

        val emailEdit = binding.editTextTextEmailAddress
        val passEdit = binding.editTextTextPassword
        val registerText = binding.dontHaveAccText.getChildAt(1) as TextView
        val userInfoText = binding.userInfoText

        binding.buttonLog.setOnClickListener {
            val email = emailEdit.text.toString()
            val password = passEdit.text.toString()
            Log.d(logTag, "Попытка входа: email=$email")

            if (dbHelper.checkUser(email, password)) {
                Log.d(logTag, "Вход успешен")
                Toast.makeText(requireContext(), "Вход выполнен", Toast.LENGTH_SHORT).show()
                val direction = SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                findNavController().navigate(direction)
            } else {
                Log.d(logTag, "Вход неуспешен")
                Toast.makeText(requireContext(), "Неверный email или пароль", Toast.LENGTH_SHORT).show()
            }
        }

        registerText.setOnClickListener {
            Log.d(logTag, "Переход на регистрацию")
            val direction = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(direction)
        }

        binding.arrowBackLog.setOnClickListener {
            Log.d(logTag, "Возврат назад")
            findNavController().navigateUp()
        }

        val userName = args.userName
        val userEmail = args.userEmail

        if (!userName.isNullOrEmpty() && !userEmail.isNullOrEmpty()) {
            userInfoText.text = "Пользователь: $userName\nEmail: $userEmail"
            userInfoText.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
