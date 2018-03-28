package com.baidu.coin.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.coin.R;
import com.baidu.coin.ShareActivity;
import com.baidu.coin.bean.News;
import com.baidu.coin.utils.DateUtil;
import com.baidu.coin.utils.Week;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by KangWei on 2018/3/21.
 * 2018/3/21 13:23
 * Coin
 * com.baidu.coin
 */

public class HomeFragment extends Fragment {
    private LinearLayoutManager mLayoutManager;
    private ContentAdapter mAdapter;
    private String today;
    private SuperSwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar footerProgressBar;
    private ImageView footerImageView;
    private TextView footerTextView;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_bak, container, false);
        initView(inflater, view);
        return view;
    }

    private void initData() {
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new ContentAdapter(getData());
        today = getCurrentDate();
    }

    private String getCurrentDate() {
        long now = System.currentTimeMillis();
        Week week = DateUtil.getWeek(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("今天 MM月dd日", Locale.CHINA);
        return sdf.format(now) + week.getChineseName();
    }

    private ArrayList<News> getData() {
        String json =
                "{'time':'23:13','content':'【下拉刷新就调用SwipeRefreshLayout相关就好了，那么加载更多呢？" +
                        "这个就要自己去写相关的布局了。然后第一个问题，什么时候加载更多？" +
                        "？因为RecycleView有各种布局，所以判断最后一个也是要区分不同的adapter的！】'}";
        Gson gson = new Gson();
        News news = gson.fromJson(json, News.class);
        ArrayList<News> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add(news);
        }
        return data;
    }

    private void initView(LayoutInflater inflater, View view) {
        TextView tvShow = view.findViewById(R.id.today);
        RecyclerView mRecyclerView = view.findViewById(R.id.content_list);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        tvShow.setText(today);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setHeaderViewBackgroundColor(0xff888888);
        swipeRefreshLayout.setHeaderView(createHeaderView(inflater));// add headerView
        swipeRefreshLayout.setFooterView(createFooterView(inflater));
        swipeRefreshLayout.setTargetScrollWithLayout(true);
        swipeRefreshLayout
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        textView.setText("正在刷新");
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                                progressBar.setVisibility(View.GONE);
                            }
                        }, 2000);
                    }

                    @Override
                    public void onPullDistance(int distance) {
                        // pull distance
                    }

                    @Override
                    public void onPullEnable(boolean enable) {
                        textView.setText(enable ? "松开刷新" : "下拉刷新");
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setRotation(enable ? 180 : 0);
                    }
                });

        swipeRefreshLayout
                .setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {

                    @Override
                    public void onLoadMore() {
                        footerTextView.setText("正在加载...");
                        footerImageView.setVisibility(View.GONE);
                        footerProgressBar.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                footerImageView.setVisibility(View.VISIBLE);
                                footerProgressBar.setVisibility(View.GONE);
                                swipeRefreshLayout.setLoadMore(false);
                            }
                        }, 5000);
                    }

                    @Override
                    public void onPushEnable(boolean enable) {
                        footerTextView.setText(enable ? "松开加载" : "上拉加载");
                        footerImageView.setVisibility(View.VISIBLE);
                        footerImageView.setRotation(enable ? 0 : 180);
                    }

                    @Override
                    public void onPushDistance(int distance) {
                        // TODO Auto-generated method stub

                    }

                });
    }

    private View createHeaderView(LayoutInflater inflater) {
        View headerView = inflater.inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

    private View createFooterView(LayoutInflater inflater) {
        View footerView = inflater.inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView
                .findViewById(R.id.footer_pb_view);
        footerImageView = footerView
                .findViewById(R.id.footer_image_view);
        footerTextView = footerView
                .findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }


    private class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> {


        private ArrayList<News> mData;

        ContentAdapter(@NonNull ArrayList<News> data) {
            this.mData = data;
        }

        public void updateData(ArrayList<News> data) {
            this.mData = data;
            notifyDataSetChanged();
        }

        @Override
        public ContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // 实例化展示的view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rv_item, parent, false);
            // 实例化viewholder
            return new ContentHolder(v);
        }

        @Override
        public void onBindViewHolder(ContentAdapter.ContentHolder holder, int position) {
            holder.tvTime.setText(mData.get(position).getTime());
            holder.tvContent.setText(mData.get(position).getContent());
            holder.ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("HomeFragment", "chick share");
                    Intent intent = new Intent(getContext(), ShareActivity.class);
                    startActivity(intent);
                }
            });
        }

        /**
         * Returns the total number of items in the mData set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return mData.size();
        }

        class ContentHolder extends RecyclerView.ViewHolder {

            final TextView tvTime;
            final TextView tvContent;
            final ImageView ivShare;

            ContentHolder(View itemView) {
                super(itemView);
                tvTime = itemView.findViewById(R.id.time);
                tvContent = itemView.findViewById(R.id.content);
                ivShare = itemView.findViewById(R.id.share);
            }
        }
    }
}
