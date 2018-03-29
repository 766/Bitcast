package com.baidu.coin;

import com.baidu.coin.bean.News;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KangWei on 2018-03-29.
 * 2018-03-29 00:17
 * Coin
 * com.baidu.coin
 */
public class DataProvider {
    public static List<News> getPersonList(int page) {
        ArrayList<News> arr = new ArrayList<>();
        if (page >= 4) return arr;

        String json =
                "{'time':'23:13','content':'【下拉刷新就调用SwipeRefreshLayout相关就好了，那么加载更多呢？" +
                        "这个就要自己去写相关的布局了。然后第一个问题，什么时候加载更多？" +
                        "？因为RecycleView有各种布局，所以判断最后一个也是要区分不同的adapter的！】'}";
        Gson gson = new Gson();
        News news = gson.fromJson(json, News.class);
        news.setTitle("微信支付和支付宝的成功，能复制到东南亚去吗？");
        for (int i = 0; i < 5; i++) {
            arr.add(news);
        }
        return arr;
    }
}
