package com.example.food_project.database.repository

import CartDao
import androidx.lifecycle.LiveData
import com.example.food_project.model.CartItem


class CartRepository(private val cartDao: CartDao) {

    fun getAllItems(uid: String): LiveData<List<CartItem>> {
        return cartDao.getAllItems(uid)
    }

    suspend fun clearCart(uid: String) {
        cartDao.clearCart(uid)
    }

    suspend fun insert(item: CartItem) = cartDao.insert(item)
    suspend fun update(item: CartItem) = cartDao.update(item)
    suspend fun delete(item: CartItem) = cartDao.delete(item)
}

