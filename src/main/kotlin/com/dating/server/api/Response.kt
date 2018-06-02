package com.dating.server.api


/**
 * Created by daishin@gmail.com
 * 12.05.2016.
 */
data class Response<T>(val errorCode: ErrorCode = ErrorCode.OK,
                       val result: T? = null,
                       val apiClientVersionCode: Int=22,
                       val isLastPage: Boolean = false)

enum class ErrorCode {
    OK,
    USER_NOT_FOUND,
    UN_EXPECTED,
    USER_BANED,
    MAIN_PHOTO_CANT_BE_DELETED
}




