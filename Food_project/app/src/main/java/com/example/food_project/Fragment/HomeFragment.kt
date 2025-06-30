package com.example.food_project.Fragment

import CartViewModelFactory
import com.example.food_project.viewmodel.CartViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.food_project.Api.RetrofitClient
import com.example.food_project.R
import com.example.food_project.adapter.MenuAdapter
import com.example.food_project.databinding.FragmentHomeBinding
import com.example.food_project.model.MenuItem
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var menuPopular: MutableList<MenuItem>
    private lateinit var adapter: MenuAdapter
    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val factory = CartViewModelFactory(requireActivity().application, userId)
        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]
        menuPopular = mutableListOf()
        adapter = MenuAdapter(menuPopular, requireContext()) { cartItem ->
            cartViewModel.addToCart(cartItem)
            Toast.makeText(requireContext(), "Đã thêm ${cartItem.name} vào giỏ", Toast.LENGTH_SHORT).show()
        }

        binding.PopularRecyclview.layoutManager = LinearLayoutManager(requireContext())
        binding.PopularRecyclview.adapter = adapter

        binding.viewMenu.setOnClickListener {
            val bottomSheetDialog = MenuBootomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager, "Test")
        }

        showLoading(true)
        retrieveMenupopular()
        return binding.root
    }

    private fun retrieveMenupopular() {
        RetrofitClient.api.getAllMenuItems().enqueue(object : Callback<List<MenuItem>> {
            override fun onResponse(call: Call<List<MenuItem>>, response: Response<List<MenuItem>>) {
                showLoading(false)
                if (response.isSuccessful) {
                    response.body()?.let {
                        menuPopular.clear()
                        menuPopular.addAll(it.shuffled())
                        adapter.notifyDataSetChanged()
                        Log.d("API", "Data loaded: ${menuPopular.size}")
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
        binding.progressBarPopular.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.PopularRecyclview.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = ArrayList<SlideModel>().apply {
            add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
            add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
            add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))
        }

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {}

            override fun onItemSelected(position: Int) {
                Toast.makeText(requireContext(), "Select Image $position", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
