/*
 * CopyRight (C) 2023 Tencent. All rights reserved.
 */
package com.yzh.demoapp.yapp.wallet

import java.util.UUID

/**
 * @author baronyang@tencent.com
 * @since 2023/12/26 16:16
 */
sealed class YWalletAction {
    class AddItem(val item: WalletItem) : YWalletAction()
    class DeleteItem(val id: UUID) : YWalletAction()
    class ChangeSort(val newSortList: List<UUID>) : YWalletAction()
    class UpdateItem(val item: WalletItem) : YWalletAction()
}