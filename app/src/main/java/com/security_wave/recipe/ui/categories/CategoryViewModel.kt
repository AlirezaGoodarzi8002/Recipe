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
import com.security_wave.recipe.utils.UiState
import com.security_wave.recipe.utils.getString
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
        viewModelScope.launch {
            _categories.value = UiState.Loading
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