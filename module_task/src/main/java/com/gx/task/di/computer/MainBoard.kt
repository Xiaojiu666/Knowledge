package com.gx.task.di.computer


class MainBoard private constructor() {

    companion object {
        fun create(): MainBoard? {
            return InstanceHolder().INSTANCE
        }

        private class InstanceHolder() {
            val INSTANCE = MainBoard()
        }
    }

    fun getMainBoardName() = "ASUS"


//    fun getMainBoardNameElectric() = "ASUS" + electric.getElectric()
}