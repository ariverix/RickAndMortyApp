package com.example.userinterfaceapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class SignInActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)

        dbHelper = DBHelper(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val emailEdit = findViewById<TextInputEditText>(R.id.editTextTextEmailAddress)
        val passEdit = findViewById<TextInputEditText>(R.id.editTextTextPassword)
        val logButton = findViewById<Button>(R.id.button_log)
        val backButton = findViewById<ImageButton>(R.id.arrow_back_log)

        val registerLayout = findViewById<LinearLayout>(R.id.dont_have_acc_text)
        val registerText = registerLayout.getChildAt(1) as TextView

        logButton.setOnClickListener {
            val email = emailEdit.text.toString()
            val password = passEdit.text.toString()

            if (dbHelper.checkUser(email, password)) {
                Toast.makeText(this, "Вход выполнен", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java)) // переход на HomeActivity
                finish()
            } else {
                Toast.makeText(this, "Неверный email или пароль", Toast.LENGTH_SHORT).show()
            }
        }

        registerText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}
