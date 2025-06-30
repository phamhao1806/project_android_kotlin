package com.example.food_project.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.food_project.database.repository.CartRepository
import com.example.food_project.database.repository.OrderHistoryRepository
import com.example.food_project.model.CartItem
import com.example.food_project.model.OrderHistoryItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ThanhToanViewModel(
    application: Application, private val userId: String
) : AndroidViewModel(application) {
    private val cartRepository: CartRepository
    private val orderHistoryRepository: OrderHistoryRepository
    val allItems: LiveData<List<CartItem>>
    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> get() = _totalPrice

    private val cartObserver = Observer<List<CartItem>> { items ->
        calculateTotal(items)
    }

    init {
        val db = AppDatabase.getDatabase(application)
        cartRepository = CartRepository(db.cartDao())
        orderHistoryRepository = OrderHistoryRepository(db.orderHistoryDao())
        allItems = cartRepository.getAllItems(userId)

        allItems.observeForever(cartObserver)
    }

    // Chuyển chuỗi giá có thể có dấu chấm, dấu phẩy, ký tự 'đ' thành Double
    private fun parsePriceString(priceStr: String): Double {
        val cleaned =
            priceStr.replace("đ", "").replace("₫", "").replace(".", "").replace(",", "").trim()
        return cleaned.toDoubleOrNull() ?: 0.0
    }

    // Tính tổng tiền theo số lượng và giá từng món
    private fun calculateTotal(items: List<CartItem>) {
        val total = items.sumOf { parsePriceString(it.price) * it.quantity }
        _totalPrice.postValue(total)
    }

    // Xóa hết giỏ hàng của user hiện tại
    fun clearCart() = viewModelScope.launch {
        cartRepository.clearCart(userId)
    }

    // Lưu đơn hàng từ giỏ hàng vào lịch sử với timestamp hiện tại
    fun saveOrderHistoryFromCart(items: List<CartItem>) = viewModelScope.launch {
        val historyItems = items.map {
            OrderHistoryItem(
                userId = userId,
                name = it.name,
                price = it.price,
                quantity = it.quantity,
                imageUrl = it.imageUrl,
                timestamp = System.currentTimeMillis()
            )
        }
        orderHistoryRepository.insertAll(historyItems)
    }

    override fun onCleared() {
        super.onCleared()
        allItems.removeObserver(cartObserver)
    }
}
