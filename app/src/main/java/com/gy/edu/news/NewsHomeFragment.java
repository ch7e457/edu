package com.gy.edu.news;

import android.view.LayoutInflater;
import android.view.View;

import com.gy.edu.R;
import com.gy.edu.base.BaseFragment;

/**
 * 作者：gy on 16/12/21
 * 说明：资讯首页
 */
public class NewsHomeFragment extends BaseFragment {
    @Override
    protected View setView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_news_home,null);
        return view;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
