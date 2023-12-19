package com.yzh.demoapp.yapp.wallet

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun YWalletPage() {
    val viewModel: YWalletViewModel = viewModel()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "添加") },
                icon = { Icon(imageVector = Icons.Filled.Add, contentDescription = "") },
                onClick = { showBottomSheet = true },
            )
        }
    ) {
        Text(text = "123")
        if (showBottomSheet) {
            AddWalletItem(viewModel = viewModel) {
                showBottomSheet = false
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWalletItem(
    viewModel: YWalletViewModel,
    onDismiss: () -> Unit,
) {
    var name by remember {
        mutableStateOf("")
    }
    val dataList = remember {
        mutableStateListOf<WalletItemData>()
    }
    var type: WalletType by remember {
        mutableStateOf(WalletType.Asset)
    }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 20.dp)
        ) {
            TextField(value = name, onValueChange = { name = it })
            Switch(checked = type == WalletType.Asset, onCheckedChange = {
                type = if (it) WalletType.Asset else WalletType.Debt
            })
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.addItem(WalletItem(name, dataList, type))
                    onDismiss()
                },
            ) {
                Text(text = "添加")
            }
        }
    }
}