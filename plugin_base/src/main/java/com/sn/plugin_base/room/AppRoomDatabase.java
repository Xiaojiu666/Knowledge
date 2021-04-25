package com.sn.plugin_base.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sn.plugin_base.data.UserInfo;


/**
 * @Copyright 天津华来科技股份有限公司北京分公司
 * @Author
 * @Date 2021/3/5 10:14
 * Describe
 */
//@Database(entities = {UserInfo.class}, version = 1)
public abstract class AppRoomDatabase extends RoomDatabase {

    public abstract UserInfo firmwareUpgradeInfoDao();

}

