package com.example.msi.week1demo.units;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>文件描述：<p>
 * <p>作者：${Ln}<p>
 * <p>创建时间：2019/2/16  9:52<p>
 * <p>更改时间：2019/2/16  9:52<p>
 * <p>版本号：1<p>
 */
public class RetrofitUnits {
    private final MyApiServer myApiServer;
    /*网络工具类*/
    /*静态内部类单例模式*/

    //方法私有化
    private RetrofitUnits() {
        //日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //构建Okhttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //添加拦截器
                .addInterceptor(loggingInterceptor)
                //延时加载
                .connectTimeout(20,TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
                //客户端
                .retryOnConnectionFailure(true)
                .build();

        /*构建Retrofit*/
        Retrofit retrofit = new Retrofit.Builder()
                //访问处理适配器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //添加Gson解析器
                .addConverterFactory(GsonConverterFactory.create())
                //添加网络前缀
                .baseUrl(MyServerApi.BASE_URL)
                //添加网络访问工具类
                .client(okHttpClient)
                .build();
        //通过retrofit创建Service 调用请求方法
        myApiServer = retrofit.create(MyApiServer.class);
    }

    public static RetrofitUnits getInstance(){
        return RetrofitHoudel.units;
    }
    private static class RetrofitHoudel{
        private static final RetrofitUnits units=new RetrofitUnits();
    }

    //get请求方式
    public RetrofitUnits get(String url, Map<String,String>headmap,Map<String,String>bodymap){
        if (headmap==null){
            headmap=new HashMap<>();
        }
        if (bodymap==null){
            bodymap=new HashMap<>();
        }
        myApiServer.get(url,headmap,bodymap)
                //线程调度器
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return RetrofitUnits.getInstance();
    }

    //put请求方式
    public RetrofitUnits put(String url, Map<String,String>headmap,Map<String,String>bodymap){
        if (headmap==null){
            headmap=new HashMap<>();
        }
        if (bodymap==null){
            bodymap=new HashMap<>();
        }
        myApiServer.put(url,headmap,bodymap)
                //线程调度器
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return RetrofitUnits.getInstance();
    }
    //重写一个观察者对象
    private Observer observer= new Observer<ResponseBody>() {
        //暂停
        @Override
        public void onCompleted() {

        }

        //失败
        @Override
        public void onError(Throwable e) {
            if (httpListener!=null){
                httpListener.error(e.getMessage());
            }
        }

        //成功
        @Override
        public void onNext(ResponseBody responseBody) {
            if (httpListener!=null){
                try {
                    httpListener.success(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    //接口回调
    public interface HttpListener{
        void success(String jsonStr);
        void error(String error);
    }

    private HttpListener httpListener;

    public void setHttpListener(HttpListener httpListener){
        this.httpListener=httpListener;
    }
}
