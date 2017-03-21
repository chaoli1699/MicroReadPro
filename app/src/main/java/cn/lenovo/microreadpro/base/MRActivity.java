package cn.lenovo.microreadpro.base;

import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by lenovo on 2016/11/28.
 */

public abstract class MRActivity<P extends BasePresenter> extends BaseActivity {

    protected P mPresenter;
    private MyApplication mApp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter=createPresenter();
        mApp= (MyApplication) MyApplication.getInstance();
        mApp.registerActivity(this);
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }

        if (mApp.allActivities.size()>0){
            mApp.unregisterActivity(this);
        }
    }

    public void showLoading() {
        showProgressDialog();
    }

    public void hideLoading() {
        dismissProgressDialog();
    }
}
