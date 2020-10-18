package com.sn.accountbooks.framework.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sn.accountbooks.framework.User

/**
 * Created by GuoXu on 2020/10/15 15:42.
 *
 */
class MyViewModel : ViewModel() {
    private val users: MutableLiveData<List<User>> by lazy {
        users.also {
            loadUsers()
        }
    }
    fun getUsers(): LiveData<List<User>> {
        return users
    }

    private fun loadUsers() {
        // Do an asynchronous operation to fetch users.
    }
}