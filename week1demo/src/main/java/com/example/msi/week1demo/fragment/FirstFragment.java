package com.example.msi.week1demo.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msi.week1demo.R;
import com.example.msi.week1demo.adapter.XrecyclerAdapter;
import com.example.msi.week1demo.app.MyApplication;
import com.example.msi.week1demo.bean.ByKeywordBean;
import com.example.msi.week1demo.bean.shop;
import com.example.msi.week1demo.presenter.PresenterImpl;
import com.example.msi.week1demo.units.MvPInterface;
import com.example.msi.week1demo.units.MyServerApi;
import com.example.msi.week1demo.units.NetWorkUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment<T> extends Fragment implements MvPInterface.MyView<T> {

    @BindView(R.id.edit_inputname)
    EditText editInputname;
    @BindView(R.id.text_search)
    TextView textSearch;
    @BindView(R.id.xrecyclerview)
    XRecyclerView xrecyclerview;
    Unbinder unbinder;
    private PresenterImpl presenter;
    private String keyword="板鞋";
    private int page=1;
    private int count=5;
    private List<ByKeywordBean.ResultBean>mList=new ArrayList<>();
    private XrecyclerAdapter adapter;
    private com.example.msi.week1demo.mydao.shopDao shopDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        mList.clear();
        unbinder = ButterKnife.bind(this, view);
        presenter = new PresenterImpl(this);
        //数据库
        shopDao = MyApplication.getDaoSession().getShopDao();

        boolean netWork = NetWorkUtils.isNetWork(getActivity().getApplicationContext());
        if (!netWork){
            List<shop> shops = shopDao.loadAll();
            Log.e("eeee",shops.toString());
            /*for (int i=0;i<shops.size();i++){
                mList.get(i).setCommodityId(shops.get(i).getShopid());
                mList.get(i).setMasterPic(shops.get(i).getShopimage());
                mList.get(i).setCommodityName(shops.get(i).getShopname());
                mList.get(i).setPrice(shops.get(i).getShopprice());
                mList.get(i).setSaleNum(shops.get(i).getShopsum());
            }*/
        }else{
            startRequestData();
        }

        //设置适配器
        adapter = new XrecyclerAdapter(getActivity().getApplicationContext(),mList);
        xrecyclerview.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));
        xrecyclerview.setAdapter(adapter);
        // 可以设置是否开启加载更多/下拉刷新
        xrecyclerview.setLoadingMoreEnabled(true);
        // 可以设置加载更多的样式，很多种
        xrecyclerview.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        // 如果设置上这个，下拉刷新的时候会显示上次刷新的时间
        xrecyclerview.getDefaultRefreshHeaderView() // get default refresh header view
                .setRefreshTimeVisible(true);  // make refresh time visible,false means hiding
        setListener();
        return view;
    }

    private void setListener() {
        /*下拉刷新，上拉加载*/
        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                page=1;
                count=5;
                startRequestData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xrecyclerview.refreshComplete();
                    }
                },2000);
            }

            @Override
            public void onLoadMore() {
                page++;
                startRequestData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xrecyclerview.loadMoreComplete();
                    }
                },2000);
            }
        });
    }

    private void startRequestData() {
        Map<String,String> headmap=new HashMap<>();
        Map<String,String> bodymap=new HashMap<>();
        bodymap.put("keyword",keyword);
        bodymap.put("page",page+"");
        bodymap.put("count",count+"");
        //开始请求数据
        presenter.startRequestDataget(MyServerApi.BASE_BYKEYWORD,headmap,bodymap,ByKeywordBean.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.text_search)
    public void onViewClicked() {
        String shopname = editInputname.getText().toString().trim();
        keyword=shopname;
        if (shopname.equals("")){
            Toast.makeText(getActivity(),"请输入关键词",Toast.LENGTH_SHORT).show();
        }else{
            mList.clear();
            Map<String,String> headmap=new HashMap<>();
            Map<String,String> bodymap=new HashMap<>();
            bodymap.put("keyword",keyword);
            bodymap.put("page",page+"");
            bodymap.put("count",count+"");
            presenter.startRequestDataget(MyServerApi.BASE_BYKEYWORD,headmap,bodymap,ByKeywordBean.class);
        }
    }

    /*成功*/
    @Override
    public void success(T data) {
        if (data instanceof ByKeywordBean){
            ByKeywordBean byKeywordBean= (ByKeywordBean) data;
            if (byKeywordBean.getStatus().equals("0000")){
                if (byKeywordBean.getResult().size()==0){
                    Toast.makeText(getActivity(),"没有找到你想要的商品",Toast.LENGTH_SHORT).show();
                }else{
                    mList.addAll(byKeywordBean.getResult());
                    adapter.notifyDataSetChanged();
                    shop shop = new shop();
                    for (int i=0;i<byKeywordBean.getResult().size();i++){
                        shop.setShopid(byKeywordBean.getResult().get(i).getCommodityId());
                        shop.setShopimage(byKeywordBean.getResult().get(i).getMasterPic());
                        shop.setShopname(byKeywordBean.getResult().get(i).getCommodityName());
                        shop.setShopprice(byKeywordBean.getResult().get(i).getPrice());
                        shop.setShopsum(byKeywordBean.getResult().get(i).getSaleNum());
                    }

                    long insert = shopDao.insert(shop);
                    /*if (insert > 0) {
                        Toast.makeText(getActivity(), "插入成功", Toast.LENGTH_SHORT).show();
                    }*/
                }
            }else {
                Toast.makeText(getActivity(),byKeywordBean.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*失败*/
    @Override
    public void error(T error) {
        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.OnDestorys();
        if (presenter!=null){
            presenter=null;
        }
    }
}
