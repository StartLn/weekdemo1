package com.example.msi.myapplication;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class MainActivity extends AppCompatActivity {

    private SimpleDraweeView simple1,simple2,simple3,simple4,simple5,simple6;
    private RoundingParams parames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        /*展示图片*/
        Uri uri=Uri.parse("http://img1.yulin520.com/news/8OIFWKCGZFR0O868X20V.jpg#586_616");
        simple1.setImageURI(uri);
        /*进度条*/

        //创建Builder对象,一般创建出参数对象
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        //创建参数对象,设置其样式为进度条
        GenericDraweeHierarchy hierarchy = builder.setProgressBarImage(new ProgressBarDrawable()).build();
        //将参数对象设置给图片控件
        simple2.setHierarchy(hierarchy);
        //控件加载图片,参数:网络图片的网址.
        simple2.setImageURI(uri);


        /*圆形,圆角*/

        //加载图片的网址
        uri = Uri.parse("http://img4q.duitang.com/uploads/item/201304/27/20130427043538_wAfHC.jpeg");
        //builder对象用一个即可,在这里创建出成员变量
        builder = new GenericDraweeHierarchyBuilder(getResources());
        // 设置圆形图片
        // 设置形状对象,形状为圆形
        parames = RoundingParams.asCircle();
        //创建设置参数,设置一个形状,把形状对象塞入
        GenericDraweeHierarchy roundness= builder.setRoundingParams(parames).build();
        //将参数对象设置给图片控件
        simple3.setHierarchy(roundness);
        //控件加载图片
        simple3.setImageURI(uri);
        // 设置圆角图片
        //设置边角的弧度,使其为圆角
        parames =RoundingParams.fromCornersRadius(30f);

        //设置图片控件的背景颜色
        //parames.setOverlayColor(getResources().getColor(android.R.color.holo_red_light));//覆盖层
        //设置图片的边框颜色及边框的粗细
        parames.setBorder(getResources().getColor(android.R.color.holo_blue_light),5);//边框*//*
        //这里的代码和设置圆形图片这一块代码是一种的,唯一不同就是对parames的设置.
        GenericDraweeHierarchy circularBead = builder.setRoundingParams(parames).build();
        simple4.setHierarchy(circularBead);
        // 加载图片
        simple4.setImageURI(uri);

        /*动图加载*/

        //请求GIF动画,采用MVC的设计模式(注意加载GIF动画还要添加依赖)
        /* 支持 GIF 动图，需要添加:compile 'com.facebook.fresco:animated-gif:0.14.1' */
        //GIF动画网址,加载需要一段时间
        Uri uri1 = Uri.parse("http://p1.so.qhmsg.com/bdr/_240_/t0179d442a840b6df46.gif");
        DraweeController controller =Fresco.newDraweeControllerBuilder()
                .setUri(uri1)//设置GIF网址
                .setAutoPlayAnimations(true)//是否自动播放动画,false为不播放
                .setOldController(simple5.getController())//内存优化
                .build();
        simple5.setController(controller);
        // 动画停止
        //拿到动画对象
        Animatable animatableStop = simple5.getController().getAnimatable();
    //进行非空及是否动画在播放判断
        if(animatableStop != null &&animatableStop.isRunning()) {
    //动画在播放,停止动画播放
            animatableStop.stop();
        }
    // 动画开始
    //拿到动画对象
        Animatable animatableStart = simple5.getController().getAnimatable();
    //进行非空及是否动画在播放判断
        if(animatableStart != null &&!animatableStart.isRunning()) {
    //动画停止播放,播放动画
            animatableStart.start();
        }


        /*渐进式加载*/
        Uri uri2=Uri.parse("http://img5.duitang.com/uploads/item/201312/03/20131203153823_Y4y8F.jpeg");
        ProgressiveJpegConfig jpegConfig= new ProgressiveJpegConfig() {
            @Override
            public int getNextScanNumberToDecode(int scanNumber) {
                return scanNumber + 2;
            }

            @Override
            public QualityInfo getQualityInfo(int scanNumber) {
                boolean isGoodEnough =(scanNumber >= 5);
                return ImmutableQualityInfo.of(scanNumber,isGoodEnough, false);
            }
        };
    //上面的和下面一行是固定代码.使用使复制粘贴即可
        ImagePipelineConfig.newBuilder(MainActivity.this).setProgressiveJpegConfig(jpegConfig).build();
    // 创建 ImageRequest 对象.
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri2)//设置URL
                .setProgressiveRenderingEnabled(true)//打开渐进渲染
                .build();
        DraweeController draweeController =Fresco.newDraweeControllerBuilder()
                //必须要设置ImageRequest对象,里面包含了图片的网址.
                .setImageRequest(request)
                //开启用户点击重新加载图片的功能
                .setTapToRetryEnabled(true)
                //会复用以前的对象,可以节省内存.
                .setOldController(simple6.getController())
                .build();
        // 1设置加载的控制
        simple6.setController(draweeController);
    }

    private void initView() {
        simple1 = findViewById(R.id.Simple1);
        simple2=findViewById(R.id.Simple2);
        simple3=findViewById(R.id.Simple3);
        simple4=findViewById(R.id.Simple4);
        simple5=findViewById(R.id.Simple5);
        simple6=findViewById(R.id.Simple6);
    }

}
