package com.alireza8002.recipe.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alireza8002.recipe.R
import com.alireza8002.recipe.data.model.Category
import com.alireza8002.recipe.data.model.ResultWrapper
import com.alireza8002.recipe.data.repository.RecipeRepository
import com.alireza8002.recipe.utils.UiState
import com.alireza8002.recipe.utils.getString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: RecipeRepository, application: Application
) : AndroidViewModel(application) {

    private val _categories = MutableLiveData<UiState<List<Category>>>()
    val categories: LiveData<UiState<List<Category>>> get() = _categories

    fun fetchCategories() {
        _categories.value = UiState.Loading
        viewModelScope.launch {
            val resultUiState = when (val categories = repository.getCategories()) {
                is ResultWrapper.Success -> UiState.Success(categories.value.categories)
                is ResultWrapper.Error.HttpError ->
                    UiState.Error(getString(R.string.http_error_message))

                is ResultWrapper.Error.NetworkError ->
                    UiState.Error(getString(R.string.network_error_message))

                is ResultWrapper.Error.UnknownError ->
                    UiState.Error(getString(R.string.unknown_error_message))
            }
            _categories.value = resultUiState
        }
    }

}