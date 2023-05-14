package com.illis.weatherlist.data.source.remote.interceptor

import com.illis.weatherlist.const.ServerConsts
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.net.SocketTimeoutException


class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        try {
            val response = chain.proceed(request)
            val bodyString = response.body!!.string()

            return response.newBuilder()
                .body(bodyString.toResponseBody(response.body?.contentType()))
                .build()

        } catch (e: Exception) {
            var msg = ""
            var interceptorCode = 0
            when (e) {
                is SocketTimeoutException -> {
                    msg = "Socket timeout error"
                    interceptorCode = ServerConsts.SERVER_SOCKET_ERROR
                }
                else -> {
                    msg = "unknown error ${e.message}"
                    interceptorCode = ServerConsts.UNKNOWN_SERVER_ERROR
                }
            }
            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_2)
                .code(interceptorCode)
                .message(msg)
                .body("{${e}}".toResponseBody(null)).build()
        }
    }

}