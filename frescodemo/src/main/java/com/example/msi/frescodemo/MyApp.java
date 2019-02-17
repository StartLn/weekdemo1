package com.example.msi.frescodemo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * <p>文件描述：<p>
 * <p>作者：${Ln}<p>
 * <p>创建时间：2019/2/12  18:19<p>
 * <p>更改时间：2019/2/12  18:19<p>
 * <p>版本号：1<p>
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
