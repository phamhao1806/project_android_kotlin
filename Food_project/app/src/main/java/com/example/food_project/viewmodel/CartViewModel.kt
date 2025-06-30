package com.example.food_project.viewmodel
import AppDatabase
import com.example.food_project.database.repository.CartRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.food_project.model.CartItem
import kotlinx.coroutines.launch

class CartViewModel(application: Application, private val userId: String) : AndroidViewModel(application) {
    private val repository: CartRepository
    val allItems: LiveData<List<CartItem>>

    init {
        val cartDao = AppDatabase.getDatabase(application).cartDao()
        repository = CartRepository(cartDao)
        allItems = repository.getAllItems(userId)
    }

    fun addToCart(item: CartItem) = viewModelScope.launch {
        val itemWithUserId = item.copy(userId = userId)
        repository.insert(itemWithUserId)
    }

    fun updateItem(item: CartItem) = viewModelScope.launch {
        repository.update(item)
    }

    fun deleteItem(item: CartItem) = viewModelScope.launch {
        repository.delete(item)
    }
    fun clearCart() = viewModelScope.launch {
        repository.clearCart(userId)
    }
}
