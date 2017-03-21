package cn.lenovo.eleccal.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by lenovo on 2016/11/28.
 */

public abstract class MRFragment<P extends  BasePresenter> extends BaseFragment{

    public P mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter=createPresenter();
    }

    protected abstract P createPresenter();

    @Override
    public void onDestroyView() {
        if (mPresenter!=null){
            mPresenter.detachView();
        }
        super.onDestroyView();
    }

    public void showLoading() {
        showProgressDialog();
    }

    public void hideLoading() {
        dismissProgressDialog();
    }

}
