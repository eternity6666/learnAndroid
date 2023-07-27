package com.yzh.demoapp.yapp.loan

import kotlin.math.pow

sealed class LoanType(
    val name: String,
    val calculator: (Double, Double, Int) -> List<Triple<Double, Double, Double>>,
) {
    data object SamePrincipalSameInterest : LoanType(
        name = "等本等息",
        calculator = { loanAmount, interestYearRate, numberOfLoad ->
            val result = mutableListOf<Triple<Double, Double, Double>>()
            if (numberOfLoad > 0) {
                val interestMonthRate = interestYearRate * 0.01 / 12
                for (i in 1..numberOfLoad) {
                    val principal = loanAmount / numberOfLoad
                    val interest = loanAmount * interestMonthRate
                    val principalAndInterest = principal + interest
                    result.add(Triple(principalAndInterest, principal, interest))
                }
            }
            result
        }
    )

    data object SameSumOfPrincipalAndInterest : LoanType(
        name = "等额本息",
        calculator = { loanAmount, interestYearRate, numberOfLoad ->
            val result = mutableListOf<Triple<Double, Double, Double>>()
            if (numberOfLoad > 0) {
                val interestMonthRate = interestYearRate * 0.01 / 12
                var remainingLoanAmount= loanAmount
                for (i in 1..numberOfLoad) {
                    val onePlusRatePowN = (1 + interestMonthRate).pow(numberOfLoad)
                    val total = loanAmount * (interestMonthRate * onePlusRatePowN) / (onePlusRatePowN - 1)
                    val interest = remainingLoanAmount * interestMonthRate
                    val principal = total - interest
                    remainingLoanAmount -= principal
                    result.add(Triple(total, principal, interest))
                }
            }
            result
        }
    )

    data object SamePrincipal : LoanType(
        name = "等额本金",
        calculator = { loanAmount, interestYearRate, numberOfLoad ->
            val result = mutableListOf<Triple<Double, Double, Double>>()
            if (numberOfLoad > 0) {
                val interestMonthRate = interestYearRate * 0.01 / 12
                for (i in 1..numberOfLoad) {
                    val principal = loanAmount / numberOfLoad
                    val interest = loanAmount * (numberOfLoad - i + 1) * interestMonthRate / numberOfLoad
                    result.add(Triple(principal + interest, principal, interest))
                }
            }
            result
        }
    )

    data object FirstInterestThenPrincipal : LoanType(
        name = "先息后本",
        calculator = { loanAmount, interestYearRate, numberOfLoad ->
            val result = mutableListOf<Triple<Double, Double, Double>>()
            if (numberOfLoad > 0) {
                val interestMonthRate = interestYearRate * 0.01 / 12
                for (index in 1..numberOfLoad) {
                    val principal = if (index == numberOfLoad) {
                        loanAmount
                    } else {
                        0.0
                    }
                    val interest = loanAmount * interestMonthRate
                    result.add(Triple(principal + interest, principal, interest))
                }
            }
            result
        }
    )
}
