package com.gx.task.model.room

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gx.task.model.data.Task
import com.gx.utils.log.LogUtil

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao?

    companion object {
        const val DATABASE_NAME = "room_demo"
        const val TAG = "RoomTaskDataBase"
        @Volatile
        private var instance: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase? {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {

                    instance = it }
            }
        }

        private fun buildDatabase(appContext: Context): TaskDatabase {
            return Room.databaseBuilder(
                appContext,
                TaskDatabase::class.java,
                DATABASE_NAME
            ).addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    LogUtil.e(TAG,"onCreate ")
                }
            }).build()
        }
    }
}