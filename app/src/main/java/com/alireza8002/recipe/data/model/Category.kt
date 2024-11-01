package com.alireza8002.recipe.data.model

import com.google.gson.annotations.SerializedName

data class CategoryMeal(
    @SerializedName("meals")
    val categories: List<Category>
)

data class Category(
    @SerializedName("strCategory")
    val categoryName: String
)
