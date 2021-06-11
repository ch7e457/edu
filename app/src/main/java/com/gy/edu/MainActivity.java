package com.gy.edu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.gy.edu.base.BaseActivity;
import com.gy.edu.util.MainTabEnum;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends BaseActivity {
    //定义FragmentTabHost对象
    private FragmentTabHost mTabHost;
    //定义一个布局
    private LayoutInflater layoutInflater;
    private MainTabEnum[] mainTabArray = new MainTabEnum[]{MainTabEnum.HOME, MainTabEnum.HAILIAO, MainTabEnum.YOUPIN, MainTabEnum.SHOPCAR, MainTabEnum.MINE};
    private int pushFlag;
    private boolean isExit = false;

    private int tabIndex = -1;
    private final static String ALBUM_PATH
            = Environment.getExternalStorageDirectory() + "/load_test.png";

    @Override
    protected void initView() {
        setContentViewRes(R.layout.activity_main);
        hidenTop();
    }

    @Override
    protected void initData() {
        //实例化布局对象
        layoutInflater = LayoutInflater.from(this);

        //实例化TabHost对象，得到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setDividerDrawable(R.color.white);
        //得到fragment的个数
        int count = mainTabArray.length;

        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mainTabArray[i].name).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, mainTabArray[i].mClass, null);
            //设置Tab按钮的背景
            //                mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.mipmap.ic_tab_home_bg);
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));

        }

        //保存icon,供分享使用
        save();

    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tabIndex != -1) {
            mTabHost.setCurrentTab(tabIndex);
            tabIndex = -1;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void fillData() {
        super.fillData();
    }

    private void save() {
        Bitmap tmp = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
        try {
            saveBitmapToFile(tmp, ALBUM_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveBitmapToFile(Bitmap bitmap, String _file)
            throws IOException {
        BufferedOutputStream os = null;
        try {
            File file = new File(_file);
            int end = _file.lastIndexOf(File.separator);
            String _filePath = _file.substring(0, end);
            File filePath = new File(_filePath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            file.createNewFile();
            os = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mainTabArray[index].imgId);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mainTabArray[index].name);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ExitProgram();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void ExitProgram() {
        if (!isExit) {
            isExit = true;
            showToast("再按一次\"退出\"程序");
            exitHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            this.finish();
            System.exit(0);
        }
    }

    public Handler exitHandler = new Handler() {
        public void handleMessage(Message msg) {
            isExit = false;
        }
    };
}
