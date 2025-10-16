package com.rochelle.rochrun

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private val productList = mutableListOf<Product>()
    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.productRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        productAdapter = ProductAdapter(productList)
        recyclerView.adapter = productAdapter

        //gets firebase instance and gets all documents from the collection

        seedFirestoreData()
        fetchProductsFromFirestore()
    }

    private fun seedFirestoreData() {
        val db = FirebaseFirestore.getInstance()

        val products = listOf(
            Product(
                "Apple",
                "Fruits",
                5.99,
                "https://media.istockphoto.com/id/1141529240/vector/simple-apple-in-flat-style-vector-illustration.jpg?s=612x612&w=0&k=20&c=BTUl_6mGduAMWaGT9Tcr4X6n2IfK4M3HH-KCsr-Hrgs="
            ),
            Product(
                "Bananas",
                "Fruits",
                3.49,
                "https://static.vecteezy.com/system/resources/previews/005/992/583/non_2x/wooden-box-with-ripe-yellow-bananas-sweet-food-and-healthy-dessert-delicious-exotic-fruit-flat-food-illustration-vector.jpg"
            ),
            Product(
                "Carrots",
                "Vegetables",
                4.29,
                "https://static.vecteezy.com/system/resources/previews/014/770/455/non_2x/carrots-in-wooden-crate-bunch-of-carrots-carrots-isolated-illustration-illustration-of-vegetables-carrots-in-a-flat-style-drawing-on-the-topic-of-healthy-food-full-container-free-vector.jpg"
            ),
            Product(
                "Tomatoes",
                "Vegetables",
                6.10,
                "https://static.vecteezy.com/system/resources/previews/014/053/394/non_2x/wooden-box-with-tomatoes-and-a-sign-vegetable-box-icon-illustration-isolated-on-white-background-free-vector.jpg"
            )
        )

        for (product in products) {
            db.collection("products").add(product)
        }

        Toast.makeText(this, "Sample products added to Firestore", Toast.LENGTH_SHORT).show()
    }

    private fun fetchProductsFromFirestore() {
        val db = FirebaseFirestore.getInstance()

        db.collection("products").get()
            .addOnSuccessListener { result ->
                productList.clear()
                for (doc in result) {
                    val product = doc.toObject(Product::class.java)
                    productList.add(product)
                }
                productAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to load products: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}