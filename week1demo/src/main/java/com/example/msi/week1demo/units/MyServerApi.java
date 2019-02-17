package com.example.msi.week1demo.units;

/**
 * <p>文件描述：<p>
 * <p>作者：${Ln}<p>
 * <p>创建时间：2019/2/16  9:56<p>
 * <p>更改时间：2019/2/16  9:56<p>
 * <p>版本号：1<p>
 */
public class MyServerApi {

    //网址前缀
    public static final String BASE_URL="http://172.17.8.100/";

    //根据关键词查询
    public static final String BASE_BYKEYWORD="small/commodity/v1/findCommodityByKeyword";

    //商品详情
    public static final String BASE_DETAILSBYID="small/commodity/v1/findCommodityDetailsById";

    //同步购物车
    public static final String BASE_SHOPPINGCART="small/order/verify/v1/syncShoppingCart";
}
