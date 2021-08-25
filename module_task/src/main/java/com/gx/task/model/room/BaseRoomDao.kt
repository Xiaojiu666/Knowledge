package com.gx.task.model.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gx.task.model.data.Task
import kotlinx.coroutines.flow.Flow

interface BaseRoomDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: T?): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<T?>?): LongArray?

    @Delete
    fun delete(vararg data: T?)

    @Delete
    fun deleteAll(data: List<T?>?)

}