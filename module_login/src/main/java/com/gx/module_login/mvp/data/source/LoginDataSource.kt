package com.gx.module_login.mvp.data.source

interface LoginDataSource {

    fun login(userName: String, passWord: String)

    fun register(userName: String,passWord: String, verCode: Int)

    fun modifyPassWord(userName: String,passWord: String, verCode: Int)
}