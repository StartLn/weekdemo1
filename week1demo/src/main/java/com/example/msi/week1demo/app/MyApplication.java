package com.example.msi.week1demo.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.msi.week1demo.mydao.DaoMaster;
import com.example.msi.week1demo.mydao.DaoSession;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * <p>文件描述：<p>
 * <p>作者：${Ln}<p>
 * <p>创建时间：2019/2/16  11:56<p>
 * <p>更改时间：2019/2/16  11:56<p>
 * <p>版本号：1<p>
 */
public class MyApplication extends Application {

    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        /*配置数据库*/
        initGreenDao();
    }

    private void initGreenDao() {
        //第一步创建OpenHeler类
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "lns.db");
        //第二部开启一个可写数据库
        SQLiteDatabase writableDatabase = openHelper.getWritableDatabase();
        //通过DaoMaster封装
        DaoMaster master = new DaoMaster(writableDatabase);
        mDaoSession = master.newSession();
    }

    public static DaoSession getDaoSession(){
        return mDaoSession;
    }
}
