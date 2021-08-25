package com.gx.task.model.room

import androidx.room.*
import com.gx.task.model.data.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao : BaseRoomDao<Task> {

    @Query("SELECT * FROM  task ")
    fun getAllTaskData(): Flow<MutableList<Task>>

}