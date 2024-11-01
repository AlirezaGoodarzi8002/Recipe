package com.alireza8002.recipe.data.api

import com.alireza8002.recipe.data.model.CategoryMeal
import com.alireza8002.recipe.data.model.RecipeDetail
import com.alireza8002.recipe.data.model.RecipeMeal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {
    @GET("list.php")
    suspend fun getCategories(@Query("c") listQuery: String = "list"): Response<CategoryMeal>

    @GET("filter.php")
    suspend fun getRecipes(@Query("c") nameToFilter: String): Response<RecipeMeal>

    @GET("lookup.php")
    suspend fun getRecipeById(@Query("i") mealId: String): Response<RecipeDetail>
}
