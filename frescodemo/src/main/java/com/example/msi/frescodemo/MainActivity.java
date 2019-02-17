package com.example.msi.frescodemo;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.drawable.ScalingUtils;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn7)
    Button btn7;
    @BindView(R.id.btn8)
    Button btn8;
    @BindView(R.id.Simple_DraweeView)
    com.facebook.drawee.view.SimpleDraweeView SimpleDraweeView;
    private Uri uri=Uri.parse("http://img1.yulin520.com/news/8OIFWKCGZFR0O868X20V.jpg#586_616");
    private Uri uri0 = Uri.parse("http://img4q.duitang.com/uploads/item/201304/27/20130427043538_wAfHC.jpeg");
    private Uri uri1 = Uri.parse("http://p1.so.qhmsg.com/bdr/_240_/t0179d442a840b6df46.gif");
    private Uri uri2=Uri.parse("http://img5.duitang.com/uploads/item/201312/03/20131203153823_Y4y8F.jpeg");
    private GenericDraweeHierarchyBuilder builder;
    private RoundingParams parames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SimpleDraweeView.setAspectRatio(1f);
        builder = new GenericDraweeHierarchyBuilder(getResources());
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                /*圆角*/
                yuanjiao();
                break;
            case R.id.btn2:
                /*圆形*/
                yuanxing();
                break;
            case R.id.btn3:
                /*缩放比例1.2f*/
                bili();
                break;
            case R.id.btn4:
                /*渐进式*/
                jianjin();
                break;
            case R.id.btn5:
                /*磁盘缓存*/
                huanchun();
                break;
            case R.id.btn6:
                /*加载动图*/
                gifjiazai();
                break;
            case R.id.btn7:
                /*加载监听*/
                jiazaijianting();
                break;
            case R.id.btn8:
                /*配置OKHTTP*/
                peizhiOk();
                break;
        }
    }


    private void yuanjiao() {

        // 设置圆角图片
        //设置边角的弧度,使其为圆角
        parames = RoundingParams.fromCornersRadius(60f);
        //设置图片控件的背景颜色
        parames.setOverlayColor(getResources().getColor(android.R.color.holo_green_light));
        //设置图片的边框颜色及边框的粗细
        parames.setBorder(getResources().getColor(android.R.color.holo_blue_light),5);
        GenericDraweeHierarchy circularBead = builder.setRoundingParams(parames).build();
        SimpleDraweeView.setHierarchy(circularBead);
        // 加载图片
        SimpleDraweeView.setImageURI(uri);
    }

    private void yuanxing() {
        // 设置圆形图片
        parames = RoundingParams.asCircle();
        //创建设置参数,设置一个形状,把形状对象塞入
        GenericDraweeHierarchy roundness= builder.setRoundingParams(parames).build();
        //将参数对象设置给图片控件
        SimpleDraweeView.setHierarchy(roundness);
        //控件加载图片
        SimpleDraweeView.setImageURI(uri);
    }

    private void bili() {
    // 图片的请求
        SimpleDraweeView.setAspectRatio(1.2f);

    }

    private void jianjin() {
        /*渐进式加载*/
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
                .setOldController(SimpleDraweeView.getController())
                .build();
        //设置加载的控制
        SimpleDraweeView.setController(draweeController);
    }

    private void huanchun() {

        //设置磁盘缓存

        DiskCacheConfig diskCacheConfig =  DiskCacheConfig.newBuilder(this)

                .setBaseDirectoryName("images_zjj")

                .setBaseDirectoryPath(Environment.getExternalStorageDirectory())

                .build();

        //设置磁盘缓存的配置,生成配置文件

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)

                .setMainDiskCacheConfig(diskCacheConfig)

                .build();

        Fresco.initialize(this,config);

    }

    private void jiazaijianting() {
    }

    private void gifjiazai() {
        //加载动图
        DraweeController controller =Fresco.newDraweeControllerBuilder()
                .setUri(uri1)//设置GIF网址
                .setAutoPlayAnimations(true)//是否自动播放动画,false为不播放
                .setOldController(SimpleDraweeView.getController())//内存优化
                .build();
        SimpleDraweeView.setController(controller);
        // 动画停止
        //拿到动画对象
        Animatable animatableStop = SimpleDraweeView.getController().getAnimatable();
        //进行非空及是否动画在播放判断
        if(animatableStop != null &&animatableStop.isRunning()) {
            //动画在播放,停止动画播放
            animatableStop.stop();
        }
        // 动画开始
        //拿到动画对象
        Animatable animatableStart = SimpleDraweeView.getController().getAnimatable();
        //进行非空及是否动画在播放判断
        if(animatableStart != null &&!animatableStart.isRunning()) {
            //动画停止播放,播放动画
            animatableStart.start();
        }
    }

    private void peizhiOk() {

    }
}
