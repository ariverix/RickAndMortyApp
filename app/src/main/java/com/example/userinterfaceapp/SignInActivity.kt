//package com.example.userinterfaceapp
//
//import android.annotation.SuppressLint
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import android.widget.ImageButton
//import android.widget.LinearLayout
//import android.widget.TextView
//import android.widget.Toast
//import androidx.activity.enableEdgeToEdge
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.google.android.material.textfield.TextInputEditText
//
//class SignInActivity : BaseActivity() {
//
//    private lateinit var dbHelper: DBHelper
//    private lateinit var userInfoText: TextView
//
//    private val registerLauncher = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        Log.d("SignInActivity", "Получен результат от регистрации: $result")
//        if (result.resultCode == RESULT_OK) {
//            val data = result.data
//            val userName = data?.getStringExtra("user_name")
//            val userEmail = data?.getStringExtra("user_email")
//            val userObject = data?.getSerializableExtra("user_object") as? User
//
//            Log.d("SignInActivity", "Получены данные: name=$userName, email=$userEmail")
//            Log.d("SignInActivity", "Получен объект User: $userObject")
//
//            if (userName != null && userEmail != null) {
//                userInfoText.text = "Пользователь: $userName\nEmail: $userEmail"
//                userInfoText.visibility = TextView.VISIBLE
//                Log.d("SignInActivity", "Данные отображены в UI")
//            }
//        }
//    }
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_sign_in)
//
//        dbHelper = DBHelper(this)
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        val emailEdit = findViewById<TextInputEditText>(R.id.editTextTextEmailAddress)
//        val passEdit = findViewById<TextInputEditText>(R.id.editTextTextPassword)
//        val logButton = findViewById<Button>(R.id.button_log)
//        val backButton = findViewById<ImageButton>(R.id.arrow_back_log)
//        userInfoText = findViewById<TextView>(R.id.user_info_text)
//
//        val registerLayout = findViewById<LinearLayout>(R.id.dont_have_acc_text)
//        val registerText = registerLayout.getChildAt(1) as TextView
//
//        logButton.setOnClickListener {
//            val email = emailEdit.text.toString()
//            val password = passEdit.text.toString()
//
//            Log.d("SignInActivity", "Попытка входа: email=$email")
//
//            if (dbHelper.checkUser(email, password)) {
//                Log.d("SignInActivity", "Вход успешен")
//                Toast.makeText(this, "Вход выполнен", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this, HomeActivity::class.java))
//                finish()
//            } else {
//                Log.d("SignInActivity", "Вход неуспешен - неверные данные")
//                Toast.makeText(this, "Неверный email или пароль", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        registerText.setOnClickListener {
//            Log.d("SignInActivity", "Переход на регистрацию")
//            val intent = Intent(this, SignUpActivity::class.java)
//            registerLauncher.launch(intent)
//        }
//
//        backButton.setOnClickListener {
//            finish()
//        }
//    }
//}
//
