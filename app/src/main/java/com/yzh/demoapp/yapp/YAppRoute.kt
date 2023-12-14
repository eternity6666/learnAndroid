package com.yzh.demoapp.yapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.yzh.demoapp.compose.randomColor
import com.yzh.demoapp.yapp.applist.AppListPage
import com.yzh.demoapp.yapp.drawboard.DrawBoardPage
import com.yzh.demoapp.yapp.fund.FundPage
import com.yzh.demoapp.yapp.loan.LoanCalculatorPage
import com.yzh.demoapp.yapp.wallet.YWalletPage
import com.yzh.demoapp.yapp.weather.YWeatherPage

sealed class YAppRoute(
    val page: @Composable () -> Unit,
    val name: String = "",
    val bgColor: Color = randomColor(),
) {
    data object Weather : YAppRoute(page = { YWeatherPage() })
    data object LoanCalculator : YAppRoute(page = { LoanCalculatorPage() })
    data object Fund : YAppRoute(page = { FundPage() })
    data object DrawBoard : YAppRoute(page = { DrawBoardPage() })
    data object AppList : YAppRoute(page = { AppListPage() })
    data object Wallet: YAppRoute(page = { YWalletPage() })

    val routeName: String
        get() = this.javaClass.simpleName
}

internal val yAppSupportList = listOf(
    YAppRoute.Weather,
    YAppRoute.LoanCalculator,
    YAppRoute.DrawBoard,
    YAppRoute.Wallet,
    YAppRoute.AppList
)