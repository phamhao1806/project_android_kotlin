package com.example.food_project.activity

import CartViewModelFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.food_project.databinding.ActivityDetailBinding
import com.example.food_project.model.MenuItem
import com.example.food_project.model.CartItem
import com.example.food_project.viewmodel.CartViewModel
import com.google.firebase.auth.FirebaseAuth

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val factory = CartViewModelFactory(application, userId)
        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]


        val menuItem = intent.getSerializableExtra("menuItem") as? MenuItem

        if (menuItem == null) {
            Toast.makeText(this, "Không nhận được dữ liệu món ăn", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.detailMonAn.text = menuItem.foodName ?: "Không rõ tên"
        binding.txtMota.text = menuItem.foodMota ?: ""
        binding.txtNguyenlieu.text = menuItem.foodNguyenlieu ?: ""

        Glide.with(this)
            .load(menuItem.foodImage ?: "")
            .into(binding.imgDetail)

        binding.button4.setOnClickListener {

            val priceString = menuItem.foodPrice ?: "0đ"

            val cartItem = CartItem(
                userId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
                name = menuItem.foodName ?: "Không rõ tên",
                imageUrl = menuItem.foodImage ?: "",
                price = priceString,
                quantity = 1
            )

            cartViewModel.addToCart(cartItem)
            Toast.makeText(this, "${menuItem.foodName ?: "Món ăn"} đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show()
        }

        binding.imgButton.setOnClickListener {
            finish()
        }
    }
}
