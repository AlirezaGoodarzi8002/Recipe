package com.security_wave.recipe.ui.recipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.security_wave.recipe.R
import com.security_wave.recipe.data.model.Recipe
import com.security_wave.recipe.data.model.ResultWrapper
import com.security_wave.recipe.data.repository.RecipeRepository
import com.security_wave.recipe.utils.getString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val CATEGORY_NAME_KEY = "CATEGORY_NAME"

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository,
    application: Application,
    savedStateHandle: SavedStateHandle

) : AndroidViewModel(application) {

    private val _recipes = MutableLiveData<RecipeUiState<List<Recipe>>>()
    val recipes: LiveData<RecipeUiState<List<Recipe>>> = _recipes

    private val categoryName: String? = savedStateHandle[CATEGORY_NAME_KEY]

    fun fetchRecipes() {
        viewModelScope.launch {
            _recipes.value = RecipeUiState.Loading

            categoryName?.let { name ->
                _recipes.value = when (val recipes = repository.getRecipes(name)) {
                    is ResultWrapper.Success -> RecipeUiState.Success(recipes.value.recipes)
                    is ResultWrapper.Error.HttpError ->
                        RecipeUiState.Error(getString(R.string.http_error_message))

                    is ResultWrapper.Error.NetworkError ->
                        RecipeUiState.Error(getString(R.string.network_error_message))

                    is ResultWrapper.Error.UnknownError ->
                        RecipeUiState.Error(getString(R.string.unknown_error_message))
                }
            } ?: run {
                _recipes.value = RecipeUiState.Error(getString(R.string.category_id_is_missing))
            }
        }
    }

    sealed class RecipeUiState<out T> {
        data object Loading : RecipeUiState<Nothing>()
        data class Success(val recipes: List<Recipe>) : RecipeUiState<List<Recipe>>()
        data class Error(val message: String) : RecipeUiState<Nothing>()
    }

}