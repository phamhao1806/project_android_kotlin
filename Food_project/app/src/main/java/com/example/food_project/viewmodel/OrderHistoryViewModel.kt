package com.example.food_project.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.food_project.database.repository.OrderHistoryRepository
import com.example.food_project.model.OrderHistoryItem
import kotlinx.coroutines.launch

class OrderHistoryViewModel(application: Application, userId: String) : AndroidViewModel(application) {
    private val repository: OrderHistoryRepository
    val allOrders: LiveData<List<OrderHistoryItem>>

    init {
        val dao = AppDatabase.getDatabase(application).orderHistoryDao()
        repository = OrderHistoryRepository(dao)
        allOrders = repository.getAllOrders(userId)
    }

    fun insertAllOrders(items: List<OrderHistoryItem>) {
        viewModelScope.launch {
            repository.insertAll(items)
        }
    }

}
