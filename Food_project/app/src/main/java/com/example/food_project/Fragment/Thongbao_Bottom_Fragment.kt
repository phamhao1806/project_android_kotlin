package com.example.food_project.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_project.R
import com.example.food_project.databinding.FragmentThongbaoBottomBinding
import com.example.food_project.adapter.ThongbaoAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class Thongbao_Bottom_Fragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentThongbaoBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentThongbaoBottomBinding.inflate(layoutInflater, container, false)
        val thongbao = listOf(
            "Đơn hàng của bạn đã được hủy thành công",
            "Đơn hàng đã được tài xế tiếp nhận",
            "Chúc mừng bạn đã đặt hàng"
        )
        val imgthongbao = listOf(R.drawable.img_buon, R.drawable.img_giaohang, R.drawable.img_tick)

        val adapter = ThongbaoAdapter(
            ArrayList(thongbao),
            ArrayList(imgthongbao)
        )

        binding.thongbaoRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.thongbaoRecycleView.adapter = adapter
        return binding.root
    }

    companion object {

    }
}