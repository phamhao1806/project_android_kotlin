package com.example.food_project.Fragment

import CartViewModelFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.food_project.adapter.BuyAgainAdapter
import com.example.food_project.databinding.FragmentHistoryBinding
import com.example.food_project.model.CartItem
import com.example.food_project.viewmodel.CartViewModel
import com.example.food_project.viewmodel.OrderHistoryViewModel
import com.example.food_project.viewmodel.OrderHistoryViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var orderHistoryViewModel: OrderHistoryViewModel
    private lateinit var cartViewModel: CartViewModel

    private lateinit var buyAgainAdapter: BuyAgainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        val factory = OrderHistoryViewModelFactory(requireActivity().application, userId)
        orderHistoryViewModel = ViewModelProvider(this, factory)[OrderHistoryViewModel::class.java]

        val cartFactory = CartViewModelFactory(requireActivity().application, userId)
        cartViewModel = ViewModelProvider(this, cartFactory)[CartViewModel::class.java]

        setupRecyclerView()
        observeData()

        return binding.root
    }


    private fun setupRecyclerView() {
        buyAgainAdapter = BuyAgainAdapter { item ->
            val cartItem = CartItem(
                id = 0,
                userId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
                name = item.name,
                price = item.price,
                imageUrl = item.imageUrl,
                quantity = 1
            )
            lifecycleScope.launch {
                cartViewModel.addToCart(cartItem)
                Toast.makeText(requireContext(), "Đã thêm ${item.name} vào giỏ hàng", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buyAgainRecycleView.apply {
            adapter = buyAgainAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeData() {
        orderHistoryViewModel.allOrders.observe(viewLifecycleOwner) { orders ->
            if (orders.isNotEmpty()) {
                val sortedOrders = orders.sortedByDescending { it.timestamp }
                val latestOrder = sortedOrders.first()
                val pastOrders = sortedOrders.drop(1)
                binding.txtBuyAgainName.text = latestOrder.name
                binding.txtBuyAgainPrice.text = latestOrder.price  //
                Glide.with(this)
                    .load(latestOrder.imageUrl)
                    .into(binding.imgBuyAgain)
                buyAgainAdapter.submitList(pastOrders)
            } else {
                binding.txtBuyAgainName.text = "Chưa có đơn hàng"
                binding.txtBuyAgainPrice.text = ""
                binding.imgBuyAgain.setImageResource(0)
                buyAgainAdapter.submitList(emptyList())
            }
        }
    }
}
