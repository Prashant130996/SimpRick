package com.example.rickmorty.repo

import retrofit2.Response
import java.lang.Exception

data class SimpleResponse<T>(
    val status: Status,
    val response: Response<T>?,
    val exception: Exception?
) {

    companion object {
        fun <T> success(data: Response<T>): SimpleResponse<T> {
            return SimpleResponse(Status.Success, data, null)
        }

        fun <T> failure(exception: Exception?): SimpleResponse<T> {
            return SimpleResponse(Status.Failure, null, exception)
        }
    }

    sealed class Status {
        object Success : Status()
        object Failure : Status()
    }

    val failed: Boolean
        get() = this.status == Status.Failure

    val isSuccessful: Boolean
        get() = !failed && this.response?.isSuccessful == true

    val body: T
        get() = this.response!!.body()!!
}