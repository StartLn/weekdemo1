package com.example.msi.reflection.bean;

import android.util.Log;

/**
 * <p>文件描述：<p>
 * <p>作者：${Ln}<p>
 * <p>创建时间：2019/2/13  13:54<p>
 * <p>更改时间：2019/2/13  13:54<p>
 * <p>版本号：1<p>
 */
public class MyData {
    private String name;
    private String sex;
    private int age;
    private String lick="lccc";

    /*有参构造*/
    public MyData(String name, String sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    /*无参构造*/
    public MyData() {

    }
    //自定义
    private MyData (String  name){
        this.name=name;
    }

    public void getInfo(String name){
        this.name=name;
    }

    private void getInfo(String name,String sex,int age){
        Log.e("name和sex",name+",sex+"+sex+",age="+age);
    }
    @Override
    public String toString() {
        return "MyData{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", lick='" + lick + '\'' +
                '}';
    }
}
