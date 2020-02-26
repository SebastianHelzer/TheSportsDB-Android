package com.seboba.remote

import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.util.*


class HttpInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (request.body() != null && Objects.requireNonNull(request.body())?.contentLength() ?: 0 > 0) {
            try {
                val copy = request.newBuilder().build()
                val buffer = Buffer()
                Objects.requireNonNull(copy.body())?.writeTo(buffer)
                val message = "\ncall body: ${buffer.clone().readUtf8()}\n"
                println(message)
//                Log.d("HttpInterceptor", message)
            } catch (e: Exception) {
                val message = e.message.toString()
                println(message)
//                Log.d("HttpInterceptor", message)
            }
        }
        val response = chain.proceed(request)
        return run {
            val responseBody = response.body()!!
            val source = responseBody.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()
            val message = "\ncall url: ${request.url()}\n\nresponse: ${buffer.clone().readUtf8()}\n"
            println(message)
//            Log.d("HttpInterceptor", message)
            response
        }
    }
}