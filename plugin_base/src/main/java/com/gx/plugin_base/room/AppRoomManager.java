package com.gx.plugin_base.room;

import android.content.Context;

import androidx.room.Room;

public class AppRoomManager {

    private static final String APPDBNAME = "diary.db";

    private AppRoomDatabase appRoomDatabase;
    private AppRoomManager(){

    }

    private static  class AppRoomHolder{
        private static AppRoomManager INSTANCE = new AppRoomManager();
    }
    public static AppRoomManager getInstance(){
        return AppRoomHolder.INSTANCE;
    }

    public void init(Context context){
        appRoomDatabase = Room.databaseBuilder(context,AppRoomDatabase.class, APPDBNAME).fallbackToDestructiveMigration().build();
    }


}
