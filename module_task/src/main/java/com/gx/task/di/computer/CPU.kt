package com.gx.task.di.computer


class CPU  {

    companion object {
        fun create(): CPU? {
            return InstanceHolder().INSTANCE
        }

        private class InstanceHolder() {
            val INSTANCE = CPU()
        }
    }

    fun getCpuName() = "I9-10900X"

}