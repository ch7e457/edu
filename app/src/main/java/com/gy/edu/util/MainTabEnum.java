package com.gy.edu.util;


import com.gy.edu.R;
import com.gy.edu.examination.ExaminationHomeFragment;
import com.gy.edu.home.HomeFragment;
import com.gy.edu.mine.MineHomeFragment;
import com.gy.edu.news.NewsHomeFragment;
import com.gy.edu.teach.TeachHomeFragment;

/**
 * Created by 高岳 on 2016/7/4.
 * Describe:tabhost 枚举类
 */
public enum MainTabEnum {

    HOME(HomeFragment.class,"微课", R.drawable.tab_home),
    HAILIAO(TeachHomeFragment.class,"微教材",R.drawable.tab_teach),
    YOUPIN(ExaminationHomeFragment.class,"微考试",R.drawable.tab_teach),
    SHOPCAR(NewsHomeFragment.class,"资讯",R.drawable.tab_news),
    MINE(MineHomeFragment.class,"我的",R.drawable.tab_mine);

    public Class mClass;
    public String name;
    public int imgId;

    MainTabEnum(Class mClass, String name, int imgId) {
        this.mClass = mClass;
        this.name = name;
        this.imgId = imgId;
    }


}
