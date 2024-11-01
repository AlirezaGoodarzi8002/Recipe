package com.security_wave.recipe.data.repository

import android.content.Context
import com.security_wave.recipe.data.api.RecipeApi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val api: RecipeApi, @ApplicationContext private val context: Context
) : BaseApiService(context) {

    suspend fun getCategories() = safeApiCall { api.getCategories() }
    suspend fun getRecipes(category: String) = safeApiCall { api.getRecipes(category) }
    suspend fun getRecipeById(mealId: String) = safeApiCall { api.getRecipeById(mealId) }

}