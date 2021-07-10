package com.gx.plugin_base.room;

import androidx.room.RoomDatabase;

import com.gx.plugin_base.data.UserInfo;


//@Database(entities = {UserInfo.class}, version = 1)
public abstract class AppRoomDatabase extends RoomDatabase {

    public abstract UserInfo firmwareUpgradeInfoDao();

}

