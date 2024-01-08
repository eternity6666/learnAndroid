package com.yzh.demoapp.yapp.loan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
    val loanType = remember {
        mutableStateOf(supportLoanTypeList.first())
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
    ) {
        item {
            TextField(
                value = loanAmount.value.toString(),
                onValueChange = {
                    loanAmount.value = it.toDoubleOrNull() ?: 0.0
                },
                label = { Text(text = "贷款金额") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = interestRate.value.toString(),
                onValueChange = {
                    interestRate.value = it.toDoubleOrNull() ?: 0.0
                },
                label = { Text(text = "利率") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = numberOfLoad.value.toString(),
                onValueChange = {
                    numberOfLoad.value = it.toIntOrNull()?.takeIf { number -> number > 0 } ?: 0
                },
                label = { Text(text = "分期数") },
                modifier = Modifier.fillMaxWidth()
            )
            LoanTypeSelector(loanTypeState = loanType)
        }
        LoanCalculatorResult(
            loanType = loanType.value,
            loanAmount = loanAmount.value,
            interestRate = interestRate.value,
            numberOfLoad = numberOfLoad.value
        )
    }
}

@Composable
private fun LoanTypeSelector(
    loanTypeState: MutableState<LoanType>,
) {
    require(supportLoanTypeList.isNotEmpty())
    require(supportLoanTypeList.contains(loanTypeState.value))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "还款方式")
        Box {
            val isExpanded = remember {
                mutableStateOf(false)
            }
            Button(onClick = { isExpanded.value = true }) {
                Text(text = loanTypeState.value.name)
            }
            DropdownMenu(
                expanded = isExpanded.value,
                onDismissRequest = { isExpanded.value = false },
            ) {
                supportLoanTypeList.forEach { loanType ->
                    DropdownMenuItem(
                        text = {
                            Text(text = loanType.name)
                        },
                        onClick = {
                            loanTypeState.value = loanType
                            isExpanded.value = false
                        }
                    )
                }
            }
        }
    }
}

private val supportLoanTypeList = listOf(
    LoanType.SamePrincipalSameInterest,
    LoanType.SameSumOfPrincipalAndInterest,
    LoanType.SamePrincipal,
    LoanType.FirstInterestThenPrincipal
)

private fun LazyListScope.LoanCalculatorResult(
    loanType: LoanType,
    loanAmount: Double,
    interestRate: Double,
    numberOfLoad: Int,
) {
    val result = loanType.calculator(
        loanAmount, interestRate, numberOfLoad
    )
    if (result.isNotEmpty()) {
        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                LoanLabelContent(label = "贷款总额", content = "${String.format("%.2f", loanAmount)}元")
                Spacer(modifier = Modifier.width(16.dp))
                LoanLabelContent(
                    label = "利息总额",
                    content = "${String.format("%.2f", result.sumOf { it.third })}元"
                )
            }
        }
        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                LoanTableHeadItem(text = "期数")
                LoanTableHeadItem(text = "月供(元)")
                LoanTableHeadItem(text = "本金(元)")
                LoanTableHeadItem(text = "利息(元)")
            }
            Divider()
        }
    }
    itemsIndexed(result) { index, (principalAndInterest, principal, interest) ->
        Row(modifier = Modifier.fillMaxWidth()) {
            LoanTableContentItem(text = "第${index + 1}期")
            LoanTableContentItem(text = String.format("%.2f", principalAndInterest))
            LoanTableContentItem(text = String.format("%.2f", principal))
            LoanTableContentItem(text = String.format("%.2f", interest))
        }
    }
}

@Composable
private fun RowScope.LoanTableHeadItem(
    text: String,
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.weight(1f),
    )
}

@Composable
private fun RowScope.LoanTableContentItem(
    text: String,
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        modifier = Modifier.weight(1f),
    )
}

@Composable
private fun RowScope.LoanLabelContent(
    label: String,
    content: String,
) {
    Row(
        modifier = Modifier.weight(1f),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label)
        Text(text = content)
    }
}

@Preview
@Composable
private fun LoanCalculatorPage_Preview() {
    LoanCalculatorPage()
}