package com.example.userinterfaceapp

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import coil.load
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class HomeActivity : AppCompatActivity() {

    private lateinit var container: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        container = findViewById(R.id.characters_container)

        lifecycleScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    URL("https://rickandmortyapi.com/api/character").readText()
                }

                val json = JSONObject(data)
                val results = json.getJSONArray("results")

                for (i in 0 until results.length()) {
                    val char = results.getJSONObject(i)
                    val name = char.getString("name")
                    val image = char.getString("image")
                    val status = char.getString("status")
                    val species = char.getString("species")

                    val row = LinearLayout(this@HomeActivity).apply {
                        orientation = LinearLayout.HORIZONTAL
                        setPadding(16, 16, 16, 16)
                        gravity = Gravity.CENTER_VERTICAL
                    }

                    val img = ImageView(this@HomeActivity).apply {
                        layoutParams = LinearLayout.LayoutParams(250, 250)
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        contentDescription = name
                    }

                    img.load(image)

                    val info = TextView(this@HomeActivity).apply {
                        text = name
                        setTextColor(ContextCompat.getColor(this@HomeActivity, R.color.morty_white))
                        textSize = 16f
                        setTypeface(null, Typeface.BOLD)
                        setPadding(16, 0, 0, 0)
                    }

                    val statusView = TextView(this@HomeActivity).apply {
                        text = "Статус: $status"
                        textSize = 14f
                        setTypeface(null, Typeface.BOLD)
                        setPadding(16, 4, 0, 0)

                        setTextColor(
                            when (status) {
                                "Alive" -> 0xFF4CAF50.toInt() // зелёный
                                "Dead" -> 0xFFF44336.toInt()  // красный
                                else -> 0xFF9E9E9E.toInt()    // серый
                            }
                        )
                    }

                    val speciesView = TextView(this@HomeActivity).apply {
                        text = "Вид: $species"
                        textSize = 14f
                        setTypeface(null, Typeface.BOLD)
                        setPadding(16, 2, 0, 0)

                        setTextColor(
                            when (species) {
                                "Human" -> 0xFF2196F3.toInt() // синий
                                "Alien" -> 0xFF9C27B0.toInt() // фиолетовый
                                else -> 0xFFFF9800.toInt()    // оранжевый
                            }
                        )
                    }

                    val textContainer = LinearLayout(this@HomeActivity).apply {
                        orientation = LinearLayout.VERTICAL
                        addView(info)
                        addView(statusView)
                        addView(speciesView)
                    }

                    row.addView(img)
                    row.addView(textContainer)

                    container.addView(row)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@HomeActivity, "Ошибка загрузки персонажей", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
