package com.alireza8002.recipe.data.model

import com.google.gson.annotations.SerializedName

data class RecipeDetail(
    @SerializedName("meals")
    val meals: List<Meal>
)

data class Meal(
    @SerializedName("idMeal")
    val id: String,
    @SerializedName("strArea")
    val area: String,
    @SerializedName("strCategory")
    val category: String,
    @SerializedName("strInstructions")
    val instructions: String,
    @SerializedName("strMeal")
    val meal: String,
    @SerializedName("strMealThumb")
    val mealThumb: String
)