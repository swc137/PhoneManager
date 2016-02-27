package com.wjustudio.phoneManager.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wjustudio.phoneManager.R;
import com.wjustudio.phoneManager.adapter.HomeMainAdapter;
import com.wjustudio.phoneManager.adapter.LeftMenuAdapter;
import com.wjustudio.phoneManager.javaBean.IconInfo;
import com.wjustudio.phoneManager.lib.dicview.DiscView;
import com.wjustudio.phoneManager.utils.CommonUtil;
import com.wjustudio.phoneManager.utils.LogUtil;
import com.wjustudio.phoneManager.utils.SpUtil;
import com.wjustudio.phoneManager.widgt.AvatarView;
import com.wjustudio.phoneManager.widgt.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：songwenju on 2016/1/31 20:26
 * 邮箱：songwenju01@163.com
 */
public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.lv_appList)
    ListView mLvAppList;
    @Bind(R.id.disc_view)
    DiscView mDiscView;
    @Bind(R.id.tb_custom)
    Toolbar mToolbar;
    @Bind(R.id.dl_left)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.iv_avatar)
    AvatarView mIvAvatar;
    @Bind(R.id.tv_left_switch_model)
    TextView mTvLeftSwitchModel;
    @Bind(R.id.tv_left_setting)
    TextView mTvLeftSetting;
    @Bind(R.id.rv_left_menu_content)
    RecyclerView mRvLeftMenuContent;

    private List<IconInfo> mMainPageIcons;
    private List<IconInfo> mLeftPageIcons;
    private Context mContext;
    private int mWindowHeight;

    private int[] mIconArray = {R.mipmap.icon_safe, R.mipmap.icon_contacts,
            R.mipmap.icon_progress, R.mipmap.icon_app, R.mipmap.icon_cache};

    private int[] mLeftIconArray = {R.mipmap.icon_safe,R.mipmap.icon_safe};
    private ActionBarDrawerToggle mDrawerToggle;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    /**
     * 初始化相关数据
     */
    private void init() {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mContext = this;
        //设置Toolbar
        mToolbar.setTitle("手机管理系统");
        setSupportActionBar(mToolbar);
        //设置drawerLayout
        setDrawerLayout();
        //获得数据
        initData();
        //初始化view
        initView();
    }

    /**
     * 获得图标的列表
     */
    private void initData() {
        mMainPageIcons = new ArrayList<>();
        String[] iconNames = CommonUtil.getStringArray(R.array.icon_name);
        for (int i = 0; i < mIconArray.length; i++) {
            IconInfo iconInfo = new IconInfo(mIconArray[i], iconNames[i]);
            LogUtil.e(this, iconNames[i]);
            mMainPageIcons.add(iconInfo);
        }

        mLeftPageIcons = new ArrayList<>();
        iconNames = CommonUtil.getStringArray(R.array.left_menu_text);
        for (int j = 0; j < mLeftIconArray.length;j++){
            IconInfo iconInfo = new IconInfo(mLeftIconArray[j], iconNames[j]);
            mLeftPageIcons.add(iconInfo);
        }
    }

    /**
     * 初始化view
     */
    private void initView() {
        mDiscView.setValue(100);
        HashMap<String, Integer> windowSize = CommonUtil.getWindowSize(this);
        mWindowHeight = windowSize.get("height");
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mDiscView.getLayoutParams();
        params.height = (int) (mWindowHeight / 3 + 0.5) - mActionBar.getHeight();
        mDiscView.setLayoutParams(params);
        //设置主页面的listView的布局
        mLvAppList.setAdapter(new HomeMainAdapter(mContext, mMainPageIcons, mWindowHeight));
        mLvAppList.setOnItemClickListener(this);


        //设置RecyclerView

        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置垂直滚动，也可以设置横向滚动
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //RecyclerView设置布局管理器
        mRvLeftMenuContent.setLayoutManager(layoutManager);
        //设置侧拉菜单的recyclerView的布局
        mRvLeftMenuContent.setAdapter(new LeftMenuAdapter(mContext, mLeftPageIcons, windowSize));
        //添加分割线
        mRvLeftMenuContent.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL_LIST
        ));
        //mRvLeftMenuContent.setOnClickListener(this);
    }

    /**
     * 设置drawerLayout
     */
    private void setDrawerLayout() {
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeButtonEnabled(true); //设置返回键可用
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                //进入手机防盗
                if (isSetPwd()) {
                    showEnterPwdDialog();
                } else {
                    showSetPwdDialog();
                }
                break;
        }
    }

    /**
     * 显示设置密码的Dialog
     */
    private void showSetPwdDialog() {

    }

    /**
     * 显示填写密码的Dialog
     */
    private void showEnterPwdDialog() {
    }

    /**
     * 是否设置了密码
     *
     * @return
     */
    private boolean isSetPwd() {
        String password = SpUtil.getString("password", null);
        return !TextUtils.isEmpty(password);
    }

}
