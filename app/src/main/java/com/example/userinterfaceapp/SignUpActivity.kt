package com.example.userinterfaceapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class SignUpActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        dbHelper = DBHelper(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nameEdit = findViewById<TextInputEditText>(R.id.et_name)
        val emailEdit = findViewById<TextInputEditText>(R.id.editTextTextEmailAddress)
        val passEdit = findViewById<TextInputEditText>(R.id.editTextTextPassword)
        val ageEdit = findViewById<TextInputEditText>(R.id.et_age)
        val genderGroup = findViewById<RadioGroup>(R.id.rg_gender)
        val registerButton = findViewById<Button>(R.id.button_input)
        val loginLayout = findViewById<LinearLayout>(R.id.already_acc_reg)
        val loginText = loginLayout.getChildAt(1) as TextView


        registerButton.setOnClickListener {
            val name = nameEdit.text.toString()
            val email = emailEdit.text.toString()
            val password = passEdit.text.toString()
            val age = ageEdit.text.toString()
            val genderId = genderGroup.checkedRadioButtonId
            val gender = if (genderId != -1) findViewById<RadioButton>(genderId).text.toString() else ""

            // простая проверка
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || age.isEmpty() || gender.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    dbHelper.addUser(email, password)  // пока сохраняем только email и пароль
                    Toast.makeText(this, "Аккаунт создан", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, SignInActivity::class.java))
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this, "Пользователь уже существует", Toast.LENGTH_SHORT).show()
                }
            }
        }


        loginText.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }
}
