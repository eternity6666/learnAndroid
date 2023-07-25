package com.yzh.demoapp.yapp.loan

sealed class LoanType(
    val name: String,
    val calculator: (Double, Double, Int) -> List<Triple<Double, Double, Double>>,
) {
    object SamePrincipalSameInterest : LoanType(
        name = "等本等息",
        calculator = { loanAmount, interestRate, numberOfLoad ->
            val totalInterest = loanAmount * interestRate * 0.01 * numberOfLoad / 12
            val result = mutableListOf<Triple<Double, Double, Double>>()
            for (i in 1..numberOfLoad) {
                val principal = loanAmount / numberOfLoad
                val interest = totalInterest / numberOfLoad
                val principalAndInterest = principal + interest
                result.add(Triple(principalAndInterest, principal, interest))
            }
            result
        }
    )

    object SameSumOfPrincipalAndInterest : LoanType(
        name = "等额本息",
        calculator = { loanAmount, interestRate, numberOfLoad ->
            emptyList()
        }
    )

    object SamePrincipal : LoanType(
        name = "等额本金",
        calculator = { loanAmount, interestRate, numberOfLoad ->
            emptyList()
        }
    )

    object FirstInterestThenPrincipal : LoanType(
        name = "先息后本",
        calculator = { loanAmount, interestRate, numberOfLoad ->
            emptyList()
        }
    )
}
