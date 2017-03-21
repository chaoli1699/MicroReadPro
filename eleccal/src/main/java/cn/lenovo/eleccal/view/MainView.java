package cn.lenovo.eleccal.view;

import cn.lenovo.eleccal.base.BaseView;

/**
 * Created by Aaron on 2017/1/9.
 */

public interface MainView extends BaseView {

    void submitSuccess();
    void submitFail(String msg);
}
