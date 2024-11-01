package com.alireza8002.recipe.ui.recipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alireza8002.recipe.R
import com.alireza8002.recipe.data.model.Recipe
import com.alireza8002.recipe.data.model.ResultWrapper
import com.alireza8002.recipe.data.repository.RecipeRepository
import com.alireza8002.recipe.utils.UiState
import com.alireza8002.recipe.utils.getString
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
        _recipes.value = UiState.Loading
        viewModelScope.launch {
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
            } ?: run { _recipes.value = UiState.Error(getString(R.string.category_id_is_missing)) }
        }
    }

}