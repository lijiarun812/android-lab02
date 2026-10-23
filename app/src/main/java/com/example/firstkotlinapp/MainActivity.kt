package com.example.firstkotlinapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                var result by remember { mutableStateOf("等待请求") }
                var busy by remember { mutableStateOf(false) }

                fun request(address: String) {
                    busy = true
                    result = "请求中：$address"
                    Thread {
                        val response = fetch(address)
                        runOnUiThread {
                            result = response
                            busy = false
                        }
                    }.start()
                }

                InspectionScreen(
                    inspector = "李佳润",
                    room = "A203",
                    total = 10,
                    checked = 3,
                    result = result,
                    busy = busy,
                    onRequest = { address -> request(address) }
                )
            }
        }
    }
}