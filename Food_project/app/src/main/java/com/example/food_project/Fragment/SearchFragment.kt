package com.example.food_project.Fragment

import CartViewModelFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_project.Api.RetrofitClient
import com.example.food_project.adapter.MenuAdapter
import com.example.food_project.databinding.FragmentSearchBinding
import com.example.food_project.model.MenuItem
import com.example.food_project.viewmodel.CartViewModel
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private lateinit var cartViewModel: CartViewModel

    private val allMenuItems = mutableListOf<MenuItem>()
    private val filteredMenuItems = mutableListOf<MenuItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        cartViewModel = ViewModelProvider(
            this,
            CartViewModelFactory(requireActivity().application, userId)
        )[CartViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        adapter = MenuAdapter(filteredMenuItems, requireContext()) { cartItem ->
            cartViewModel.addToCart(cartItem)
            Toast.makeText(requireContext(), "Đã thêm ${cartItem.name} vào giỏ", Toast.LENGTH_SHORT).show()
        }

        binding.menuRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecycleView.adapter = adapter

        setupSearchView()
        fetchMenuItemsFromApi()

        return binding.root
    }

    private fun fetchMenuItemsFromApi() {
        showLoading(true)
        RetrofitClient.api.getAllMenuItems().enqueue(object : Callback<List<MenuItem>> {
            override fun onResponse(call: Call<List<MenuItem>>, response: Response<List<MenuItem>>) {
                showLoading(false)
                if (response.isSuccessful) {
                    response.body()?.let {
                        allMenuItems.clear()
                        allMenuItems.addAll(it)
                        filteredMenuItems.clear()
                        filteredMenuItems.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<List<MenuItem>>, t: Throwable) {
                showLoading(false)
            }
        })
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }
        })
    }

    private fun filterMenuItems(query: String) {
        filteredMenuItems.clear()
        if (query.isEmpty()) {
            filteredMenuItems.addAll(allMenuItems)
        } else {
            filteredMenuItems.addAll(
                allMenuItems.filter {
                    it.foodName?.contains(query, ignoreCase = true) == true
                }
            )
        }
        adapter.notifyDataSetChanged()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.menuRecycleView.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}
