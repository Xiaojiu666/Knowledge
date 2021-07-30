package com.gx.task.di

import javax.inject.Inject

class TestRemoteDataSource @Inject constructor()  {

    fun returnName():String{
        return "TestRemoteDataSource"
    }
}