package com.yzh.demoapp.aacell.model

sealed class RoomType(val title: String) {
    data object Unknown : RoomType("")
    data object NotFound : RoomType("新的电池舱")
    sealed class Found(title: String) : RoomType(title) {
        data object NeedPassword : Found("需要进入密码")
        data object NoPassword : Found("电池舱")
    }

    companion object {
        fun match(title: String): RoomType {
            return when (title) {
                NotFound.title -> NotFound
                Found.NeedPassword.title -> Found.NeedPassword
                Found.NoPassword.title -> Found.NoPassword
                else -> Unknown
            }
        }
    }
}