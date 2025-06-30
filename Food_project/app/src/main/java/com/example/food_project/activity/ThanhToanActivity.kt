package com.example.food_project.activity

import CartViewModelFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.food_project.databinding.ActivityThanhToanBinding
import com.example.food_project.Fragment.CongratsBottomSheetFragment
import com.example.food_project.viewmodel.CartViewModel
import com.example.food_project.viewmodel.ThanhToanViewModel
import com.example.food_project.viewmodel.ThanhToanViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class ThanhToanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThanhToanBinding
    private lateinit var thanhToanViewModel: ThanhToanViewModel
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThanhToanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lấy userId hiện tại từ FirebaseAuth
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // Khởi tạo ViewModelFactory truyền userId
        val thanhToanFactory = ThanhToanViewModelFactory(application, userId)
        thanhToanViewModel = ViewModelProvider(this, thanhToanFactory)[ThanhToanViewModel::class.java]

        val cartFactory = CartViewModelFactory(application, userId)
        cartViewModel = ViewModelProvider(this, cartFactory)[CartViewModel::class.java]

        thanhToanViewModel.totalPrice.observe(this) { total ->
            val formatted = if (total != null) {
                val localeVN = Locale("vi", "VN")
                val formatter = NumberFormat.getInstance(localeVN)
                formatter.format(total.toLong()) + "đ"
            } else {
                "0đ"
            }
            binding.tvTotalPrice.setText(formatted)
        }

        binding.imgThoat.setOnClickListener {
            finish()
        }

        // Xử lý khi nhấn nút Thanh toán
        binding.btnThanhtoan.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val phone = binding.edtPhone.text.toString().trim()
            val address = binding.edtAddress.text.toString().trim()
            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Ngăn không cho tiếp tục
            }
            cartViewModel.allItems.observe(this) { cartItems ->
                if (!cartItems.isNullOrEmpty()) {
                    lifecycleScope.launch {
                        thanhToanViewModel.saveOrderHistoryFromCart(cartItems)
                        thanhToanViewModel.clearCart()
                    }
                    val bottomSheetDialog = CongratsBottomSheetFragment()
                    bottomSheetDialog.show(supportFragmentManager, "congrats_bottom_sheet")
                } else {
                    Toast.makeText(this, "Giỏ hàng đang trống", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
