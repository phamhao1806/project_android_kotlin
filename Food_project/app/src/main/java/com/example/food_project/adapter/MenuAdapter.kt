package com.example.food_project.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_project.databinding.MenuItemBinding
import com.example.food_project.activity.DetailActivity
import com.example.food_project.model.MenuItem
import com.example.food_project.model.CartItem

class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val requireContext: Context,
    private val onAddToCartClicked: (CartItem) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItems.size

    inner class MenuViewHolder(private val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailActivity(position)
                }
            }

            binding.txtThemMenu.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val menuItem = menuItems[position]
                    val userId = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid ?: ""
                    val cartItem = CartItem(
                        id = 0,
                        userId = userId,
                        name = menuItem.foodName ?: "",
                        price = menuItem.foodPrice ?: "",
                        imageUrl = menuItem.foodImage ?: "",
                        quantity = 1
                    )
                    onAddToCartClicked(cartItem)
                }
            }
        }

        private fun openDetailActivity(position: Int) {
            val menuItem = menuItems[position]
            val intent = Intent(requireContext, DetailActivity::class.java).apply {
                putExtra("menuItem", menuItem)
            }
            requireContext.startActivity(intent)
        }

        fun bind(position: Int) {
            val menuItem = menuItems[position]
            binding.apply {
                menuFoodName.text = menuItem.foodName
                txtGiaMenu.text = menuItem.foodPrice
                val uri = Uri.parse(menuItem.foodImage)
                Glide.with(requireContext).load(uri).into(imgMenu)
            }
        }
    }
}
