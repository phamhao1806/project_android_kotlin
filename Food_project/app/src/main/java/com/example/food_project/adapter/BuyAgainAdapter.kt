package com.example.food_project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_project.databinding.BuyAgainItemBinding
import com.example.food_project.model.OrderHistoryItem

class BuyAgainAdapter(
    private val onBuyAgainClicked: (OrderHistoryItem) -> Unit
) : ListAdapter<OrderHistoryItem, BuyAgainAdapter.BuyAgainViewHolder>(DiffCallback()) {

    inner class BuyAgainViewHolder(private val binding: BuyAgainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OrderHistoryItem) {
            binding.txtBuyAgainName.text = item.name
            binding.txtBuyAgainPrice.text = item.price
            Glide.with(binding.root.context)
                .load(item.imageUrl)
                .into(binding.imgBuyAgain)

            binding.btnBuyAgain.setOnClickListener {
                onBuyAgainClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        val binding = BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuyAgainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<OrderHistoryItem>() {
        override fun areItemsTheSame(oldItem: OrderHistoryItem, newItem: OrderHistoryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OrderHistoryItem, newItem: OrderHistoryItem): Boolean {
            return oldItem == newItem
        }
    }
}
