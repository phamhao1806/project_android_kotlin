package com.example.food_project.Fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.food_project.activity.LoginActivity
import com.example.food_project.R
import com.example.food_project.databinding.FragmentProfileBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container,false)
        binding.txtlichsu.setOnClickListener{
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).selectedItemId =
                R.id.historyFragment

        }
        binding.txtDangxuat.setOnClickListener{
//            val uid = FirebaseAuth.getInstance().currentUser?.uid
//            FirebaseAuth.getInstance().signOut()
//
//            lifecycleScope.launch {
//                uid?.let { userId ->
//                    val db = AppDatabase.getDatabase(requireContext())
//                    withContext(Dispatchers.IO) {
//                        db.cartDao().clearCart(userId)
//                        db.orderHistoryDao().clearHistory(userId)
//                    }
//                }

                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                 Toast.makeText(requireContext(), "Đăng xuất thành công!", Toast.LENGTH_SHORT).show()
            }

//        }
        binding.imgFace.setOnClickListener {
            openLink("https://www.facebook.com/pham.hao.167894/")
        }
        binding.imgzalo.setOnClickListener {
            openLink("https://zalo.me/0389710154")
        }
        binding.txtDoiMk.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_changePasswordFragment)
        }
        return binding.root
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Không tìm thấy ứng dụng để mở liên kết", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {

    }
}