package com.yzh.demoapp.yapp.wallet

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID

class YWalletViewModel : ViewModel() {
    private val _itemList: MutableStateFlow<List<WalletItem>> = MutableStateFlow(emptyList())
    val itemList: Flow<List<WalletItem>> = _itemList

    fun dispatchAction(action: YWalletAction) {
        val (isChange, newList) = when (action) {
            is YWalletAction.AddItem -> addItem(action.item)
            is YWalletAction.DeleteItem -> deleteItem(action.id)
            else -> {
                false to _itemList.value
            }
        }
        if (isChange) {
            _itemList.value = newList
        }
    }

    private fun addItem(item: WalletItem): Pair<Boolean, List<WalletItem>> {
        val newList = buildList {
            addAll(_itemList.value)
            add(item)
        }
        return (true to newList)
    }

    private fun deleteItem(id: UUID): Pair<Boolean, List<WalletItem>> {
        val newList = _itemList.value.filterNot { it.id == id }
        return (true to newList)
    }


}