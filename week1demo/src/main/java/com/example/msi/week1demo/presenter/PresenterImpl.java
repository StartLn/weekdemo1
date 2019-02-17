package com.example.msi.week1demo.presenter;

import com.example.msi.week1demo.moudel.MoudelImpl;
import com.example.msi.week1demo.units.MvPInterface;

import java.util.Map;

/**
 * <p>文件描述：<p>
 * <p>作者：${Ln}<p>
 * <p>创建时间：2019/2/16  9:53<p>
 * <p>更改时间：2019/2/16  9:53<p>
 * <p>版本号：1<p>
 */
public class PresenterImpl implements MvPInterface.IPresenter {

    private MvPInterface.MyView myView;
    private MoudelImpl moudel;

    public PresenterImpl(MvPInterface.MyView myView) {
        this.myView = myView;
        moudel = new MoudelImpl();
    }

    @Override
    public void startRequestDataget(String url, Map<String, String> headmap, Map<String, String> bodymap, Class aClass) {
        moudel.getdata(url, headmap, bodymap, aClass, new MvPInterface.MyCallBack() {
            @Override
            public void setData(Object data) {
                myView.success(data);
            }

            @Override
            public void setError(Object error) {
                myView.error(error);
            }
        });
    }

    @Override
    public void startRequestDataput(String url, Map<String, String> headmap, Map<String, String> bodymap, Class aClass) {
        moudel.putdata(url, headmap, bodymap, aClass, new MvPInterface.MyCallBack() {
            @Override
            public void setData(Object data) {
                myView.success(data);
            }

            @Override
            public void setError(Object error) {
                myView.error(error);
            }
        });
    }

    //MVP的优化  处理内存泄漏
    public void OnDestorys(){
        if (myView!=null){
            myView=null;
        }
        if (moudel!=null){
            moudel=null;
        }
    }
}
