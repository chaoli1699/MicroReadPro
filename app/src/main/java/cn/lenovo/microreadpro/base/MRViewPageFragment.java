package cn.lenovo.microreadpro.base;

import android.os.Bundle;
import android.view.View;

/**
 * Created by lenovo on 2016/11/28.
 */

public abstract class MRViewPageFragment<P extends BasePresenter> extends BaseViewPageFragment{

    protected P mvpPresenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mvpPresenter = createPresenter();
    }

    protected abstract P createPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

    public void showLoading() {
        showProgressDialog();
    }

    public void hideLoading() {
        dismissProgressDialog();
    }
}
