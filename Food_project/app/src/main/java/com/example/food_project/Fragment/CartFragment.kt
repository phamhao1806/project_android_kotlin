package com.example.food_project.Fragment
import CartViewModelFactory
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_project.activity.ThanhToanActivity
import com.example.food_project.adapter.CartAdapter
import com.example.food_project.databinding.FragmentCartBinding
import com.example.food_project.model.CartItem
import com.example.food_project.viewmodel.CartViewModel
import com.google.firebase.auth.FirebaseAuth

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter
    private var cartItemsList = listOf<CartItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        cartAdapter = CartAdapter(
            mutableListOf(),
            onQuantityChanged = { item -> cartViewModel.updateItem(item) },
            onItemDeleted = { item -> cartViewModel.deleteItem(item) }
        )

        binding.cartRecycleView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }

        // lấy userId hiện tại
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // khởi tạo ViewModel với Factory để truyền userId và application
        val factory = CartViewModelFactory(requireActivity().application, userId)
        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]

        // Quan sát dữ liệu giỏ hàng, cập nhật adapter
        cartViewModel.allItems.observe(viewLifecycleOwner) { items ->
            cartItemsList = items
            cartAdapter.updateList(items)
        }

        binding.btnCart.setOnClickListener {
            if (cartItemsList.isEmpty()) {
                Toast.makeText(requireContext(), "Bạn chưa chọn món ăn, vui lòng chọn", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(requireContext(), ThanhToanActivity::class.java)
                startActivity(intent)
            }
        }

        return binding.root
    }
}
