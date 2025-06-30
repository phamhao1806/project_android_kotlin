package com.example.food_project.database.repository

import androidx.lifecycle.LiveData
import com.example.food_project.database.dao.OrderHistoryDao
import com.example.food_project.model.OrderHistoryItem

class OrderHistoryRepository(private val dao: OrderHistoryDao) {

    fun getAllOrders(uid: String): LiveData<List<OrderHistoryItem>> {
        return dao.getAllOrders(uid)
    }

    suspend fun insertAll(items: List<OrderHistoryItem>) = dao.insertAll(items)

    suspend fun clearHistory(uid: String) = dao.clearHistory(uid)
}
