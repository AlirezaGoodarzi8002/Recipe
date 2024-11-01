package com.security_wave.recipe.data.model

import com.google.gson.annotations.SerializedName

data class RecipeDetail(
    @SerializedName("meals")
    val meals: List<Meal>
)

data class Meal(
    @SerializedName("idMeal")
    val idMeal: String,
    @SerializedName("strArea")
    val strArea: String,
    @SerializedName("strCategory")
    val strCategory: String,
    @SerializedName("strDrinkAlternate")
    val strDrinkAlternate: Any,
    @SerializedName("strInstructions")
    val strInstructions: String,
    @SerializedName("strMeal")
    val strMeal: String,
    @SerializedName("strMealThumb")
    val strMealThumb: String,
    @SerializedName("strTags")
    val strTags: String
)