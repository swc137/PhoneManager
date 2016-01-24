package com.wjustudio.phoneManager.activities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wjustudio.phoneManager.R;
import com.wjustudio.phoneManager.biz.CheckVersionBizImpl;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.tv_splash_activity)
    TextView mTvSplashActivity;
    @Bind(R.id.pb_splash_activity)
    ProgressBar mPbSplashActivity;
    //包管理器
    private PackageManager mPackageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mPackageManager = getPackageManager();
        try {
            PackageInfo packageInfo = mPackageManager.getPackageInfo("com.wjustudio.phoneManager", 0);
            String versionName = packageInfo.versionName;
            mTvSplashActivity.setText(versionName);
            final int versionCode = packageInfo.versionCode;
            final CheckVersionBizImpl cvb = new CheckVersionBizImpl();
            //请求网络在子线程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cvb.checkVersion(String.valueOf(versionCode));
                }
            }).start();

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
