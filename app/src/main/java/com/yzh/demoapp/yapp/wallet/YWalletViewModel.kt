package com.yzh.demoapp.yapp.wallet

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class YWalletViewModel : ViewModel() {
    private val _itemList: MutableStateFlow<List<WalletItem>> = MutableStateFlow(emptyList())
    val itemList: Flow<List<WalletItem>> = _itemList

    fun addItem(item: WalletItem) {
        _itemList.value = buildList {
            addAll(_itemList.value)
            add(item)
        }
    }

}