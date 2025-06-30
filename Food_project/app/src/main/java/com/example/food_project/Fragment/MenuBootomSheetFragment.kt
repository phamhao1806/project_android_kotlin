package com.example.food_project.Fragment

import CartViewModelFactory
import com.example.food_project.viewmodel.CartViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_project.Api.RetrofitClient
import com.example.food_project.adapter.MenuAdapter
import com.example.food_project.databinding.FragmentMenuBootomSheetBinding
import com.example.food_project.model.MenuItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuBootomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMenuBootomSheetBinding
    private lateinit var menuItems: MutableList<MenuItem>
    private lateinit var adapter: MenuAdapter
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid ?: "unknown_user"

        val factory = CartViewModelFactory(requireActivity().application, userId)
        cartViewModel = ViewModelProvider(requireActivity(), factory)[CartViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBootomSheetBinding.inflate(inflater, container, false)

        menuItems = mutableListOf()
        adapter = MenuAdapter(menuItems, requireContext()) { cartItem ->
            cartViewModel.addToCart(cartItem)
            Toast.makeText(requireContext(), "Đã thêm ${cartItem.name} vào giỏ", Toast.LENGTH_SHORT)
                .show()
        }

        binding.menuRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecycleView.adapter = adapter

        binding.btnBack.setOnClickListener {
            dismiss()
        }
        showLoading(true)
        retrieveMenuItems()

        return binding.root
    }


    private fun retrieveMenuItems() {
        RetrofitClient.api.getAllMenuItems().enqueue(object : Callback<List<MenuItem>> {
            override fun onResponse(
                call: Call<List<MenuItem>>,
                response: Response<List<MenuItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    response.body()?.let {
                        menuItems.clear()
                        menuItems.addAll(it)
                        adapter.notifyDataSetChanged()
                        Log.d("API", "Data loaded: ${menuItems.size}")
                    }
                } else {
                    Log.e("API", "Error response: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<MenuItem>>, t: Throwable) {
                showLoading(false)
                Log.e("API", "Failed to fetch data: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.menuRecycleView.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}
