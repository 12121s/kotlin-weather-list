package com.illis.weatherlist.data.source.remote.interceptor

import com.illis.weatherlist.WeatherListApplication
import com.illis.weatherlist.const.ServerConsts
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.io.InputStream
import kotlin.math.min

class MockInterceptor(private val filterList: List<String>) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val responseString = getResponseStringFromUri(chain.request())
        return if (ServerConsts.TEST_MODE && responseString.isNotEmpty())
            chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    responseString.toByteArray()
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
                .build()
        else chain.proceed(chain.request())
    }

    private fun getResponseStringFromUri(request: Request): String {
        val uri: String = getAPINameFromURL(request.url.toUri().toString())
        var responseString = ""

        if (filterList.isNotEmpty()) {
            for (filter in filterList) {
                val data = filter.split("\\|").toTypedArray()
                if (request.method == data[0] && uri == data[1]) {
                    responseString = getJsonStringFromFile(getJsonFileName(filter))
                }
            }
        } else {
            responseString = getJsonStringFromFile(getJsonFileName(uri))
        }
        return responseString;
    }

    private fun getJsonFileName(filter: String): String {
        var fileName = ""
        if (filter.contains(ServerConsts.MOCK_WEATHER))
            fileName = "Weathers.json"

        return fileName
    }

    private fun getAPINameFromURL(url: String?): String {
        if (url == null) return ""

        val startIndex = url.lastIndexOf('/') + 1
        val length = url.length

        var lastQMPos = url.lastIndexOf('?')
        if (lastQMPos == -1) lastQMPos = length

        var lastHashPos = url.lastIndexOf('#')
        if (lastHashPos == -1) lastHashPos = length

        val endIndex = min(lastQMPos, lastHashPos)
        return url.substring(startIndex, endIndex)
    }

    private fun getJsonStringFromFile(fileName: String): String {
        var json = ""
        try {
            val `is`: InputStream =
                WeatherListApplication.applicationContext().assets.open(fileName)
            val fileSize = `is`.available()
            val buffer = ByteArray(fileSize)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return json
    }
}