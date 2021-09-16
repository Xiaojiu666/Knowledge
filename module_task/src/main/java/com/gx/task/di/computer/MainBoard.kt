package com.gx.task.di.computer


class MainBoard {
    companion object{
        fun create(): MainBoard? {
            return InstanceHolder.INSTANCE
        }

        private object InstanceHolder {
            val INSTANCE = MainBoard()
        }
    }

    fun getMainBoardName() = "ASUS"
}