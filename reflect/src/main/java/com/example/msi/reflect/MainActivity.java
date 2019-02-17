package com.example.msi.reflect;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_circle)
    Button btnCircle;
    @BindView(R.id.btn_circular)
    Button btnCircular;
    @BindView(R.id.btn_ratio)
    Button btnRatio;
    @BindView(R.id.btn_gif)
    Button btnGif;
    @BindView(R.id.Simple)
    SimpleDraweeView Simple;
    @BindView(R.id.btn_getnote)
    Button btnGetnote;
    @BindView(R.id.btn_addlist)
    Button btnAddlist;
    private Unbinder unbinder;

    //网址定义全局
    //动图
    Uri uri1=Uri.parse("http://www.zhaoapi.cn/images/girl.gif");
    //静图
    Uri uri2=Uri.parse("http://www.zhaoapi.cn/images/quarter/ad1.png");
    private GenericDraweeHierarchyBuilder builder;
    private RoundingParams parames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        Simple.setAspectRatio(1f);
        builder = new GenericDraweeHierarchyBuilder(getResources());

        //磁盘缓存
        DiskCacheConfig diskCacheConfig =  DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryName("images")
                .setBaseDirectoryPath(Environment.getExternalStorageDirectory())
                .build();
        //设置磁盘缓存的配置,生成配置文件
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();
        Fresco.initialize(this, config); //不设置默认传一个参数既可
    }

    @OnClick({R.id.btn_circle, R.id.btn_circular, R.id.btn_ratio, R.id.btn_gif, R.id.btn_getnote, R.id.btn_addlist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_circle:
                circle();
                break;
            case R.id.btn_circular:
                circular();
                break;
            case R.id.btn_ratio:
                ratio();
                break;
            case R.id.btn_gif:
                gif();
                break;
            case R.id.btn_getnote:
                getnote();
                break;
            case R.id.btn_addlist:
                addlist();
                break;
        }
    }

    private void addlist() {

    }

    private void getnote() {

    }

    private void gif() {
        //加载动图
        DraweeController controller =Fresco.newDraweeControllerBuilder()
                .setUri(uri1)//设置GIF网址
                .setAutoPlayAnimations(true)//是否自动播放动画,false为不播放
                .setOldController(Simple.getController())//内存优化
                .build();
        Simple.setController(controller);
        // 动画停止
        //拿到动画对象
        Animatable animatableStop = Simple.getController().getAnimatable();
        //进行非空及是否动画在播放判断
        if(animatableStop != null &&animatableStop.isRunning()) {
            //动画在播放,停止动画播放
            animatableStop.stop();
        }
        // 动画开始
        //拿到动画对象
        Animatable animatableStart = Simple.getController().getAnimatable();
        //进行非空及是否动画在播放判断
        if(animatableStart != null &&!animatableStart.isRunning()) {
            //动画停止播放,播放动画
            animatableStart.start();
        }
    }

    private void ratio() {
        Simple.setAspectRatio(2.71f);
    }

    private void circular() {
        // 设置圆角图片
        //设置边角的弧度,使其为圆角
        parames = RoundingParams.fromCornersRadius(20f);
        //设置图片控件的背景颜色
        GenericDraweeHierarchy circularBead = builder.setRoundingParams(parames).build();
        Simple.setHierarchy(circularBead);
        // 加载图片
        Simple.setImageURI(uri2);
    }

    private void circle() {
        // 设置圆形图片
        parames = RoundingParams.asCircle();
        //创建设置参数,设置一个形状,把形状对象塞入
        GenericDraweeHierarchy roundness= builder.setRoundingParams(parames).build();
        //将参数对象设置给图片控件
        Simple.setHierarchy(roundness);
        //控件加载图片
        Simple.setImageURI(uri2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除绑定
        unbinder.unbind();
    }
}
