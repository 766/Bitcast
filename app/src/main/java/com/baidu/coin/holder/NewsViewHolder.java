package com.baidu.coin.holder;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.coin.R;
import com.baidu.coin.bean.News;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by KangWei on 2018-03-29.
 * 2018-03-29 00:11
 * Coin
 * com.baidu.coin.holder
 */
public class NewsViewHolder extends BaseViewHolder<News> {
    private final TextView newsTitle;
    private final TextView newsContent;
    private final TextView newsTime;
    private final View shareBtn;

    public NewsViewHolder(ViewGroup parent) {
        super(parent, R.layout.view_rv_item);
        newsTitle = $(R.id.news_title);
        newsTime = $(R.id.time);
        newsContent = $(R.id.content);
        shareBtn = $(R.id.share);
    }

    @Override
    public void setData(final News news) {
        Log.i("ViewHolder", "position" + getDataPosition());
        newsTime.setText(news.getTime());
        newsTitle.setText(news.getTitle());
        newsContent.setText(news.getContent());
    }

    public View getShareButton() {
        return shareBtn;
    }
}
