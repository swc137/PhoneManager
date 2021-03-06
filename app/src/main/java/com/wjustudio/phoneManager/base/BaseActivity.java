package com.wjustudio.phoneManager.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.wjustudio.phoneManager.R;
import com.wjustudio.phoneManager.utils.LogUtil;
import com.wjustudio.phoneManager.utils.ToastUtil;

/**
 * Activity的基类
 * 1.规范代码结构
 * 2.提供公共方法，精简代码
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    protected Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        Fresco.initialize(mContext);
        setContentView(getLayoutID());
        onInitView();
        onInitListener();
        onInitData();
        onSetViewData();
        registerCommonButton();
    }

    /**
     * 多个界面公共的Button
     */
    private void registerCommonButton() {
        View view = findViewById(R.id.back);
        if (view != null){
            view.setOnClickListener(this);
        }
    }

    /**
     * 设置Activity的布局文件
     * @return
     */
    protected abstract int getLayoutID();

    /**
     * 初始化view
     */
    protected abstract void onInitView();


    /**
     * 初始化数据
     */
    protected abstract void onInitData();

    /**
     * 设置view的数据
     */
    protected abstract void onSetViewData();

    /**
     * 初始化listener，注册监听器，适配器
     */
    protected abstract void onInitListener();

    /**
     * BaseActivity没有处理的点击事件，将在这个里面处理
     * @param v
     */
    protected abstract void processClick(View v);


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            default:
                processClick(v);
                break;
        }
    }

    /**
     * 弹toast
     * @param msg
     */
    public void toast(String msg){
        ToastUtil.showToast(msg);
    }

    /**
     *显示d级别的log
     * @param msg
     */
    public void logd(String msg){
        LogUtil.d(getClass(),msg);
    }

    /**
     * 显示e级别的log
     * @param msg
     */
    public void loge(String msg){
        LogUtil.e(getClass(), msg);
    }
}
