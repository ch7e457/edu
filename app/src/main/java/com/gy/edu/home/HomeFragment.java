package com.gy.edu.home;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.gy.edu.R;
import com.gy.edu.base.BaseFragment;
import com.gy.edu.home.adapter.HomeAdapter;
import com.gy.edu.home.adapter.HomePagerAdAdapter;
import com.gy.edu.home.bean.HomeBean;
import com.gy.edu.util.ListViewItemListener;
import com.gy.edu.view.PageIndicatorView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：gy on 16/12/21
 * 说明：首页
 */
public class HomeFragment extends BaseFragment implements ListViewItemListener {

    private final int UPDATE_TIME = 5000;
    private PullToRefreshListView listView;
    private View headerView;
    private ViewPager viewPager;
    private PageIndicatorView indicatorView;
    private HomeAdapter adapter;
    private HomePagerAdAdapter pagerAdAdapter;
    private ArrayList<String> adRequest;//轮播
    private boolean isAdd = true;
    private List<HomeBean> datas;

    @Override
    protected View setView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        return view;
    }

    @Override
    protected void initView() {
        listView = (PullToRefreshListView) _rootView.findViewById(R.id.home_listview);
        headerView = View.inflate(mContext, R.layout.home_header, null);
        viewPager = (ViewPager) headerView.findViewById(R.id.main_ad_viewpager);
        indicatorView = (PageIndicatorView) headerView.findViewById(R.id.main_ad_pagepoint);
    }

    @Override
    protected void initData() {
        datas = new ArrayList<>();
        String[] titles = mContext.getResources().getStringArray(R.array.home_title);
        String[] names = mContext.getResources().getStringArray(R.array.home_name);
        String[] numbers = mContext.getResources().getStringArray(R.array.home_number);
        String[] classs = mContext.getResources().getStringArray(R.array.home_class);
        for (int i = 0; i < titles.length; i++) {
            HomeBean bean = new HomeBean();
            bean.title = titles[i];
            bean.name = names[i];
            bean.number = numbers[i];
            bean.classs = classs[i];
            datas.add(bean);
        }
        adRequest = new ArrayList<>();
        adRequest.add("0");
        adRequest.add("1");
        adRequest.add("2");
        adRequest.add("3");
        adRequest.add("4");
        pagerAdAdapter = new HomePagerAdAdapter(mContext, adRequest);
        indicatorView.setPropertise(adRequest.size(), 0);
        pagerAdAdapter.setListener(this);
        adapter = new HomeAdapter(mContext,datas);
        listView.getRefreshableView().addHeaderView(headerView);

    }

    @Override
    protected void fillData() {
        super.fillData();
        listView.setMode(PullToRefreshBase.Mode.DISABLED);
        viewPager.setAdapter(pagerAdAdapter);
        viewPager.setCurrentItem(0);
        viewPager.invalidate();
        adSwitchHandler.postDelayed(adSwitchRunnable, UPDATE_TIME);

        listView.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                if (adRequest.size() > 1) {
                    indicatorView.setCurrentPosition(arg0 % adRequest.size());
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                if (arg0 == 1) {
                    adSwitchHandler.removeCallbacks(adSwitchRunnable);
                } else {
                    adSwitchHandler.removeCallbacks(adSwitchRunnable);
                    adSwitchHandler.postDelayed(adSwitchRunnable, UPDATE_TIME);
                }
            }
        });
    }

    @Override
    public void doPassActionListener(Object obj, int org1, int org2, String str) {

    }

    /**
     * 更换广告页
     */
    private Handler adSwitchHandler = new Handler();
    /**
     * 更换广告页的runnable
     */
    private Runnable adSwitchRunnable = new Runnable() {

        @Override
        public void run() {

            if (null != viewPager && null != adRequest && adRequest.size() > 0) {
                int nextPosition;
                int currentItem = viewPager.getCurrentItem();
                if (isAdd) {
                    if (currentItem == adRequest.size() - 1) {
                        nextPosition = currentItem - 1;
                        isAdd = false;
                    } else {
                        nextPosition = ++currentItem;
                    }
                } else {
                    if (currentItem == 0) {
                        nextPosition = currentItem + 1;
                        isAdd = true;
                    } else {
                        nextPosition = --currentItem;
                    }
                }
                viewPager.setCurrentItem(nextPosition, true);
            }

            adSwitchHandler.removeCallbacks(adSwitchRunnable);
            adSwitchHandler.postDelayed(adSwitchRunnable, UPDATE_TIME);
        }
    };
}
