package com.example.msi.week1demo.units;

import java.util.Map;

import javax.security.auth.callback.Callback;

/**
 * <p>文件描述：<p>
 * <p>作者：${Ln}<p>
 * <p>创建时间：2019/2/16  9:54<p>
 * <p>更改时间：2019/2/16  9:54<p>
 * <p>版本号：1<p>
 */
public interface MvPInterface {
    interface MyCallBack<T>{
        void setData(T data);
        void setError(T error);
    }

    interface MyView<T>{
        void  success(T data);
        void error(T error);
    }

    interface IMoudel{
        void getdata(String url, Map<String,String>headmap, Map<String,String>bodymap, Class aClass, MyCallBack myCallBack);
        //void postdata(String url, Map<String,String>headmap, Map<String,String>bodymap, Class aClass, MyCallBack myCallBack);
        void putdata(String url, Map<String,String>headmap, Map<String,String>bodymap, Class aClass, MyCallBack myCallBack);
        //void deletedata(String url, Map<String,String>headmap, Map<String,String>bodymap, Class aClass, MyCallBack myCallBack);
    }

    interface IPresenter{
        void startRequestDataget(String url,Map<String,String>headmap,Map<String,String>bodymap,Class aClass);
        //void startRequestDatapost(String url,Map<String,String>headmap,Map<String,String>bodymap,Class aClass);
        void startRequestDataput(String url,Map<String,String>headmap,Map<String,String>bodymap,Class aClass);
        //void startRequestDatadelete(String url,Map<String,String>headmap,Map<String,String>bodymap,Class aClass);
    }
}
