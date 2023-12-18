package com.yzh.demoapp.yapp.wallet

import java.util.Date
import java.util.UUID

data class WalletItemData(
    val money: Double,
    val createDate: Date,
    val lastEditDate: Date,
) {
    val id: UUID = UUID.randomUUID()
}

data class WalletItem(
    val name: String = "",
    val dataList: List<WalletItemData> = emptyList(),
    val type: WalletType = WalletType.Asset,
) {
    val id: UUID = UUID.randomUUID()
}

sealed class WalletType {
    data object Debt : WalletType()
    data object Asset : WalletType()
}