package com.example.coffeeshop.util

/**
 * Inspired by https://developer.android.com/topic/libraries/architecture/guide.html#addendum.
 *
 * This represents a simple resource from the network that our services will return
 * to indicate success or failure.
 */
class Resource<out T> private constructor(val status: Status,
                                          val data: T? = null,
                                          val error: Exception? = null) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    fun isSuccessfulWithData(): Boolean = status == Status.SUCCESS && data != null

    companion object {
        fun <T> success(data: T) = Resource(Status.SUCCESS, data)

        fun <T> loading(data: T? = null) = Resource(Status.LOADING, data)

        fun <T> error(error: Exception?, data: T? = null) = Resource(Status.ERROR, data, error)
    }
}
