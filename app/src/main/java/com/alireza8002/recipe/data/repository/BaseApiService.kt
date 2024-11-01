package com.alireza8002.recipe.data.repository

import android.content.Context
import androidx.annotation.StringRes
import com.alireza8002.recipe.R
import com.alireza8002.recipe.data.model.ResultWrapper
import kotlinx.coroutines.delay
import retrofit2.Response
import java.io.IOException

abstract class BaseApiService(private val context: Context) {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ResultWrapper<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                // delay for visiting screen
                delay(1000L)
                val body = response.body()
                if (body != null) ResultWrapper.Success(body)
                else ResultWrapper.Error.UnknownError(getString(R.string.response_is_null))
            } else ResultWrapper.Error.HttpError(response.code(), response.message())
        } catch (e: IOException) {
            e.printStackTrace()
            ResultWrapper.Error.NetworkError(getString(R.string.check_your_internet_connection))
        } catch (e: Exception) {
            e.printStackTrace()
            ResultWrapper.Error.UnknownError(
                e.localizedMessage ?: getString(R.string.unexpected_error)
            )
        }
    }

    private fun getString(@StringRes resId: Int) = context.getString(resId)
}
