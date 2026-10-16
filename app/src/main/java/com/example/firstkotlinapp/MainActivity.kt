package com.example.firstkotlinapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.firstkotlinapp.ui.theme.FirstKotlinAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstKotlinAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NetworkTestPage(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

private const val HTTPS_URL = "https://httpbin.org/get?room=A204"
private const val HTTP_URL = "http://httpbin.org/get?room=A203"

@Composable
fun NetworkTestPage(modifier: Modifier = Modifier) {
    var resultText by remember { mutableStateOf("点击按钮发起网络请求\n姓名：李佳润") }
    val scope = rememberCoroutineScope()

    fun request(urlStr: String): String {
        return try {
            val url = URL(urlStr)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            val code = conn.responseCode
            val reader = BufferedReader(InputStreamReader(conn.inputStream))
            val sb = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
            }
            reader.close()
            conn.disconnect()
            "状态码:$code\n${sb}"
        } catch (e: Exception) {
            "请求异常：${e.message}"
        }
    }

    Column(
        modifier = modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = resultText)
        Button(onClick = {
            scope.launch(Dispatchers.IO) {
                val res = request(HTTPS_URL)
                resultText = res
            }
        }) {
            Text("HTTPS 请求")
        }
        Button(onClick = {
            scope.launch(Dispatchers.IO) {
                val res = request(HTTP_URL)
                resultText = res
            }
        }) {
            Text("HTTP 请求")
        }
    }
}
