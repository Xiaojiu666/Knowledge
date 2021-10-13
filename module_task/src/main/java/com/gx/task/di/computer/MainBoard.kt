package com.gx.task.di.computer


class MainBoard private constructor(var electric: Electric) {

    companion object {
        fun create(electric: Electric): MainBoard? {
            return InstanceHolder(electric).INSTANCE
        }

        private class InstanceHolder(electric: Electric) {
            val INSTANCE = MainBoard(electric)
        }
    }

    fun getMainBoardName() = "ASUS"


    fun getMainBoardNameElectric() = "ASUS" + electric.getElectric()
}