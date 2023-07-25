package com.yzh.demoapp.yapp.loan

sealed class LoanType(
    val name: String,
    val calculator: (Double, Double, Int) -> List<Double>
) {
    object SamePrincipalSameInterest : LoanType(
        name = "等本等息",
        calculator = { loanAmount, interest, numberOfLoad ->
            emptyList()
        }
    )

    object SameSumOfPrincipalAndInterest : LoanType(
        name = "等额本息",
        calculator = { loanAmount, interest, numberOfLoad ->
            emptyList()
        }
    )

    object SamePrincipal : LoanType(
        name = "等额本金",
        calculator = { loanAmount, interest, numberOfLoad ->
            emptyList()
        }
    )

    object FirstInterestThenPrincipal : LoanType(
        name = "先息后本",
        calculator = { loanAmount, interest, numberOfLoad ->
            emptyList()
        }
    )
}
