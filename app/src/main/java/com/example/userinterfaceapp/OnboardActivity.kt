//package com.example.userinterfaceapp
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//
//class OnboardActivity : BaseActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_onboard)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        Log.d("OnboardActivity", "UI элементы инициализированы")
//
//        val buttonReady: Button = findViewById(R.id.button_ready)
//        buttonReady.setOnClickListener {
//            Log.d("OnboardActivity", "Кнопка 'Готов' нажата, переход на SignInActivity")
//            val intent = Intent(this, SignInActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }
//}