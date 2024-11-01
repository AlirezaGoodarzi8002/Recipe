package com.security_wave.recipe.data.model

import com.google.gson.annotations.SerializedName

data class RecipeMeal(
    @SerializedName("meals")
    val recipes: List<Recipe>
)

data class Recipe(
    @SerializedName("idMeal")
    val id: String,
    @SerializedName("strMeal")
    val name: String,
    @SerializedName("strMealThumb")
    val image: String
) {
    val description: String get() = "This is a short description about the recipe. The back-end did not completed this part yet.  :)"
}