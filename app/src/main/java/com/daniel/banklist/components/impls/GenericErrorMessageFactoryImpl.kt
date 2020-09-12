package com.daniel.banklist.components.impls

import android.content.Context
import com.daniel.banklist.components.exception.NetworkException
import com.daniel.banklist.components.interfaces.GenericErrorMessageFactory
import com.daniel.banklist.components.interfaces.NetworkExceptionMessageFactory
import com.daniel.banklist.models.vos.ReturnResult
import com.daniel.banklist.R
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class GenericErrorMessageFactoryImpl @Inject constructor(
    private val context: Context,
    private val networkExceptionMessageFactory: NetworkExceptionMessageFactory
) : GenericErrorMessageFactory {
    override fun getErrorMessage(throwable: Throwable): CharSequence {
        return getErrorMessage(throwable, 0)
    }

    override fun getErrorMessage(throwable: Throwable, defaultText: Int): CharSequence {
        return when (throwable) {
            is UnknownHostException -> networkExceptionMessageFactory.getErrorMessage(throwable)
            is SocketTimeoutException -> networkExceptionMessageFactory.getErrorMessage(throwable)
            is ConnectException -> networkExceptionMessageFactory.getErrorMessage(throwable)
            is NetworkException -> networkExceptionMessageFactory.getErrorMessage(throwable)
            else -> {
                return try {
                    context.getString(defaultText)
                } catch (e: Exception) {
                    throwable.message ?: context.getString(R.string.error_generic)
                }
            }
        }
    }

    override fun <T> getError(throwable: Throwable): ReturnResult<T> {
        return getError(throwable, 0)
    }

    override fun <T> getError(throwable: Throwable, defaultText: Int): ReturnResult<T> {
        return when (throwable) {
            is UnknownHostException -> ReturnResult.NetworkErrorResult
            is SocketTimeoutException -> ReturnResult.NetworkErrorResult
            is ConnectException -> ReturnResult.NetworkErrorResult
            is NetworkException -> getNetworkError(throwable)
            else -> ReturnResult.ErrorResult(getErrorMessage(throwable, defaultText))
        }
    }

    override fun <T> getLoginError(throwable: Throwable, defaultText: Int): ReturnResult<T> {
        return when (throwable) {
            is UnknownHostException -> ReturnResult.NetworkErrorResult
            is SocketTimeoutException -> ReturnResult.NetworkErrorResult
            is ConnectException -> ReturnResult.NetworkErrorResult
            is NetworkException -> ReturnResult.ErrorResult(getErrorMessage(throwable))
            else -> ReturnResult.ErrorResult(getErrorMessage(throwable, defaultText))
        }
    }

    private fun <T> getNetworkError(networkException: NetworkException): ReturnResult<T> {
        return when (networkException.errorCode) {
            301 -> ReturnResult.NewVersion
            401 -> ReturnResult.SessionExpired
            402 -> ReturnResult.PaymentOverdue
            422 -> ReturnResult.ValidationErrorResult(getErrorMessage(networkException))
            else -> ReturnResult.ErrorResult(getErrorMessage(networkException))
        }
    }

}