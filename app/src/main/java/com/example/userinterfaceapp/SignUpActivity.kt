//package com.example.userinterfaceapp
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import android.widget.ImageButton
//import android.widget.LinearLayout
//import android.widget.RadioButton
//import android.widget.RadioGroup
//import android.widget.TextView
//import android.widget.Toast
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.google.android.material.textfield.TextInputEditText
//
//class SignUpActivity : BaseActivity() {
//
//    private lateinit var dbHelper: DBHelper
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_sign_up)
//
//        dbHelper = DBHelper(this)
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        Log.d("SignUpActivity", "UI элементы инициализированы")
//
//        val nameEdit = findViewById<TextInputEditText>(R.id.et_name)
//        val emailEdit = findViewById<TextInputEditText>(R.id.editTextTextEmailAddress)
//        val passEdit = findViewById<TextInputEditText>(R.id.editTextTextPassword)
//        val ageEdit = findViewById<TextInputEditText>(R.id.et_age)
//        val genderGroup = findViewById<RadioGroup>(R.id.rg_gender)
//        val registerButton = findViewById<Button>(R.id.button_input)
//        val loginLayout = findViewById<LinearLayout>(R.id.already_acc_reg)
//        val loginText = loginLayout.getChildAt(1) as TextView
//
//        registerButton.setOnClickListener {
//            val name = nameEdit.text.toString()
//            val email = emailEdit.text.toString()
//            val password = passEdit.text.toString()
//            val age = ageEdit.text.toString()
//            val genderId = genderGroup.checkedRadioButtonId
//            val gender = if (genderId != -1) findViewById<RadioButton>(genderId).text.toString() else ""
//
//            Log.d("SignUpActivity", "Попытка регистрации: name=$name, email=$email, age=$age, gender=$gender")
//
//            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || age.isEmpty() || gender.isEmpty()) {
//                Log.d("SignUpActivity", "Не все поля заполнены")
//                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
//            } else {
//                try {
//                    dbHelper.addUser(email, password)
//                    Log.d("SignUpActivity", "Пользователь успешно добавлен в БД")
//                    Toast.makeText(this, "Аккаунт создан", Toast.LENGTH_SHORT).show()
//
//                    // Передача данных обратно
//                    val user = User(name, email, password, age, gender)
//                    Log.d("SignUpActivity", "Создан объект User: $user")
//
//                    val intent = Intent().apply {
//                        putExtra("user_name", name)
//                        putExtra("user_email", email)
//                        putExtra("user_object", user)
//                    }
//                    Log.d("SignUpActivity", "Данные добавлены в Intent, возвращаемся")
//                    setResult(RESULT_OK, intent)
//                    finish()
//
//                } catch (e: Exception) {
//                    Log.e("SignUpActivity", "Ошибка при регистрации: ${e.message}")
//                    Toast.makeText(this, "Пользователь уже существует", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//
//        loginText.setOnClickListener {
//            Log.d("SignUpActivity", "Возврат на страницу входа")
//            finish()
//        }
//    }
//}