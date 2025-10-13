package com.example.userinterfaceapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.userinterfaceapp.databinding.FragmentSignInBinding

class SignInFragment : BaseFragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: DBHelper
    private var pendingUserBundle: Bundle? = null

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

        val mainActivity = activity as? MainActivity

        val emailEdit = binding.editTextTextEmailAddress
        val passEdit = binding.editTextTextPassword
        val userInfoText = binding.userInfoText

        binding.buttonLog.setOnClickListener {
            val email = emailEdit.text.toString()
            val password = passEdit.text.toString()
            logEvent("Попытка входа: email=$email")

            if (dbHelper.checkUser(email, password)) {
                logEvent("Вход успешен")
                Toast.makeText(requireContext(), "Вход выполнен", Toast.LENGTH_SHORT).show()
                mainActivity?.navigateToHome(addToBackStack = false)
            } else {
                logEvent("Вход неуспешен")
                Toast.makeText(requireContext(), "Неверный email или пароль", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerLink.setOnClickListener {
            logEvent("Переход на регистрацию")
            mainActivity?.navigateToSignUp()
        }

        binding.arrowBackLog.setOnClickListener {
            logEvent("Возврат назад")
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        parentFragmentManager.setFragmentResultListener(
            REQUEST_USER_DATA,
            viewLifecycleOwner,
        ) { _, bundle ->
            logEvent("Получен результат регистрации")
            pendingUserBundle = bundle
            displayUserInfo(userInfoText, bundle)
            parentFragmentManager.clearFragmentResult(REQUEST_USER_DATA)
        }

        val argumentsBundle = savedInstanceState?.getBundle(STATE_USER_INFO)
            ?: arguments
            ?: pendingUserBundle
        argumentsBundle?.let {
            pendingUserBundle = it
            displayUserInfo(userInfoText, it)
        }
    }

    private fun displayUserInfo(targetView: TextView, bundle: Bundle) {
        val userName = bundle.getString(ARG_USER_NAME)
        val userEmail = bundle.getString(ARG_USER_EMAIL)
        val userObject = bundle.getSerializable(ARG_USER_OBJECT) as? User

        logEvent("Получены данные: name=$userName, email=$userEmail, user=$userObject")

        val displayName = userName ?: userObject?.name
        val displayEmail = userEmail ?: userObject?.email

        if (!displayName.isNullOrEmpty() && !displayEmail.isNullOrEmpty()) {
            targetView.text = "Пользователь: $displayName\nEmail: $displayEmail"
            targetView.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        logEvent("onDestroyView() вызван")
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        pendingUserBundle?.let { outState.putBundle(STATE_USER_INFO, it) }
    }

    companion object {
        const val ARG_USER_NAME = "user_name"
        const val ARG_USER_EMAIL = "user_email"
        const val ARG_USER_OBJECT = "user_object"
        const val REQUEST_USER_DATA = "request_user_data"
        private const val STATE_USER_INFO = "state_user_info"

        fun newInstance(bundle: Bundle? = null): SignInFragment {
            return SignInFragment().apply {
                arguments = bundle
            }
        }
    }
}
