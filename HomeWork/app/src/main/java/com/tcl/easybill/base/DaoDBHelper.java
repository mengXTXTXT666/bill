package com.tcl.easybill.base;

import android.database.sqlite.SQLiteDatabase;

import com.tcl.easybill.greendao.DaoMaster;
import com.tcl.easybill.greendao.DaoSession;

public class DaoDBHelper {
    private static final String DB_NAME = "CoShareBill_DB";

    private static volatile DaoDBHelper sInstance;
    private SQLiteDatabase mDb;
    private DaoMaster mDaoMaster;
    private DaoSession mSession;


    private  DaoDBHelper(){

        /*package database operate*/
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(MyApplication.getContext(),DB_NAME,null);
        /*get database*/
        mDb = openHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mDb);
        //operate data
        mSession = mDaoMaster.newSession();
    }


    public static DaoDBHelper getInstance(){
        if (sInstance == null){
            synchronized (DaoDBHelper.class){
                if (sInstance == null){
                    sInstance = new DaoDBHelper();
                }
            }
        }
        return sInstance;
    }

    public DaoSession getSession(){
        return mSession;
    }

    public SQLiteDatabase getDatabase(){
        return mDb;
    }

    public DaoSession getNewSession(){
        return mDaoMaster.newSession();
    }
}
