package com.security_wave.recipe.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.security_wave.recipe.R
import com.security_wave.recipe.data.model.Category
import com.security_wave.recipe.data.model.ResultWrapper
import com.security_wave.recipe.data.repository.RecipeRepository
import com.security_wave.recipe.utils.getString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: RecipeRepository, application: Application
) : AndroidViewModel(application) {

    private val _categories = MutableLiveData<CategoryUiState<List<Category>>>()
    val categories: LiveData<CategoryUiState<List<Category>>> get() = _categories

    fun fetchCategories() {
        viewModelScope.launch {
            _categories.value = CategoryUiState.Loading
            val resultUiState = when (val categories = repository.getCategories()) {
                is ResultWrapper.Success -> CategoryUiState.Success(categories.value.categories)
                is ResultWrapper.Error.HttpError ->
                    CategoryUiState.Error(getString(R.string.http_error_message))

                is ResultWrapper.Error.NetworkError ->
                    CategoryUiState.Error(getString(R.string.network_error_message))

                is ResultWrapper.Error.UnknownError ->
                    CategoryUiState.Error(getString(R.string.unknown_error_message))
            }
            _categories.value = resultUiState
        }
    }

    sealed class CategoryUiState<out T> {
        data object Loading : CategoryUiState<Nothing>()
        data class Success(val categories: List<Category>) : CategoryUiState<List<Category>>()
        data class Error(val message: String) : CategoryUiState<Nothing>()
    }

}