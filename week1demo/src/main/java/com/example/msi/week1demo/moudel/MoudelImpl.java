package com.example.msi.week1demo.moudel;

import com.example.msi.week1demo.units.MvPInterface;
import com.example.msi.week1demo.units.RetrofitUnits;
import com.google.gson.Gson;

import java.util.Map;

/**
 * <p>文件描述：<p>
 * <p>作者：${Ln}<p>
 * <p>创建时间：2019/2/16  9:53<p>
 * <p>更改时间：2019/2/16  9:53<p>
 * <p>版本号：1<p>
 */
public class MoudelImpl implements MvPInterface.IMoudel {
    private MvPInterface.MyCallBack myCallBack;

    @Override
    public void getdata(String url, Map<String, String> headmap, Map<String, String> bodymap, final Class aClass, final MvPInterface.MyCallBack myCallBack) {
        this.myCallBack=myCallBack;
        RetrofitUnits.getInstance().get(url,headmap,bodymap).setHttpListener(new RetrofitUnits.HttpListener() {
            @Override
            public void success(String jsonStr) {
                Gson gson = new Gson();
                Object fromJson = gson.fromJson(jsonStr, aClass);
                myCallBack.setData(fromJson);
            }

            @Override
            public void error(String error) {
                myCallBack.setError(error);
            }
        });
    }

    @Override
    public void putdata(String url, Map<String, String> headmap, Map<String, String> bodymap, final Class aClass, final MvPInterface.MyCallBack myCallBack) {
        this.myCallBack=myCallBack;
        RetrofitUnits.getInstance().put(url,headmap,bodymap).setHttpListener(new RetrofitUnits.HttpListener() {
            @Override
            public void success(String jsonStr) {
                Gson gson = new Gson();
                Object fromJson = gson.fromJson(jsonStr,aClass);
                myCallBack.setData(fromJson);
            }

            @Override
            public void error(String error) {
                myCallBack.setError(error);
            }
        });
    }


}
