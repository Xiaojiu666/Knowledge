package com.sn.plugin_base.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserInfo {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "userName")
    private String userName;

    @ColumnInfo(name = "userEmail")
    private String userEmail;

}
