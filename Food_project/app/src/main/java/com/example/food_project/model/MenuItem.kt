package com.example.food_project.model

import java.io.Serializable

data class MenuItem(
    val foodName: String?=null,
    val foodPrice: String?=null,
    val foodMota: String?=null,
    val foodImage: String?=null,
    val foodNguyenlieu: String?=null
): Serializable
