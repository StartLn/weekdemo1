package com.example.msi.week1demo.units;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * <p>文件描述：<p>
 * <p>作者：${Ln}<p>
 * <p>创建时间：2019/2/17  19:34<p>
 * <p>更改时间：2019/2/17  19:34<p>
 * <p>版本号：1<p>
 */
/**
 * 判断手机是否有网
 */
public class NetWorkUtils {
    public static  boolean isNetWork(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo!=null && activeNetworkInfo.isAvailable();
    }
}
