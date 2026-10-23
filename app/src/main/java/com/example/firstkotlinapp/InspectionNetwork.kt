package com.example.firstkotlinapp

import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

private val client = OkHttpClient.Builder()
    .connectTimeout(8, TimeUnit.SECONDS)
    .readTimeout(8, TimeUnit.SECONDS)
    .build()

fun fetch(address: String): String = try {
    val requestReal = Request.Builder().url(if(address.startsWith("http")) address else "https://httpbin.org/get?room=$address").build()

    client.newCall(requestReal).execute().use { response ->
        "$address\nHTTP ${response.code}\n${response.body?.string()?.take(800)}"
    }
} catch (e: Exception) {
    "请求失败：${e.javaClass.simpleName}\n${e.message}"
}