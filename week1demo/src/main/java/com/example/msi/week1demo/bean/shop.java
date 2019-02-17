package com.example.msi.week1demo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * <p>文件描述：<p>
 * <p>作者：${Ln}<p>
 * <p>创建时间：2019/2/16  17:25<p>
 * <p>更改时间：2019/2/16  17:25<p>
 * <p>版本号：1<p>
 */
@Entity
public class shop {

    @Id(autoincrement = true)
    private Long id;

    private String shopimage;

    private String shopname;

    private double shopprice;

    private int shopsum;

    private int shopid;

    @Generated(hash = 1111816716)
    public shop(Long id, String shopimage, String shopname, double shopprice,
            int shopsum, int shopid) {
        this.id = id;
        this.shopimage = shopimage;
        this.shopname = shopname;
        this.shopprice = shopprice;
        this.shopsum = shopsum;
        this.shopid = shopid;
    }

    @Generated(hash = 1494887899)
    public shop() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopimage() {
        return this.shopimage;
    }

    public void setShopimage(String shopimage) {
        this.shopimage = shopimage;
    }

    public String getShopname() {
        return this.shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public double getShopprice() {
        return this.shopprice;
    }

    public void setShopprice(double shopprice) {
        this.shopprice = shopprice;
    }

    public int getShopsum() {
        return this.shopsum;
    }

    public void setShopsum(int shopsum) {
        this.shopsum = shopsum;
    }

    public int getShopid() {
        return this.shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }
    
}
