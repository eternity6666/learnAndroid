package com.yzh.demoapp.yapp.loan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoanCalculatorPage() {
    val loanAmount = remember {
        mutableStateOf(100000.00)
    }
    val interestRate = remember {
        mutableStateOf(3.50)
    }
    val numberOfLoad = remember {
        mutableStateOf(24)
    }
    val type = remember {
        mutableStateOf<LoanType>(LoanType.SamePrincipalSameInterest)
    }
    val result = type.value.calculator(loanAmount.value, interestRate.value, numberOfLoad.value)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = loanAmount.value.toString(),
            onValueChange = {
                loanAmount.value = it.toDoubleOrNull() ?: 0.0
            },
            modifier = Modifier.fillMaxWidth()
        )
        result.forEachIndexed { index, (principalAndInterest, principal, interest) ->
            if (index == 0) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "期数", modifier = Modifier.weight(1f))
                    Text(text = "月供(元)", modifier = Modifier.weight(1f))
                    Text(text = "本金(元)", modifier = Modifier.weight(1f))
                    Text(text = "利息(元)", modifier = Modifier.weight(1f))
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "第${index + 1}期", modifier = Modifier.weight(1f))
                Text(text = String.format("%.2f", principalAndInterest), modifier = Modifier.weight(1f))
                Text(text = String.format("%.2f", principal), modifier = Modifier.weight(1f))
                Text(text = String.format("%.2f", interest), modifier = Modifier.weight(1f))
            }
        }
    }
}