package com.example.learnandroid

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var itemsVertical: List<VerticalRecyclerViewItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpHorizontalRecyclerView()
        setUpVerticalRecyclerView()
        setUpNavBarFragment()

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setUpHorizontalRecyclerView() {
        val itemsHorizontal = listOf(
            HorizontalRecylerViewItem(buttonText = getString(R.string.all)),
            HorizontalRecylerViewItem(buttonText = getString(R.string.party)),
            HorizontalRecylerViewItem(buttonText = getString(R.string.camping)),
            HorizontalRecylerViewItem(buttonText = getString(R.string.category1)),
            HorizontalRecylerViewItem(buttonText = getString(R.string.category2)),
            HorizontalRecylerViewItem(buttonText = getString(R.string.category3)),
        )
        val recyclerViewHorizontal = binding.recyclerViewHorizontal
        val layoutManagerHorizontal = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewHorizontal.layoutManager = layoutManagerHorizontal
        recyclerViewHorizontal.adapter = HorizontalRecyclerViewAdapter(itemsHorizontal) { selectedCategory ->
            filterVerticalItems(selectedCategory)
        }
    }

    private fun setUpVerticalRecyclerView() {
        // repeating same images for longer scroll effect
        itemsVertical = listOf(
            VerticalRecyclerViewItem(
                photo = R.drawable.photo1,
                title = getString(R.string.title),
                price = getString(R.string.price),
                categoryType = getString(R.string.camping)
            ),
            VerticalRecyclerViewItem(
                photo = R.drawable.photo2,
                title = getString(R.string.title),
                price = getString(R.string.price),
                categoryType = getString(R.string.party)
            ),
            VerticalRecyclerViewItem(
                photo = R.drawable.photo3,
                title = getString(R.string.title),
                price = getString(R.string.price),
                categoryType = getString(R.string.category1)
            ),
            VerticalRecyclerViewItem(
                photo = R.drawable.photo4,
                title = getString(R.string.title),
                price = getString(R.string.price),
                categoryType = getString(R.string.category2)
            ),
            VerticalRecyclerViewItem(
                photo = R.drawable.photo1,
                title = getString(R.string.title),
                price = getString(R.string.price),
                categoryType = getString(R.string.camping)
            ),
            VerticalRecyclerViewItem(
                photo = R.drawable.photo2,
                title = getString(R.string.title),
                price = getString(R.string.price),
                categoryType = getString(R.string.party)
            ),
            VerticalRecyclerViewItem(
                photo = R.drawable.photo3,
                title = getString(R.string.title),
                price = getString(R.string.price),
                categoryType = getString(R.string.category1)
            ),
            VerticalRecyclerViewItem(
                photo = R.drawable.photo4,
                title = getString(R.string.title),
                price = getString(R.string.price),
                categoryType = getString(R.string.category2)
            ),
            VerticalRecyclerViewItem(
                photo = R.drawable.photo1,
                title = getString(R.string.title),
                price = getString(R.string.price),
                categoryType = getString(R.string.camping)
            ),
            VerticalRecyclerViewItem(
                photo = R.drawable.photo2,
                title = getString(R.string.title),
                price = getString(R.string.price),
                categoryType = getString(R.string.party)
            ),
            VerticalRecyclerViewItem(
                photo = R.drawable.photo3,
                title = getString(R.string.title),
                price = getString(R.string.price),
                categoryType = getString(R.string.category1)
            ),
            VerticalRecyclerViewItem(
                photo = R.drawable.photo4,
                title = getString(R.string.title),
                price = getString(R.string.price),
                categoryType = getString(R.string.category2)
            ),
            VerticalRecyclerViewItem(
                photo = R.drawable.photo1,
                title = getString(R.string.title),
                price = getString(R.string.price),
                categoryType = getString(R.string.camping)
            ),
            VerticalRecyclerViewItem(
                photo = R.drawable.photo2,
                title = getString(R.string.title),
                price = getString(R.string.price),
                categoryType = getString(R.string.party)
            ),
            VerticalRecyclerViewItem(
                photo = R.drawable.photo3,
                title = getString(R.string.title),
                price = getString(R.string.price),
                categoryType = getString(R.string.category1)
            ),
            VerticalRecyclerViewItem(
                photo = R.drawable.photo4,
                title = getString(R.string.title),
                price = getString(R.string.price),
                categoryType = getString(R.string.category2)
            )
        )

        val recyclerViewVertical = binding.recyclerViewVertical
        val layoutManagerVertical = GridLayoutManager(this, 2)
        recyclerViewVertical.layoutManager = layoutManagerVertical
        recyclerViewVertical.adapter = VerticalRecyclerViewAdapter(itemsVertical)
    }

    private fun setUpNavBarFragment() {
        val navBarFragment = NavBarFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.navBarFragment_container, navBarFragment)
            .commit()
    }

    /** Function for filtering items according to selected category. If category = All, shows all
     * items, else - shows items with matching category only. Uses adapter's updateItems function. */
    private fun filterVerticalItems(selectedCategory: String) {
        val recyclerViewVertical = binding.recyclerViewVertical
        val adapter = recyclerViewVertical.adapter as VerticalRecyclerViewAdapter

        if (selectedCategory == getString(R.string.all)) {
            adapter.updateItems(itemsVertical)
        }
        else {
            val filteredItems = itemsVertical.filter { item ->
                item.categoryType == selectedCategory }
            adapter.updateItems(filteredItems)
        }
    }
}
