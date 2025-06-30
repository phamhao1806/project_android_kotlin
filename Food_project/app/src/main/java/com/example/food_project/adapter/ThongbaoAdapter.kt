package com.example.food_project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food_project.databinding.ThongbaoItemBinding

class ThongbaoAdapter(
    private var thongbao: ArrayList<String>,
    private var imgThongbao: ArrayList<Int>
) : RecyclerView.Adapter<ThongbaoAdapter.ThongbaoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThongbaoViewHolder {
        val binding =
            ThongbaoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ThongbaoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThongbaoViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = thongbao.size

    inner class ThongbaoViewHolder(private val binding: ThongbaoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                txtThongbao.text = thongbao[position]
                imgthongbao.setImageResource(imgThongbao[position])
            }
        }

    }
}