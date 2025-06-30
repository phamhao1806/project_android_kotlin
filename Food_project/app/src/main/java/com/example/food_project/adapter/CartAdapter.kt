package com.example.food_project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_project.databinding.CartItemBinding
import com.example.food_project.model.CartItem

class CartAdapter(
    private val cartItems: MutableList<CartItem>,
    private val onQuantityChanged: (CartItem) -> Unit,
    private val onItemDeleted: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            binding.cartFoodName.text = item.name
            binding.cartItemGia.text = item.price
            binding.soLuong.text = item.quantity.toString()
            Glide.with(binding.root.context).load(item.imageUrl).into(binding.cartImage)

            binding.btnTang.setOnClickListener {
                if (item.quantity < 10) {
                    item.quantity++
                    onQuantityChanged(item)
                    binding.soLuong.text = item.quantity.toString()
                }
            }

            binding.btnGiam.setOnClickListener {
                if (item.quantity > 1) {
                    item.quantity--
                    onQuantityChanged(item)
                    binding.soLuong.text = item.quantity.toString()
                }
            }

            binding.btnxoa.setOnClickListener {
                onItemDeleted(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int = cartItems.size

    fun updateList(newList: List<CartItem>) {
        cartItems.clear()
        cartItems.addAll(newList)
        notifyDataSetChanged()
    }
}
