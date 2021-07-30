package com.gx.task.di

import javax.inject.Inject

class TestRepository @Inject constructor(
    val testRemoteDataSource: TestRemoteDataSource,
    val testLocalDataSource: TestLocalDataSource
) {

    fun returnName():String{
        return "TestRepository"
    }
}