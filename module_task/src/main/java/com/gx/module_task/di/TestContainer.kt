package com.gx.module_task.di

import dagger.Component

@Component
interface TestContainer {
    fun repository(): TestRepository
}