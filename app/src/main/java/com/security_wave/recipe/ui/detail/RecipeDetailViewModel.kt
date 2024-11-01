package com.security_wave.recipe.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.security_wave.recipe.R
import com.security_wave.recipe.data.model.Meal
import com.security_wave.recipe.data.model.ResultWrapper
import com.security_wave.recipe.data.repository.RecipeRepository
import com.security_wave.recipe.utils.UiState
import com.security_wave.recipe.utils.getString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val RECIPE_ID_KEY = "recipeId"

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val repository: RecipeRepository,
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val _recipeUiState = MutableLiveData<UiState<Meal>>()
    val recipeUiState: LiveData<UiState<Meal>> get() = _recipeUiState

    private val recipeId: String? = savedStateHandle[RECIPE_ID_KEY]

    fun fetchRecipe() {
        _recipeUiState.value = UiState.Loading
        viewModelScope.launch {
            recipeId?.let {
                _recipeUiState.value = when (val recipe = repository.getRecipeById(recipeId)) {
                    is ResultWrapper.Success -> UiState.Success(recipe.value.meals.first())

                    is ResultWrapper.Error.HttpError ->
                        UiState.Error(getString(R.string.http_error_message))

                    is ResultWrapper.Error.NetworkError ->
                        UiState.Error(getString(R.string.network_error_message))

                    is ResultWrapper.Error.UnknownError ->
                        UiState.Error(getString(R.string.unknown_error_message))
                }
            } ?: run {
                _recipeUiState.value = UiState.Error(getString(R.string.recipe_id_is_missed))
            }
        }
    }
}
