package com.yzh.demoapp.yapp.loan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun LoanCalculatorPage() {
    val originPrice = remember {
        mutableStateOf(0.00)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        TextField(value = originPrice.value.toString(), onValueChange = {
            originPrice.value = it.toDoubleOrNull() ?: 0.0
        })
    }
}