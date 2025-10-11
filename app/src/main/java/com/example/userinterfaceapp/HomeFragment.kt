package com.example.userinterfaceapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class HomeFragment : BaseFragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logEvent("onCreateView() вызван")
        return inflater.inflate(R.layout.activity_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.characters_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    URL("https://rickandmortyapi.com/api/character").readText()
                }

                val json = JSONObject(data)
                val results = json.getJSONArray("results")
                val characters = mutableListOf<Character>()

                for (i in 0 until results.length()) {
                    val char = results.getJSONObject(i)
                    characters.add(Character(
                        name = char.getString("name"),
                        image = char.getString("image"),
                        status = char.getString("status"),
                        species = char.getString("species")
                    ))
                }

                recyclerView.adapter = CharacterAdapter(characters)

            } catch (e: Exception) {
                Log.e(logTag, "Ошибка: ${e.message}", e)
                Toast.makeText(requireContext(), "Ошибка загрузки персонажей: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}