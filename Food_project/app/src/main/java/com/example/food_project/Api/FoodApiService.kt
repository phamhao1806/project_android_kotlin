package com.example.food_project.Api
import com.example.food_project.model.MenuItem
import retrofit2.Call
import retrofit2.http.GET

interface FoodApiService {
    @GET("menuItems")
    fun getAllMenuItems(): Call<List<MenuItem>>
}
