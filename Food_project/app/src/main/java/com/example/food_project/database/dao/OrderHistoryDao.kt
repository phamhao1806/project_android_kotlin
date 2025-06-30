package com.example.food_project.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.food_project.model.OrderHistoryItem

@Dao
interface OrderHistoryDao {

    @Query("SELECT * FROM order_history WHERE userId = :uid ORDER BY timestamp DESC")
    fun getAllOrders(uid: String): LiveData<List<OrderHistoryItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<OrderHistoryItem>)

    @Query("DELETE FROM order_history WHERE userId = :uid")
    suspend fun clearHistory(uid: String)

}
