package com.sn.desnginpatterns.Single;

public class LazySingle {

    //public static LazySingle mLazySingle = new LazySingle();
    private LazySingle mLazySingle;

    /**
     *  懒汉式 :
     * @return
     */
    public synchronized LazySingle getInstance() {
        if (mLazySingle == null) {
            mLazySingle = new LazySingle();
        }
        return mLazySingle;
    }
}
