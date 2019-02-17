package com.example.msi.week1demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.msi.week1demo.R;
import com.example.msi.week1demo.bean.ByKeywordBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>文件描述：<p>
 * <p>作者：${Ln}<p>
 * <p>创建时间：2019/2/16  9:58<p>
 * <p>更改时间：2019/2/16  9:58<p>
 * <p>版本号：1<p>
 */
public class XrecyclerAdapter extends RecyclerView.Adapter<XrecyclerAdapter.ViewHolder> {
    private Context mContext;
    private List<ByKeywordBean.ResultBean> mData;

    public XrecyclerAdapter(Context mContext, List<ByKeywordBean.ResultBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.shopImage.setImageURI(mData.get(position).getMasterPic());
        holder.shopTitle.setText(mData.get(position).getCommodityName());
        holder.shopPrice.setText("￥"+mData.get(position).getPrice());
        holder.shopNumber.setText("已售"+mData.get(position).getSaleNum()+"件");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shop_image)
        SimpleDraweeView shopImage;
        @BindView(R.id.shop_title)
        TextView shopTitle;
        @BindView(R.id.shop_price)
        TextView shopPrice;
        @BindView(R.id.shop_number)
        TextView shopNumber;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
