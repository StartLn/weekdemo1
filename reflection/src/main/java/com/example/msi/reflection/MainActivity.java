package com.example.msi.reflection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.msi.reflection.bean.MyData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button field;
    private Button method;
    private Button concur;
    private MyData myData;
    private Class<? extends MyData> aClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //得到字节码对象
        myData = new MyData();
        aClass = myData.getClass();
    }

    private void initView() {
        //成员变量
        field = findViewById(R.id.btn_field);
        //成员方法
        method = findViewById(R.id.btn_method);
        //构造方法
        concur = findViewById(R.id.btn_concur);

        field.setOnClickListener(this);
        method.setOnClickListener(this);
        concur.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_field:
                //得到成员变量
                getField();
                //设置成员变量
                setField();
                break;
            case R.id.btn_method:
                //得到成员方法
                getMethod();
                break;
            case R.id.btn_concur:
                //得到构造方法
                getConcur();
                break;
        }
    }

    //反射
    private void getConcur() {
        //操作里面的构造方法

        //得到所有的构造方法
        Constructor[] con = aClass.getDeclaredConstructors();
        for (Constructor c:con){
            Log.e("构造方法",c.getName());
        }
        //只有一个参数就是通过参数就可以知道那个构造了
        try {
            Constructor constructor = aClass.getDeclaredConstructor(String.class,String.class,int.class);
            Object o = constructor.newInstance("memeda", "heheda", 17);
            Log.e("获取构造",o.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getMethod() {
        //得到所有的成员方法
        Method[] methods = aClass.getDeclaredMethods();
        for (Method m:methods){
            Log.e("查看方法",m.getName());
        }

        try {
            Method info = aClass.getDeclaredMethod("getInfo", String.class, String.class, int.class);
            info.setAccessible(true);
            Object instance = aClass.newInstance();
            info.invoke(instance, "小帅", "帅", 18);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setField() {
        //通过反射给某个属性赋值
        try {
            Field field = aClass.getDeclaredField("name");
            field.setAccessible(true);

            MyData instance=aClass.newInstance();
            field.set(instance,"sunny");
            Log.e("给属性赋值",instance.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getField() {
        //通过反射得到该类的所有成员变量
        Field[] fields = aClass.getDeclaredFields();
        //所有public修饰 的变量

        for(Field f:fields){
            Log.e("变量",f.getName());
        }

        try {
            Field field = aClass.getDeclaredField("lick");
            //暴力反射
            field.setAccessible(true);
            Object o = field.get(myData);
            if (o instanceof String){
                Log.e("通过反射获取属性的值",o.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
