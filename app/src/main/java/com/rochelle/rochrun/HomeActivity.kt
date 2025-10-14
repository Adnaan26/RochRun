package com.rochelle.rochrun

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.productRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


    // sample products
    val products = listOf(
        Product("Apples", "Fresh apples.", 80.0),
        Product("Bananas", "Fresh Bananas.", 95.0),
        Product("Bread", "Fresh Bread.", 30.0),
        Product("Carrots", "Carrots.", 65.0),
        Product("Cereal", "Tasty new cereal.", 48.0),
        Product("Chicken", "Fresh chicken.", 120.0),
        Product("Eggs", "Free-range eggs.", 40.0),
        Product("Fish", "Fresh fish.", 150.0),
        Product("Milk", "Organic whole milk.", 35.0),
        Product("Water", "Insulated water bottle to keep drinks cold.", 25.0)
    )

    val adapter = ProductAdapter(products)
    recyclerView.adapter = adapter
    }
}
