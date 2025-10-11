//package com.example.userinterfaceapp
//
//import android.os.Bundle
//import android.util.Log
//import android.widget.Toast
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import org.json.JSONObject
//import java.net.URL
//
//class HomeActivity : BaseActivity() {
//
//    private lateinit var recyclerView: RecyclerView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)
//
//        Log.d("HomeActivity", "Activity создана")
//
//        recyclerView = findViewById(R.id.characters_recycler_view)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        lifecycleScope.launch {
//            try {
//                Log.d("HomeActivity", "Начинаем загрузку данных")
//
//                val data = withContext(Dispatchers.IO) {
//                    URL("https://rickandmortyapi.com/api/character").readText()
//                }
//
//                Log.d("HomeActivity", "Данные получены: ${data.take(100)}...")
//
//                val json = JSONObject(data)
//                val results = json.getJSONArray("results")
//                val characters = mutableListOf<Character>()
//
//                Log.d("HomeActivity", "Количество персонажей: ${results.length()}")
//
//                for (i in 0 until results.length()) {
//                    val char = results.getJSONObject(i)
//                    characters.add(Character(
//                        name = char.getString("name"),
//                        image = char.getString("image"),
//                        status = char.getString("status"),
//                        species = char.getString("species")
//                    ))
//                }
//
//                Log.d("HomeActivity", "Персонажи созданы: ${characters.size}")
//
//                recyclerView.adapter = CharacterAdapter(characters)
//
//                Log.d("HomeActivity", "Адаптер установлен")
//
//            } catch (e: Exception) {
//                Log.e("HomeActivity", "Ошибка: ${e.message}", e)
//                Toast.makeText(this@HomeActivity, "Ошибка загрузки персонажей: ${e.message}", Toast.LENGTH_LONG).show()
//            }
//        }
//    }
//}