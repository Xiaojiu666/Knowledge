package com.gx.base.room;


import com.gx.base.data.UserInfo;


//@Database(entities = {UserInfo.class}, version = 1)
public abstract class AppRoomDatabase  {

    public abstract UserInfo firmwareUpgradeInfoDao();

}

