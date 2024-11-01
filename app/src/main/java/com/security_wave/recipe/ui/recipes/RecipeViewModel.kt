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
import com.security_wave.recipe.utils.UiState
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

    private val _recipes = MutableLiveData<UiState<List<Recipe>>>()
    val recipes: LiveData<UiState<List<Recipe>>> = _recipes

    private val categoryName: String? = savedStateHandle[CATEGORY_NAME_KEY]

    fun fetchRecipes() {
        viewModelScope.launch {
            _recipes.value = UiState.Loading

            categoryName?.let { name ->
                _recipes.value = when (val recipes = repository.getRecipes(name)) {
                    is ResultWrapper.Success -> UiState.Success(recipes.value.recipes)
                    is ResultWrapper.Error.HttpError ->
                        UiState.Error(getString(R.string.http_error_message))

                    is ResultWrapper.Error.NetworkError ->
                        UiState.Error(getString(R.string.network_error_message))

                    is ResultWrapper.Error.UnknownError ->
                        UiState.Error(getString(R.string.unknown_error_message))
                }
            } ?: run {
                _recipes.value = UiState.Error(getString(R.string.category_id_is_missing))
            }
        }
    }

}