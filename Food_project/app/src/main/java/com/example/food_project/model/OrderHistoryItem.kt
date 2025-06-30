package com.example.food_project.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_history")
data class OrderHistoryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val name: String,
    val price: String,
    val quantity: Int,
    val imageUrl: String,
    val userId: String,
    val timestamp: Long = System.currentTimeMillis()
)
