package com.example.learnandroid

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learnandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var inputList: MutableList<String>
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inputList = mutableListOf<String>()

        binding.saveButton.setOnClickListener {
            val input = binding.inputAnagram.text.toString()
            if (input.isNotEmpty()) {
                inputList.add(input)
            }
        }

        binding.outputButton.setOnClickListener {
            binding.tvResult.text = sortAnagrams(inputList).toString()
        }

        binding.clearButton.setOnClickListener {
            inputList.clear()
            binding.inputAnagram.text.clear()
            binding.tvResult.text = ""
        }


    }

    private fun sortAnagrams(list: MutableList<String>): MutableList<List<String>> {

        val groupedAnagrams = list.groupBy { it.toCharArray().sorted().joinToString("") }

        val sortedAnagrams = groupedAnagrams.values.map { it.sorted() }

        return sortedAnagrams.toMutableList()
    }

}
