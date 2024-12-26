package com.example.learnandroid

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var currentPosition: Int = 0
    private var itemsList = mutableListOf<Items>()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpListeners()
        setUpRecyclerView()

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setUpListeners() {
        binding.btnAddNewAddress.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AddNewAddressFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.recyclerView
        val adapter = RecyclerViewAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.submitList(itemsList)
    }

    /** Function for adding new items at zero index. Takes 3 arguments and is called from AddNewAddressFragment */
    fun addItems(image: Int, addressName: String, address: String) {
        val id = itemsList.size + 1
        itemsList.add(index = 0, Items(id, image, addressName, address))
        binding.recyclerView.adapter?.notifyItemInserted(0)
    }

    /** Function for removing items by checking id. Takes 2 arguments and is called from RecyclerViewAdapter */
    fun removeItem(id: Int, position: Int) {
        for (item in itemsList) {
            if (item.id == id) {
                itemsList.remove(item)
                break
            }
        }
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }

    /** Function for updating items. Takes 3 arguments that have default values and can be left blank
     *  and a position which is a must. If argument is not passed, value won't be updated.
     *  Functions is called from EditAddressFragment */
    fun updateItem(addressName: String = "", address: String = "", image: Int = 0, position: Int) {
        if (addressName != "") {
            itemsList[position].addressName = addressName
        }
        if (address != "") {
            itemsList[position].address = address
        }
        if (image != 0) {
            itemsList[position].image = image
        }
        binding.recyclerView.adapter?.notifyItemChanged(position)
    }

}
